package cn.fizzo.hub.school.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.fizzo.hub.school.LocalApp;
import cn.fizzo.hub.school.config.UrlConfig;
import cn.fizzo.hub.school.data.DBDataCache;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.entity.db.CacheDE;
import cn.fizzo.hub.school.entity.event.NewUploadDataEE;
import cn.fizzo.hub.school.entity.net.BaseRE;
import cn.fizzo.hub.school.network.BaseResponseParser;
import cn.fizzo.hub.school.network.RequestParamsBuilder;


/**
 * Created by Raul on 2015/10/29.
 * 监听需要上传的数据的后台服务
 */
public class UploadWatcherService extends Service {

    /* contains */
    private static final String TAG = "UploadWatcherService";
    private static final boolean DEBUG = true;

    public static final int MSG_UPLOAD = 0x01;

    /* local data */
    private long delayTime = 5000;
    private static final long DelayTimeMax = 60 * 1000 * 5;

    private int mRepeatTime = 0;//重试次数
    private boolean uploading = false;

    private boolean netStatus = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startUpload();
        LocalApp.getInstance().getEventBus().register(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 收到新上传的数据
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NewUploadDataEE event) {
        startUpload();
    }


//    /**
//     * 收到新上传的数据
//     *
//     * @param event
//     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEventMainThread(NetStatusEE event) {
//        if (netStatus != event.netOk) {
//            if (event.netOk) {
//                uploading = false;
//                uploadHandler.sendEmptyMessage(MSG_UPLOAD);
//            }
//        }
//        netStatus = event.netOk;
//    }

    @Override
    public void onDestroy() {
        LocalApp.getInstance().getEventBus().unregister(this);
        stopUpload();
        super.onDestroy();
    }

    Handler uploadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_UPLOAD) {
                //若正在上传
                if (uploading) {
                    return;
                }
                uploading = true;
                // 若网络不好, 过段时间重试
                if (!netStatus) {
                    return;
                }
                // 若需要上传的数据是空, 重新获取数据
                CacheDE cache = DBDataCache.getFirst();
                if (cache != null) {
                    switch (cache.type) {
                        //心率分段数据
                        case CacheDE.TYPE_ANT_SPLIT:
                            postSplit(cache);
                            break;
                    }
                } else {
                    uploading = false;
                    return;
                }
            }
        }
    };


    /**
     * 开始上传线程
     */
    private void startUpload() {
        // 启动计时线程，开始上传
        uploadHandler.sendEmptyMessage(MSG_UPLOAD);
    }

    private void stopUpload() {
        if (uploadHandler != null) {
            uploadHandler.removeMessages(MSG_UPLOAD);
        }
    }

    /**
     * 发送Split数据
     */
    private void postSplit(final CacheDE cache) {
        RequestParams requestParams = RequestParamsBuilder.buildUploadAntSplitInfoRP(UploadWatcherService.this
                , SPDataApp.getServiceIp(UploadWatcherService.this) + UrlConfig.URL_UPLOAD_AMT_SPLIT,
                cache.info);
        x.http().post(requestParams, new Callback.CommonCallback<BaseRE>() {
            @Override
            public void onSuccess(BaseRE s) {
                if (s.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                    mRepeatTime = 0;
                    delayTime = 100;
                    DBDataCache.delete(cache);
                } else {
                    mRepeatTime++;
                    delayTime += 1000;
                    if (delayTime > DelayTimeMax) {
                        delayTime = DelayTimeMax;
                    }
                    if (mRepeatTime > 5) {
                        DBDataCache.delete(cache);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                delayTime += 1000;
                if (delayTime > DelayTimeMax) {
                    delayTime = DelayTimeMax;
                }
            }

            @Override
            public void onCancelled(CancelledException e) {
            }

            @Override
            public void onFinished() {
                uploading = false;
                uploadHandler.sendEmptyMessageDelayed(MSG_UPLOAD, delayTime);
            }
        });
    }

}
