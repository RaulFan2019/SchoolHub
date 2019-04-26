package cn.fizzo.hub.school;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import org.greenrobot.eventbus.EventBus;
import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

import cn.fizzo.hub.school.config.FileConfig;
import cn.fizzo.hub.school.service.AntRealTimeUploadService;
import cn.fizzo.hub.school.service.ConsoleInfoMonitorService;
import cn.fizzo.hub.school.service.UploadWatcherService;
import cn.fizzo.hub.school.utils.CrashU;
import cn.fizzo.hub.school.utils.SerialU;
import cn.fizzo.hub.sdk.Fh;
import cn.fizzo.hub.sdk.observer.NotifyGetHwVerListener;

/**
 * Created by Raul.fan on 2017/12/26 0026.
 */

public class LocalApp extends MultiDexApplication implements NotifyGetHwVerListener {

    public static Context applicationContext;//整个APP的上下文
    private static LocalApp instance;//Application 对象

    private static final String TAG = "LocalApp";

    /* App config */
    public static final boolean SHOW_LOG = true;           //LOG开关
    public static final boolean CATCH_EX = true;            //Crash 捕捉开关
    public static final String DB_NAME = "fizzo.db";        //DB 名称
    public static final int DB_VERSION = 4;                 //DB 版本


    /**
     * 版本信息
     */
    public static final String VersionInfo = "1.重构上课页面";

    /* local data about db */
    DbManager.DaoConfig daoConfig;
    DbManager db;

    /* Device info */
    private String cpuSerial;
    private EventBus eventBus;
    private String hwVer;

    private boolean updateHwNow = false;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        x.Ext.init(this);
        //初始化Fizzo SDK
        Fh.getManager().init(this);
        Fh.getManager().setDebug(true);
        Fh.getManager().registerNotifyGetHwVerListener(this);

        startupExceptionHandler();
        initDB();
        createFileSystem();
        startLocalService();
    }

    /**
     * 获取 LocalApplication
     *
     * @return
     */
    public static LocalApp getInstance() {
        if (instance == null) {
            instance = new LocalApp();
        }
        return instance;
    }

    @Override
    public void notifyGetHwVer(String ver) {
        LocalApp.getInstance().setHwVer(ver);
    }

    /**
     * 捕捉错误日志机制
     */
    private void startupExceptionHandler() {
        if (LocalApp.CATCH_EX) {
            CrashU crashHandler = CrashU.getInstance();
            crashHandler.init(this);
        }
    }

    /**
     * 初始化数据库
     */
    private void initDB() {
        daoConfig = new DbManager.DaoConfig()
                .setDbName(LocalApp.DB_NAME)
                .setDbVersion(LocalApp.DB_VERSION)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });
    }


    /**
     * 创建私有文件目录
     */
    private void createFileSystem() {
        File CrashF = new File(FileConfig.CRASH_PATH);
        if (!CrashF.exists()) {
            CrashF.mkdirs();
        }
        File downloadF = new File(FileConfig.DOWNLOAD_PATH);
        if (!downloadF.exists()) {
            downloadF.mkdirs();
        }
    }


    /**
     * 启动私有服务
     */
    private void startLocalService() {
//        LogU.v("LocalApp","startLocalService");
        Intent ConsoleMonitorIntent = new Intent(this, ConsoleInfoMonitorService.class);
        startService(ConsoleMonitorIntent);
        Intent recentAntIntent = new Intent(this, AntRealTimeUploadService.class);
        startService(recentAntIntent);
        Intent UploadCacheIntent = new Intent(this, UploadWatcherService.class);
        startService(UploadCacheIntent);
    }


    /**
     * 获取数据库操作库
     *
     * @return
     */
    public DbManager getDb() {
        if (daoConfig == null) {
            daoConfig = new DbManager.DaoConfig()
                    .setDbName(LocalApp.DB_NAME)
                    .setDbVersion(LocalApp.DB_VERSION)
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            // 开启WAL
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    });
        }
        if (db == null) {
            db = x.getDb(daoConfig);
        }
        return db;
    }

    public void setHwVer(String hwVer) {
        this.hwVer = hwVer;
    }

    public boolean isUpdateHwNow() {
        return updateHwNow;
    }

    public void setUpdateHwNow(boolean updateHwNow) {
        this.updateHwNow = updateHwNow;
    }

    /**
     * 获取设备CPU信息
     *
     * @return
     */
    public String getHwVer() {
        if (hwVer == null) {
            hwVer = "unKnow";
        }
        return hwVer;
    }

    /**
     * 获取设备CPU信息
     *
     * @return
     */
    public String getCpuSerial() {
        if (cpuSerial == null) {
            cpuSerial = SerialU.getCpuSerial();
        }
        return cpuSerial;
    }

    /**
     * 获取EventBus
     *
     * @return
     */
    public EventBus getEventBus() {
        if (eventBus == null) {
            eventBus = EventBus.builder()
                    .sendNoSubscriberEvent(false)
                    .logNoSubscriberMessages(false)
                    .build();
        }
        return eventBus;
    }
}
