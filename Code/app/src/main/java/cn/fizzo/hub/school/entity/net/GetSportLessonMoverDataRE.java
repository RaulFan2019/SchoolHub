package cn.fizzo.hub.school.entity.net;

import java.util.List;

/**
 * Created by Raul.fan on 2018/3/12 0012.
 * Mail:raul.fan@139.com
 * QQ: 35686324
 */

public class GetSportLessonMoverDataRE {


    /**
     * cur_bpm : 69
     * avg_bpm : 104
     * max_bpm : 215
     * bpms : [{"timeoffset":4461,"bpm":59},{"timeoffset":4462,"bpm":59},{"timeoffset":4463,"bpm":60},{
     * "timeoffset":4464,"bpm":60},{"timeoffset":4465,"bpm":65},{"timeoffset":4466,"bpm":66},{"timeoffset":4467,"bpm":68},
     * {"timeoffset":4468,"bpm":70},{"timeoffset":4469,"bpm":70},{"timeoffset":4470,"bpm":68},{"timeoffset":4471,"bpm":67},
     * {"timeoffset":4472,"bpm":67},{"timeoffset":4473,"bpm":66},{"timeoffset":4474,"bpm":66},{"timeoffset":4475,"bpm":68},{"timeoffset":4476,"bpm":69},{"timeoffset":4477,"bpm":69},{"timeoffset":4478,"bpm":70},{"timeoffset":4479,"bpm":70},{"timeoffset":4480,"bpm":70},{"timeoffset":4481,"bpm":70},{"timeoffset":4482,"bpm":70},{"timeoffset":4483,"bpm":70},{"timeoffset":4484,"bpm":70}]
     */

    public int cur_bpm;
    public int avg_bpm;
    public int max_bpm;
    public int elasped;
    public float resthr_intensity;
    public float density;//有效密度
    public List<BpmsBean> bpms;
    public List<ExercisesBean> exercises;

    public int getElasped() {
        return elasped;
    }

    public void setElasped(int elasped) {
        this.elasped = elasped;
    }

    public int getCur_bpm() {
        return cur_bpm;
    }

    public void setCur_bpm(int cur_bpm) {
        this.cur_bpm = cur_bpm;
    }

    public int getAvg_bpm() {
        return avg_bpm;
    }

    public void setAvg_bpm(int avg_bpm) {
        this.avg_bpm = avg_bpm;
    }

    public int getMax_bpm() {
        return max_bpm;
    }

    public void setMax_bpm(int max_bpm) {
        this.max_bpm = max_bpm;
    }

    public float getResthr_intensity() {
        return resthr_intensity;
    }

    public void setResthr_intensity(float resthr_intensity) {
        this.resthr_intensity = resthr_intensity;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public List<BpmsBean> getBpms() {
        return bpms;
    }

    public void setBpms(List<BpmsBean> bpms) {
        this.bpms = bpms;
    }

    public List<ExercisesBean> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExercisesBean> exercises) {
        this.exercises = exercises;
    }

    public static class BpmsBean {
        /**
         * timeoffset : 4461
         * bpm : 59
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


    public static class ExercisesBean {
        /**
         * startoffset : 1400
         * finishoffset : 1508
         */

        public int startoffset;
        public int finishoffset;

        public int getStartoffset() {
            return startoffset;
        }

        public void setStartoffset(int startoffset) {
            this.startoffset = startoffset;
        }

        public int getFinishoffset() {
            return finishoffset;
        }

        public void setFinishoffset(int finishoffset) {
            this.finishoffset = finishoffset;
        }
    }
}
