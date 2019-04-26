package cn.fizzo.hub.school.entity.net;

import java.util.List;

/**
 * Created by FW on 2018/3/24.
 */

public class GetLessonReportRE {


    /**
     * classname : 五年级2班
     * density : 3.8
     * avg_bpm : 109
     * resthr_intensity : 1.7
     * alert_times : 15
     * bpms : [{"timeoffset":0,"bpm":87},{"timeoffset":2099,"bpm":57}]
     */

    public String classname;
    public double density;
    public int avg_bpm;
    public double resthr_intensity;
    public int alert_times;
    public List<BpmsBean> bpms;

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public int getAvg_bpm() {
        return avg_bpm;
    }

    public void setAvg_bpm(int avg_bpm) {
        this.avg_bpm = avg_bpm;
    }

    public double getResthr_intensity() {
        return resthr_intensity;
    }

    public void setResthr_intensity(double resthr_intensity) {
        this.resthr_intensity = resthr_intensity;
    }

    public int getAlert_times() {
        return alert_times;
    }

    public void setAlert_times(int alert_times) {
        this.alert_times = alert_times;
    }

    public List<BpmsBean> getBpms() {
        return bpms;
    }

    public void setBpms(List<BpmsBean> bpms) {
        this.bpms = bpms;
    }

    public static class BpmsBean {
        /**
         * timeoffset : 0
         * bpm : 87
         */

        public int timeoffset;
        public int bpm;

        public int getTimeoffset() {
            return timeoffset;
        }

        public void setTimeoffset(int timeoffset) {
            this.timeoffset = timeoffset;
        }

        public int getBpm() {
            return bpm;
        }

        public void setBpm(int bpm) {
            this.bpm = bpm;
        }
    }
}
