package cn.fizzo.hub.school.fc;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import org.xutils.HttpManager;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZhangMin on 2016/11/18.
 * <p>
 * README:
 * <p>
 * 所需权限:
 * AndroidManifest.xml配置
 * <uses-permission android:name="android.permission.INTERNET" />
 * <p>
 * 所需要引入的第三方库
 * app/build.gradle中配置
 * compile 'org.xutils:xutils:3.3.38'
 * compile 'com.squareup.okhttp3:okhttp:3.4.2'
 * compile 'com.google.code.gson:gson:2.8.0'
 * <p>
 * <p>
 * 初始化Utils的时候需要将开发者帐号和密码,以及hub的mac地址传入进来
 * Utils(developer,pwd,hubMac)
 * <p>
 * 身份验证:
 * oauth(callback)
 * 将callback传递进来,处理success,fail后的操作
 * <p>
 * 扫描设备
 * scan(callback)
 * <p>
 * 停止扫描
 * stopScan()
 */

public class FcUtils {
    private static final String TAG = "FcUtils";

    private Handler handler = new Handler(Looper.getMainLooper());
//    private String root = "http://api.cassianetworks.com/";
    private String root = "192.168.40.1";
    private HttpManager manager = x.http();
    private final int TIMEOUT = 30;
    private String access_token;
    private String developer;
    private String pwd;
    private String hubMac;
    private OkHttpClient client = new OkHttpClient().newBuilder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();
    private Call call;

    /**
     * 初始化工具类
     *
     * @param developer 开发者账号
     * @param pwd       开发者密码
     * @param hubMac    执行扫描的HUB的MAC地址
     */
    public FcUtils(@NonNull String developer, @NonNull String pwd, @NonNull String hubMac, String root) {
        this.developer = developer;
        this.pwd = pwd;
        this.hubMac = hubMac;
        this.root = root;
    }


    /**
     * 验证开发者信息
     *
     * @param callback
     */
    void oauth(HttpCallBack<String> callback) {
        String strBase64 = new String(Base64.encode((developer + ":" + pwd).getBytes(), Base64.DEFAULT));
        RequestParams params = new RequestParams(root + "oauth2/token");
        params.setConnectTimeout(30 * 1000);
        params.addHeader("Authorization", "Basic " + strBase64);
        Map<String, String> map = new HashMap<>();
        map.put("grant_type", "client_credentials");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.addParameter(entry.getKey(), entry.getValue());

        }
        manager.post(params, callback);

    }

    /**
     * 发送扫描请求
     *
     * @param callback 处理请求回调
     */
    public void scan(OkHttpCallback callback) {
        Map<String, String> map = new HashMap<>();
//        map.put("mac", hubMac);
        map.put("event", "1");
        map.put("chip", "1");
//        map.put("filter_uuid","C0D0");
        get("http://" + root  + "/gap/nodes/", map, callback);
    }


    /**
     * 停止扫描
     */
    public void stopScan() {
        if (call != null) {
            call.cancel();
        }
    }

    private void get(String url, Map<String, String> params, final OkHttpCallback callback) {
        url = buildGetUrl(url, params);
        Log.v(TAG,"url:" + url);
        Request request = new Request.Builder().url(url).addHeader("Authorization", "Bearer " + access_token).build();
        call = client.newCall(request);
        onStart(callback);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onError(callback, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    onSuccess(callback, response);

                } else {
                    onError(callback, response.message());
                    response.body().close();
                }
            }
        });

    }

    String getAccess_token() {
        return access_token;
    }

    void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    /**
     * 拼接get请求的url
     *
     * @param url root url
     * @param map 参数集合
     * @return 拼接好的url
     */
    private String buildGetUrl(String url, Map<String, String> map) {
        if (map == null) return url;
        try {
            StringBuilder sb = new StringBuilder();
            for (String name : map.keySet()) {
                sb.append(name).append("=").append(
                        java.net.URLEncoder.encode(map.get(name), "UTF-8")).append("&");
            }
            return url + "?" + sb.toString().substring(0, sb.toString().length() - 1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }


    private void onStart(OkHttpCallback callback) {
        if (null != callback) {
            callback.onStart();
        }
    }

    private void onSuccess(final OkHttpCallback callback, final Response response) {

        if (null != callback) {
            handler.post(new Runnable() {
                public void run() {
                    callback.onSuccess(response);
                }
            });
        }
    }

    private void onError(final OkHttpCallback callback, final String msg) {
        if (null != callback) {
            handler.post(new Runnable() {
                public void run() {
                    callback.onFailure(msg);
                }
            });
        }
    }

}
