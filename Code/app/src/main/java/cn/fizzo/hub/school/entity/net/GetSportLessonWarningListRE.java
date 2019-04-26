package cn.fizzo.hub.school.entity.net;

import java.util.List;

/**
 * Created by FW on 2018/3/19.
 */

public class GetSportLessonWarningListRE {


    /**
     * count : 4
     * alert_movers : [{"studentnumber":2,"id":26019,"nickname":"沈绉","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoZ8f8JE9rFnGT3iac4Yn05AOl1zX0VrGtVSGlCiaiax4AIaiaiavKfJmI3CZ4zf7iaFsbkfTKYK62Via2tQ/132","max_hr":179,"rest_hr":60,"target_hr":119,"target_hr_high":161,"elasped":2005,"cur_bpm":103,"avg_bpm":73,"max_bpm":105,"bpms":[{"timeoffset":2000,"bpm":97},{"timeoffset":2001,"bpm":97},{"timeoffset":2002,"bpm":99},{"timeoffset":2003,"bpm":101},{"timeoffset":2004,"bpm":103}]},{"studentnumber":1,"id":10154,"nickname":"raul","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJyOmDVPSA0xRHUTL4crlE9XoibXbqv7ybj4lRiaA0ticEtCwyLoxfcOQHL994y1AUKH6gSffu79NLHA/132","max_hr":185,"rest_hr":60,"target_hr":122,"target_hr_high":166,"elasped":2005,"cur_bpm":110,"avg_bpm":80,"max_bpm":113,"bpms":[{"timeoffset":2000,"bpm":102},{"timeoffset":2001,"bpm":105},{"timeoffset":2002,"bpm":105},{"timeoffset":2003,"bpm":107},{"timeoffset":2004,"bpm":110}]},{"studentnumber":3,"id":10373,"nickname":"舟溪","avatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLOWRiaXcjicmYiclH5TrIeqNKVThwmvfKHfZCsCnVibnZVoI0R9EcKzl9MHbGnoIYfNibNFich5uqFUObw/132","max_hr":197,"rest_hr":60,"target_hr":130,"target_hr_high":170,"elasped":2005,"cur_bpm":108,"avg_bpm":82,"max_bpm":112,"bpms":[{"timeoffset":2000,"bpm":106},{"timeoffset":2001,"bpm":105},{"timeoffset":2002,"bpm":105},{"timeoffset":2003,"bpm":105},{"timeoffset":2004,"bpm":108}]},{"studentnumber":5,"id":147,"nickname":"James008","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLaeDJAUMnu7JrJKibTESBShPwD0ib4uN3ZrLicPGfjjaWqaCQ8MnbbWiaGpp4VNQed3IW6buUQiaiaffgA/132","max_hr":192,"rest_hr":50,"target_hr":119,"target_hr_high":161,"elasped":2005,"cur_bpm":106,"avg_bpm":98,"max_bpm":128,"bpms":[{"timeoffset":2000,"bpm":109},{"timeoffset":2001,"bpm":107},{"timeoffset":2002,"bpm":106},{"timeoffset":2003,"bpm":106},{"timeoffset":2004,"bpm":106}]}]
     */

    public int count;
    public List<AlertMoversBean> alert_movers;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<AlertMoversBean> getAlert_movers() {
        return alert_movers;
    }

    public void setAlert_movers(List<AlertMoversBean> alert_movers) {
        this.alert_movers = alert_movers;
    }

    public static class AlertMoversBean {
        /**
         * studentnumber : 2
         * id : 26019
         * nickname : 沈绉
         * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoZ8f8JE9rFnGT3iac4Yn05AOl1zX0VrGtVSGlCiaiax4AIaiaiavKfJmI3CZ4zf7iaFsbkfTKYK62Via2tQ/132
         * max_hr : 179
         * rest_hr : 60
         * target_hr : 119
         * target_hr_high : 161
         * elasped : 2005
         * cur_bpm : 103
         * avg_bpm : 73
         * max_bpm : 105
         * bpms : [{"timeoffset":2000,"bpm":97},{"timeoffset":2001,"bpm":97},{"timeoffset":2002,"bpm":99},{"timeoffset":2003,"bpm":101},{"timeoffset":2004,"bpm":103}]
         */

        public int studentnumber;
        public int id;
        public String nickname;
        public String avatar;
        public int max_hr;
        public int rest_hr;
        public int target_hr;
        public int target_hr_high;
        public int alert_hr;
        public int elasped;
        public int cur_bpm;
        public int avg_bpm;
        public int max_bpm;
        public List<BpmsBean> bpms;

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

        public int getMax_hr() {
            return max_hr;
        }

        public void setMax_hr(int max_hr) {
            this.max_hr = max_hr;
        }

        public int getRest_hr() {
            return rest_hr;
        }

        public void setRest_hr(int rest_hr) {
            this.rest_hr = rest_hr;
        }

        public int getTarget_hr() {
            return target_hr;
        }

        public void setTarget_hr(int target_hr) {
            this.target_hr = target_hr;
        }

        public int getTarget_hr_high() {
            return target_hr_high;
        }

        public void setTarget_hr_high(int target_hr_high) {
            this.target_hr_high = target_hr_high;
        }

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

        public int getAlert_hr() {
            return alert_hr;
        }

        public void setAlert_hr(int alert_hr) {
            this.alert_hr = alert_hr;
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

        public static class BpmsBean {
            /**
             * timeoffset : 2000
             * bpm : 97
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
}
