package cn.fizzo.hub.school.entity.event;

/**
 * Created by Raul.Fan on 2016/11/22.
 */

public class AntDebugEE {

    public int hr;//心率
    public String serialNo;//设备号
    public int rssi;
    public int cadence;//步频
    public int step;//步数

    public AntDebugEE() {
    }

    public AntDebugEE(int hr, String serialNo, int cadence, int step, int rssi) {
        super();
        this.hr = hr;
        this.serialNo = serialNo;
        this.rssi = rssi;
        this.cadence = cadence;
        this.step = step;
    }
}
