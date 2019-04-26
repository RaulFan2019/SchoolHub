package cn.fizzo.hub.school.entity.model;

/**
 * Created by Raul.fan on 2018/1/1 0001.
 */

public class CrashLog {

    public String type;
    public String info;
    public String model;
    public String os;
    public String app;
    public String content;
    public int logLevel;

    public CrashLog() {
    }

    public CrashLog(String type, String info, String model, String os, String app,
                    String content ,int logLevel) {
        super();
        this.type = type;
        this.info = info;
        this.model = model;
        this.os = os;
        this.app = app;
        this.content = content;
        this.logLevel = logLevel;
    }
}
