package cn.fizzo.hub.school.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.fizzo.hub.school.ActivityStackManager;
import cn.fizzo.hub.school.LocalApp;
import cn.fizzo.hub.school.config.SPEnums;
import cn.fizzo.hub.school.config.UrlConfig;
import cn.fizzo.hub.school.data.DBDataConsole;
import cn.fizzo.hub.school.data.DBDataStore;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.data.SPDataStore;
import cn.fizzo.hub.school.entity.event.ConnectedServiceStatusEE;
import cn.fizzo.hub.school.entity.event.ConsoleInfoMonitorEE;
import cn.fizzo.hub.school.entity.event.StoreInfoMonitorEE;
import cn.fizzo.hub.school.entity.net.BaseRE;
import cn.fizzo.hub.school.entity.net.GetConsoleInfoCheckRE;
import cn.fizzo.hub.school.entity.net.GetConsoleInfoRE;
import cn.fizzo.hub.school.entity.net.GetConsoleInfoStrRE;
import cn.fizzo.hub.school.network.BaseResponseParser;
import cn.fizzo.hub.school.network.RequestParamsBuilder;
import cn.fizzo.hub.school.ui.activity.help.HelpHwUpdateActivity;

/**
 * Created by Raul.fan on 2018/1/3 0003.
 */

public class ConsoleInfoMonitorService extends Service {


    /* contains */
    private static final String TAG = "ConsoleInfoMonitorService";
    private static final boolean DEBUG = true;

    private int INTERVAL_DEFAULT_MONITOR = 10 * 1000;//更新间隔

    private static final int MSG_GET_CONSOLE_INFO = 0x01;//更新设备信息
    private static final int MSG_APP_REBOOT = 0x02;//APP重启

    private String mLastUpdateTime = "1970-12-19 10:16:00";

    private int mLastStoreId = 0;
    private String mLastStore = "";
    private String mLastConsole = "";
    private String mLastCassias = "";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                //获取设备信息
                case MSG_GET_CONSOLE_INFO:
                    try {
                        getUpdateChecking();
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessageDelayed(MSG_GET_CONSOLE_INFO, INTERVAL_DEFAULT_MONITOR);
                    break;
                case MSG_APP_REBOOT:
                    ActivityStackManager.getAppManager().finishAllActivity();
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mLastStoreId = SPDataStore.getStoreId(ConsoleInfoMonitorService.this);
        mHandler.sendEmptyMessage(MSG_GET_CONSOLE_INFO);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 获取Console最新状态
     *
     * @throws PackageManager.NameNotFoundException
     */
    private void getUpdateChecking() throws PackageManager.NameNotFoundException {
        RequestParams params = RequestParamsBuilder.buildGetConsoleRP(ConsoleInfoMonitorService.this,
                SPDataApp.getServiceIp(ConsoleInfoMonitorService.this) + UrlConfig.URL_GET_CONSOLE_INFO,
                "updatetime");
        x.http().post(params, new Callback.CommonCallback<BaseRE>() {
            @Override
            public void onSuccess(BaseRE result) {
                if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                    GetConsoleInfoCheckRE checkingRE = JSON.parseObject(result.result, GetConsoleInfoCheckRE.class);
                    //硬件需要升级
                    if (!checkingRE.hrt_fw_url.equals("") && !LocalApp.getInstance().isUpdateHwNow()){
                        Intent intent = new Intent(ConsoleInfoMonitorService.this, HelpHwUpdateActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("fwUrl",checkingRE.hrt_fw_url);
                        intent.putExtras(bundle);
                        ConsoleInfoMonitorService.this.startActivity(intent);
                    }
                    //需要获取最新信息
                    if (!checkingRE.updatetime.equals(mLastUpdateTime)) {
                        try {
                            getConsoleInfo();
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                LocalApp.getInstance().getEventBus().post(new ConnectedServiceStatusEE(true));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LocalApp.getInstance().getEventBus().post(new ConnectedServiceStatusEE(false));
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取门店信息
     */
    private void getConsoleInfo() throws PackageManager.NameNotFoundException {
        RequestParams params = RequestParamsBuilder.buildGetConsoleRP(ConsoleInfoMonitorService.this,
                SPDataApp.getServiceIp(ConsoleInfoMonitorService.this) + UrlConfig.URL_GET_CONSOLE_INFO,
                "all");
        x.http().post(params, new Callback.CommonCallback<BaseRE>() {
            @Override
            public void onSuccess(BaseRE result) {
                if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
//                    LogU.v(DEBUG, TAG, result.result);
                    GetConsoleInfoStrRE strRE = JSON.parseObject(result.result, GetConsoleInfoStrRE.class);
                    GetConsoleInfoRE entity = JSON.parseObject(result.result, GetConsoleInfoRE.class);

                    //设备信息发生变化
                    if (!strRE.console.equals(mLastConsole)) {
                        DBDataConsole.saveStoreInfo(entity.console);
                        mLastConsole = strRE.console;
                        LocalApp.getInstance().getEventBus().post(new ConsoleInfoMonitorEE());
                    }

                    //门店信息发生变化
                    if (!strRE.store.equals(mLastStore)) {
                        DBDataStore.saveStoreInfo(entity.store);
                        SPDataStore.setStoreId(ConsoleInfoMonitorService.this, entity.store.id);
                        mLastStore = strRE.store;
                        LocalApp.getInstance().getEventBus().post(new StoreInfoMonitorEE());

                        //门店ID发生变化
                        if (entity.store.id != mLastStoreId) {
                            mLastStoreId = entity.store.id;
                            //若回归出厂状态
                            if (entity.store.id == SPEnums.DEFAULT_STORE_ID){
                                mHandler.sendEmptyMessage(MSG_APP_REBOOT);
                            }
                        }
                    }

                    //桂花设备发生变化
//                    if (!strRE.cassia_hubs.equals(mLastCassias)){
//                        FcManager.getManager().reConnectFc(entity.cassia_hubs);
//                    }

                    mLastUpdateTime = strRE.updatetime;
                }
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
