package cn.fizzo.hub.school.fc.entity;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import cn.fizzo.hub.school.config.UrlConfig;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.entity.net.BaseRE;
import cn.fizzo.hub.school.entity.net.GetConsoleInfoRE;
import cn.fizzo.hub.school.fc.FcUtils;
import cn.fizzo.hub.school.fc.OkHttpCallback;
import cn.fizzo.hub.school.network.RequestParamsBuilder;
import cn.fizzo.hub.school.utils.LogU;
import cn.fizzo.hub.school.utils.ShellU;
import cn.fizzo.hub.sdk.utils.ByteU;
import okhttp3.Response;

/**
 * 桂花网连接对象
 */
public class FcConnectEntity {

    private static final String TAG = "FcConnectEntity";

    private static final int MSG_FIND_IP = 0x01;
    private static final int MSG_OFFLINE = 0x02;
    private static final int MSG_POST_STATE = 0x03;
    private static final int MSG_NO_DATA = 0x04;
    private static final int MSG_UPLOAD_DATA = 0x05;

    private static final long DELAY_GET_IP = 2000;

    private static final long INTERVAL_POST_STATE = 1000 * 60;

    private GetConsoleInfoRE.CassiaHubsBean cassiaInfo;//桂花设备信息
    private FcUtils fcUtils;//桂花设备连接工具
    private String ip;//连接的IP
    private Context mContext;

    List<CassiaDataEntity> listData = new ArrayList<>();

    private int online = 2; //桂花网的状态
    private Callback.Cancelable mCancelable;
    /**
     * 消息队列
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //找IP
                case MSG_FIND_IP:
                    findIpByLinuxCommand();
                    break;
                //不在线
                case MSG_OFFLINE:
                    online = 2;
                    if (mHandler != null){
                        mHandler.sendEmptyMessageDelayed(MSG_FIND_IP, DELAY_GET_IP);
                    }
                    break;
                //报告状态
                case MSG_POST_STATE:
                    postCassiaState();
                    if (mHandler != null){
                        mHandler.sendEmptyMessageDelayed(MSG_POST_STATE, INTERVAL_POST_STATE);
                    }
                    break;
                    //没有数据
                case MSG_NO_DATA:
                    online = 3;
                    if (mHandler != null){
                        mHandler.sendEmptyMessageDelayed(MSG_FIND_IP, DELAY_GET_IP);
                    }
                    break;
                    //上传心率
                case MSG_UPLOAD_DATA:
                    final List<CassiaDataEntity> mTmpList = new ArrayList<>();
                    mTmpList.addAll(listData);
                    listData.clear();
                    saveTimerData(mTmpList);
                    if (mHandler != null){
                        mHandler.sendEmptyMessageDelayed(MSG_UPLOAD_DATA,1000);
                    }
                    break;
            }
        }
    };

    /**
     * 初始化
     *
     * @param cassia
     */
    public FcConnectEntity(Context context, GetConsoleInfoRE.CassiaHubsBean cassia) {
        LogU.v(TAG, "new FcConnectEntity:" + cassia.mac_addr);
        this.cassiaInfo = cassia;
        mHandler.sendEmptyMessage(MSG_FIND_IP);
        mHandler.sendEmptyMessageDelayed(MSG_POST_STATE, INTERVAL_POST_STATE);
        mHandler.sendEmptyMessage(MSG_UPLOAD_DATA);
        this.mContext = context;

    }


