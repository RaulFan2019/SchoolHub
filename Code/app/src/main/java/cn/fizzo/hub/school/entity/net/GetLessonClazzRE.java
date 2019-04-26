package cn.fizzo.hub.school.entity.net;

import java.util.List;

/**
 * Created by FW on 2018/3/23.
 */

public class GetLessonClazzRE {


    /**
     * boy : {"studentnumber":2,"id":26019,"nickname":"沈绉","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoZ8f8JE9rFnGT3iac4Yn05AOl1zX0VrGtVSGlCiaiax4AIaiaiavKfJmI3CZ4zf7iaFsbkfTKYK62Via2tQ/132","rest_hr":60,"bmi":22.4,"cur_bpm":51,"resthr_intensity":0.8,"avg_bpm":68,"max_bpm":122,"bpms":[{"timeoffset":1500,"bpm":56},{"timeoffset":1508,"bpm":51}]}
     * girl : {"studentnumber":3,"id":10373,"nickname":"舟溪","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLOWRiaXcjicmYiclH5TrIeqNKVThwmvfKHfZCsCnVibnZVoI0R9EcKzl9MYvOTNOdDRicX2GEBRMztxdQ/132","rest_hr":83,"bmi":28.2,"cur_bpm":74,"resthr_intensity":1.2,"avg_bpm":64,"max_bpm":131,"bpms":[{"timeoffset":1500,"bpm":75},{"timeoffset":1508,"bpm":74}]}
     * elasped : 1509
     * density : 2.5
     */

    public BoyBean boy;
    public GirlBean girl;
    public int elasped;
    public float density;

    public BoyBean getBoy() {
        return boy;
    }

    public void setBoy(BoyBean boy) {
        this.boy = boy;
    }

    public GirlBean getGirl() {
        return girl;
    }

    public void setGirl(GirlBean girl) {
        this.girl = girl;
    }

    public int getElasped() {
        return elasped;
    }

    public void setElasped(int elasped) {
        this.elasped = elasped;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public static class BoyBean {
        /**
         * studentnumber : 2
         * id : 26019
         * nickname : 沈绉
         * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoZ8f8JE9rFnGT3iac4Yn05AOl1zX0VrGtVSGlCiaiax4AIaiaiavKfJmI3CZ4zf7iaFsbkfTKYK62Via2tQ/132
         * rest_hr : 60
         * bmi : 22.4
         * cur_bpm : 51
         * resthr_intensity : 0.8
         * avg_bpm : 68
         * max_bpm : 122
         * bpms : [{"timeoffset":1500,"bpm":56},{"timeoffset":1508,"bpm":51}]
         */

        public int studentnumber;
        public int id;
        public String nickname;
        public String avatar;
        public int rest_hr;
        public double bmi;
        public int cur_bpm;
        public double resthr_intensity;
        public int avg_bpm;
        public int max_bpm;
        public List<BpmsBean> bpms;
        public List<ExercisesBean> exercises;


        public List<ExercisesBean> getExercises() {
            return exercises;
        }

        public void setExercises(List<ExercisesBean> exercises) {
            this.exercises = exercises;
        }

        public int getStudentnumber() {
            return studentnumber;
        }

        public void setStudentnumber(int studentnumber) {
            this.studentnumber = studentnumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getRest_hr() {
            return rest_hr;
        }

        public void setRest_hr(int rest_hr) {
            this.rest_hr = rest_hr;
        }

        public double getBmi() {
            return bmi;
        }

        public void setBmi(double bmi) {
            this.bmi = bmi;
        }

        public int getCur_bpm() {
            return cur_bpm;
        }

        public void setCur_bpm(int cur_bpm) {
            this.cur_bpm = cur_bpm;
        }

        public double getResthr_intensity() {
            return resthr_intensity;
        }

        public void setResthr_intensity(double resthr_intensity) {
            this.resthr_intensity = resthr_intensity;
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

        public List<BpmsBean> getBpms() {
            return bpms;
        }

        public void setBpms(List<BpmsBean> bpms) {
            this.bpms = bpms;
        }
    }

    public static class GirlBean {
        /**
         * studentnumber : 3
         * id : 10373
         * nickname : 舟溪
         * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLOWRiaXcjicmYiclH5TrIeqNKVThwmvfKHfZCsCnVibnZVoI0R9EcKzl9MYvOTNOdDRicX2GEBRMztxdQ/132
         * rest_hr : 83
         * bmi : 28.2
         * cur_bpm : 74
         * resthr_intensity : 1.2
         * avg_bpm : 64
         * max_bpm : 131
         * bpms : [{"timeoffset":1500,"bpm":75},{"timeoffset":1508,"bpm":74}]
         */

        public int studentnumber;
        public int id;
        public String nickname;
        public String avatar;
        public int rest_hr;
        public double bmi;
        public int cur_bpm;
        public double resthr_intensity;
        public int avg_bpm;
        public int max_bpm;
        public List<BpmsBean> bpms;
        public List<ExercisesBean> exercises;

        public List<ExercisesBean> getExercises() {
            return exercises;
        }

        public void setExercises(List<ExercisesBean> exercises) {
            this.exercises = exercises;
        }

        public int getStudentnumber() {
            return studentnumber;
        }

        public void setStudentnumber(int studentnumber) {
            this.studentnumber = studentnumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getRest_hr() {
            return rest_hr;
        }

        public void setRest_hr(int rest_hr) {
            this.rest_hr = rest_hr;
        }

        public double getBmi() {
            return bmi;
        }

        public void setBmi(double bmi) {
            this.bmi = bmi;
        }

        public int getCur_bpm() {
            return cur_bpm;
        }

        public void setCur_bpm(int cur_bpm) {
            this.cur_bpm = cur_bpm;
        }

        public double getResthr_intensity() {
            return resthr_intensity;
        }

        public void setResthr_intensity(double resthr_intensity) {
            this.resthr_intensity = resthr_intensity;
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

        public List<BpmsBean> getBpms() {
            return bpms;
        }

        public void setBpms(List<BpmsBean> bpms) {
            this.bpms = bpms;
        }
    }

    public static class BpmsBean {
        /**
         * timeoffset : 1500
         * bpm : 56
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
         * startoffset : 926
         * finishoffset : 1673
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
