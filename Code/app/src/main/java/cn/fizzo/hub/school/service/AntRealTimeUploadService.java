package cn.fizzo.hub.school.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import cn.fizzo.hub.school.LocalApp;
import cn.fizzo.hub.school.config.UrlConfig;
import cn.fizzo.hub.school.data.DBDataCache;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.entity.db.CacheDE;
import cn.fizzo.hub.school.entity.model.MinAntUpdateME;
import cn.fizzo.hub.school.entity.net.BaseRE;
import cn.fizzo.hub.school.network.RequestParamsBuilder;
import cn.fizzo.hub.school.utils.TimeU;
import cn.fizzo.hub.sdk.Fh;
import cn.fizzo.hub.sdk.entity.AntPlusInfo;
import cn.fizzo.hub.sdk.observer.NotifyNewAntsListener;

/**
 * Created by Raul.fan on 2018/1/5 0005.
 * 实时上传当前心率信息的服务
 */
public class AntRealTimeUploadService extends Service implements NotifyNewAntsListener {

    private static final String TAG = "AntRealTimeUploadService";
    private static final boolean DEBUG = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //计时器
    private static Handler mTimerHandler = null;
    private static Runnable mTimerRa = null;
    private static long startTime;
    private static long NextTime = 0;

    private List<AntPlusInfo> listAnts = new ArrayList<>();
    private List<MinAntUpdateME> listMinAnts = Collections.synchronizedList(new ArrayList<MinAntUpdateME>());


    @Override
    public void notifyAnts(List<AntPlusInfo> ants) {
        int sec = (int) (System.currentTimeMillis() / 1000 % 60);//秒数
        for (AntPlusInfo antInfo : ants){
            //过滤心率小于45的不正常值
            if (antInfo.hr < 45){
                continue;
            }
            //加入每秒上传列表
            listAnts.add(antInfo);
            boolean hasFound = false;
            //在每分钟上传列表中找已存在
            for (MinAntUpdateME minAntUpdateME : listMinAnts) {
                //找到了
                if (minAntUpdateME.serialNo.equals(antInfo.serialNo)) {
                    if (minAntUpdateME.startCount == 0) {
                        minAntUpdateME.startCount = antInfo.step;
                    }
                    minAntUpdateME.endCount = antInfo.step;
                    minAntUpdateME.antInfoList.add(
                            new MinAntUpdateME.AntInfo(sec, antInfo.hr, antInfo.cadence));
                    hasFound = true;
                    break;
                }
            }
            //没找到,新增
            if (!hasFound) {
                listMinAnts.add(new MinAntUpdateME(antInfo.serialNo, antInfo.step, antInfo.step,
                        sec, antInfo.hr, antInfo.cadence));
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
        initTimer();
        Fh.getManager().registerNotifyNewAntsListener(this);
        startTime = System.currentTimeMillis();
        mTimerRa.run();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Fh.getManager().unRegisterNotifyNewAntsListener(this);
        super.onDestroy();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        listAnts.clear();
    }

    /**
     * 初始化Timer
     */
    private void initTimer() {
        //计时器
        mTimerHandler = new Handler();
        mTimerRa = new Runnable() {
            @Override
            public void run() {
                updateTimer();
            }
        };
        mTimerHandler.removeCallbacks(mTimerRa);
    }

    private void updateTimer() {
        final long now = SystemClock.uptimeMillis();
        if (NextTime == now + (1000 - now % 1000)) {
            return;
        }
        NextTime = now + (1000 - now % 1000);
        mTimerHandler.postAtTime(mTimerRa, NextTime);

        //若有心率数据的时候上传
        if(listAnts != null && listAnts.size() > 0){
            final List<AntPlusInfo> mTmpList = new ArrayList<>();
            mTmpList.addAll(listAnts);
            listAnts.clear();
            x.task().post(new Runnable() {
                @Override
                public void run() {
                    saveTimerData(mTmpList);
                }
            });
        }

        //整分钟
        final long realNow = System.currentTimeMillis();
        if (realNow / 1000 % 60 == 0) {
            x.task().post(new Runnable() {
                @Override
                public void run() {
                    saveMinTimeData(realNow);
                }
            });
        }
    }


    /**
     * 存储这个时刻的心率
     */
    private void saveTimerData(List<AntPlusInfo> tmpList) {
//        LogU.v(DEBUG,TAG,"saveTimerData");
        String antString = "[";
        String bpmString = "[";
        String stepString = "[";
        String cadenceString = "[";
        String rssiString = "[";
        for (AntPlusInfo plusInfo : tmpList) {
            antString += "\"" + plusInfo.serialNo + "\",";
            bpmString += plusInfo.hr + ",";
            stepString += plusInfo.step + ",";
            cadenceString += plusInfo.cadence + ",";
            rssiString += plusInfo.rssi + ",";
        }
        if (bpmString.length() > 2) {
            antString = antString.substring(0, antString.length() - 1);
            bpmString = bpmString.substring(0, bpmString.length() - 1);
            stepString = stepString.substring(0, stepString.length() - 1);
            cadenceString = cadenceString.substring(0, cadenceString.length() - 1);
            rssiString = rssiString.substring(0,rssiString.length() -1);
        }
        antString += "]";
        bpmString += "]";
        stepString += "]";
        cadenceString += "]";
        rssiString += "]";
        RequestParams params = RequestParamsBuilder.buildRealTimeUploadAntRP(AntRealTimeUploadService.this,
                SPDataApp.getServiceIp(AntRealTimeUploadService.this) + UrlConfig.URL_UPLOAD_RECENT_HR,
                antString, bpmString, stepString,cadenceString,rssiString);

        x.http().post(params, new Callback.CommonCallback<BaseRE>() {
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


    /**
     * 保存每分钟的数据
     *
     * @param nextTime
     */
    private void saveMinTimeData(long nextTime) {
        long updateTime = nextTime - 1000 * 60;
        String updateTimeStr = TimeU.formatDateToStr(new Date(updateTime), TimeU.FORMAT_TYPE_1);
        for (MinAntUpdateME minAntUpdateME : listMinAnts) {
            //若这一分钟有数据
            if (minAntUpdateME.antInfoList.size() > 0) {
                JSONObject updateObj = new JSONObject();
                updateObj.put("console_sn", LocalApp.getInstance().getCpuSerial());
                updateObj.put("starttime", updateTimeStr);
                updateObj.put("antplus_serialno", minAntUpdateME.serialNo);
                updateObj.put("start_stepcount", minAntUpdateME.startCount);
                updateObj.put("end_stepcount", minAntUpdateME.endCount);
//                String bpmString = "[";
//                for (MinAntUpdateME.AntInfo antInfo : minAntUpdateME.antInfoList) {
//                    bpmString += "[" + antInfo.offset + "," + antInfo.hr + "," + antInfo.cadence + "],";
//                }
//                if (bpmString.length() > 2) {
//                    bpmString = bpmString.substring(0, bpmString.length() - 1);
//                }
//                bpmString += "]";
                updateObj.put("bpms", "[]");

                String data = updateObj.toJSONString();
                CacheDE cacheDE = new CacheDE(CacheDE.TYPE_ANT_SPLIT, data);
                DBDataCache.save(cacheDE);

                minAntUpdateME.startCount = 0;
                minAntUpdateME.antInfoList.clear();
            }
        }
    }



}