    /**
     * 断开连接
     */
    public void disConnect() {
        if (fcUtils != null) {
            fcUtils.stopScan();
        }
        if (mCancelable != null){
            mCancelable.cancel();
        }
        if (mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        mHandler = null;

    }

    /**
     * 找IP
     */
    private void findIpByLinuxCommand() {
        LogU.v(TAG,"findIpByLinuxCommand:" + cassiaInfo.mac_addr);
        String cmd = "arp -n | grep " + cassiaInfo.mac_addr + " | awk -F '[()]' '{print $2}'";
        ShellU.CommandResult commandResult = ShellU.execCmd(cmd, true);
        LogU.v(TAG, "findIpByLinuxCommand result:" + commandResult.result);
        //获取成功
        if (commandResult.result == 0) {
            LogU.v(TAG, "findIpByLinuxCommand successMsg:" + commandResult.successMsg);
            if (commandResult.successMsg.equals("")) {
                if (mHandler != null){
                    mHandler.sendEmptyMessage(MSG_OFFLINE);
                }
            } else {
                ip = commandResult.successMsg;
                fcUtils = new FcUtils(null, null, null, ip);
                startScan();
            }
        } else {
            LogU.v(TAG, "findIpByLinuxCommand errorMsg:" + commandResult.errorMsg);
            if (mHandler != null){
                mHandler.sendEmptyMessage(MSG_OFFLINE);
            }
        }
    }

    /**
     * 开始扫描数据
     */
    private void startScan() {
        fcUtils.scan(new OkHttpCallback() {
            @Override
            public void onSuccess(final Response response) {
                online = 1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //读取response信息
                        Reader charStream = response.body().charStream();
                        BufferedReader in = new BufferedReader(charStream);
                        String line;

                        try {
                            while ((line = in.readLine()) != null) {
                                if (line.length() > 10) {
//                                    Log.v(TAG, "scan devices:" + line);
                                    try {
                                        CassiaLine lineObj = JSON.parseObject(line.substring(6), CassiaLine.class);
                                        if (lineObj.name.startsWith("FZS_")) {
//                                            Log.v(TAG, "scan devices:" + line);
                                            byte [] lineByte = ByteU.HexString2Bytes(lineObj.adData);

                                            int bmp = ByteU.byteToInt(new byte[]{lineByte[13]});
                                            int sc = ByteU.byteToInt(new byte[]{lineByte[15],lineByte[14]});
//                                            LogU.v(TAG,"lineObj.mac:" + lineObj.bdaddrs.get(0).bdaddr);
//                                            LogU.v(TAG,"sc:" + sc);
//                                            LogU.v(TAG,"bpm:" + bmp);
                                            listData.add(new CassiaDataEntity(lineObj.bdaddrs.get(0).bdaddr, bmp,sc,0,lineObj.rssi));
                                        }
                                    } catch (JSONException ex) {
//                                        Log.v(TAG,ex.getMessage());
                                    }
                                }
                            }
                        } catch (IOException e) {
//                            LogU.v(TAG, "IOException:" + e.getMessage());
                            if (mHandler != null){
                                mHandler.sendEmptyMessage(MSG_NO_DATA);
                            }
                        }

                    }
                }).start();
            }

            @Override
            public void onFailure(String msg) {
                if (mHandler != null){
                    mHandler.sendEmptyMessage(MSG_OFFLINE);
                }
            }
        });
    }


    /**
     * 发送桂花网设备的在线状态
     */
    private void postCassiaState() {
//        LogU.v(TAG,"postCassiaState");
        x.task().post(new Runnable() {
            @Override
            public void run() {
                RequestParams params = RequestParamsBuilder.buildPostCassiaStateRP(mContext,
                        SPDataApp.getServiceIp(mContext) + UrlConfig.URL_POST_CASSIA_STATE,
                        cassiaInfo.id, online);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }

    /**
     * 存储这个时刻的心率
     */
    private void saveTimerData(List<CassiaDataEntity> tmpList) {
//        LogU.v(TAG,"saveTimerData");
        String macaddrsString = "[";
        String bpmString = "[";
        String stepString = "[";
        String cadenceString = "[";
        String rssiString = "[";
        for (CassiaDataEntity cassiaData : tmpList) {
            macaddrsString += "\"" + cassiaData.macaddr + "\",";
            bpmString += cassiaData.bpm + ",";
            stepString += cassiaData.stepcount + ",";
            cadenceString += cassiaData.stridefrequencie + ",";
            rssiString += cassiaData.rssis + ",";
        }
        if (bpmString.length() > 2) {
            macaddrsString = macaddrsString.substring(0, macaddrsString.length() - 1);
            bpmString = bpmString.substring(0, bpmString.length() - 1);
            stepString = stepString.substring(0, stepString.length() - 1);
            cadenceString = cadenceString.substring(0, cadenceString.length() - 1);
            rssiString = rssiString.substring(0,rssiString.length() -1);
        }
        macaddrsString += "]";
        bpmString += "]";
        stepString += "]";
        cadenceString += "]";
        rssiString += "]";
        RequestParams params = RequestParamsBuilder.buildRealTimeUploadAntRPByMac(mContext,
                SPDataApp.getServiceIp(mContext )+ UrlConfig.URL_UPLOAD_RECENT_HR_BY_MAC
                        ,cassiaInfo.id,macaddrsString, bpmString, stepString,cadenceString,rssiString);

        mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
            @Override
            public void onSuccess(BaseRE result) {

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
