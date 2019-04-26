package cn.fizzo.hub.school.entity.net;

import java.util.List;

/**
 * Created by Raul.fan on 2017/9/15 0015.
 */

public class GetGroupAntInfoRE {


    public List<AntbpmsBean> antbpms;

    public List<AntbpmsBean> getAntbpms() {
        return antbpms;
    }

    public void setAntbpms(List<AntbpmsBean> antbpms) {
        this.antbpms = antbpms;
    }

    public static class AntbpmsBean {
        /**
         * antsn : 4926
         * bpm : 50
         */

        public String antsn;
        public int bpm;
        public int stepcount;

        public String getAntsn() {
            return antsn;
        }

        public void setAntsn(String antsn) {
            this.antsn = antsn;
        }

        public int getBpm() {
            return bpm;
        }

        public void setBpm(int bpm) {
            this.bpm = bpm;
        }

        public int getStepcount() {
            return stepcount;
        }

        public void setStepcount(int stepcount) {
            this.stepcount = stepcount;
        }
    }
}
