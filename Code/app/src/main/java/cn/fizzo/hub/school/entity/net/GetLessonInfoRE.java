package cn.fizzo.hub.school.entity.net;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Raul.fan on 2018/1/4 0004.
 */

public class GetLessonInfoRE {


    /**
     * updatetime : 2018-01-04 13:00:00
     * lessons : [{"id":1,"lessonname":"体育课","grade":"五","class":"2","starttime":"2018-01-04 13:00:00","duration":2400,"status":1,"reminder":1,"movers":[{"id":10154,"nickname":"raul","avatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJyOmDVPSA0xRHUTL4crlE9XoibXbqv7ybj4lRiaA0ticEtCwyLoxfcOQHL994y1AUKH6gSffu79NLHA/0","gender":1,"weight":65,"birthdate":"1984-12-20","max_hr":185,"rest_hr":60,"target_hr":122,"target_hr_high":166,"device_serialno":"CRA26496MD2KQ","device_macaddr":"C9:92:4F:82:22:55","antplus_serialno":"4926","initial_stepcount":-1,"bindingtime":"2018-01-03 14:06:54"},{"id":26019,"nickname":"沈绉","avatar":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoZ8f8JE9rFnGT3iac4Yn05AOl1zX0VrGtVSGlCiaiax4AIaiaiavKfJmI3CZ4zf7iaFsbkfTKYK62Via2tQ/0","gender":1,"weight":70.5,"birthdate":"1976-04-07","max_hr":179,"rest_hr":60,"target_hr":119,"target_hr_high":161,"device_serialno":"","device_macaddr":"","antplus_serialno":"","initial_stepcount":-1,"bindingtime":""}]}]
     */

    public String updatetime;
    public List<LessonsBean> lessons;

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public List<LessonsBean> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonsBean> lessons) {
        this.lessons = lessons;
    }

    public static class LessonsBean {
        /**
         * id : 1
         * lessonname : 体育课
         * grade : 五
         * class : 2
         * starttime : 2018-01-04 13:00:00
         * duration : 2400
         * status : 1
         * reminder : 1
         * movers : [{"id":10154,"nickname":"raul","avatar":"http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJyOmDVPSA0xRHUTL4crlE9XoibXbqv7ybj4lRiaA0ticEtCwyLoxfcOQHL994y1AUKH6gSffu79NLHA/0","gender":1,"weight":65,"birthdate":"1984-12-20","max_hr":185,"rest_hr":60,"target_hr":122,"target_hr_high":166,"device_serialno":"CRA26496MD2KQ","device_macaddr":"C9:92:4F:82:22:55","antplus_serialno":"4926","initial_stepcount":-1,"bindingtime":"2018-01-03 14:06:54"},{"id":26019,"nickname":"沈绉","avatar":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoZ8f8JE9rFnGT3iac4Yn05AOl1zX0VrGtVSGlCiaiax4AIaiaiavKfJmI3CZ4zf7iaFsbkfTKYK62Via2tQ/0","gender":1,"weight":70.5,"birthdate":"1976-04-07","max_hr":179,"rest_hr":60,"target_hr":119,"target_hr_high":161,"device_serialno":"","device_macaddr":"","antplus_serialno":"","initial_stepcount":-1,"bindingtime":""}]
         */

        public int id;
        public String lessonname;
        public String grade;
        public String classnumber;
        public String starttime;
        public int duration;
        public int status;
        public int reminder;
        public int avg_max_hr;
        public int selected_studentnumber;
        public String classname;
        public int started_seconds;
        public List<MoversBean> movers;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public String getLessonname() {
            return lessonname;
        }

        public void setLessonname(String lessonname) {
            this.lessonname = lessonname;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getClassnumber() {
            return classnumber;
        }

        public void setClassnumber(String classnumber) {
            this.classnumber = classnumber;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getReminder() {
            return reminder;
        }

        public void setReminder(int reminder) {
            this.reminder = reminder;
        }

        public int getAvg_max_hr() {
            return avg_max_hr;
        }

        public void setAvg_max_hr(int avg_max_hr) {
            this.avg_max_hr = avg_max_hr;
        }

        public List<MoversBean> getMovers() {
            return movers;
        }

        public void setMovers(List<MoversBean> movers) {
            this.movers = movers;
        }

        public int getStarted_seconds() {
            return started_seconds;
        }

        public void setStarted_seconds(int started_seconds) {
            this.started_seconds = started_seconds;
        }

        public static class MoversBean implements Serializable{
            /**
             * id : 10154
             * nickname : raul
             * avatar : http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJyOmDVPSA0xRHUTL4crlE9XoibXbqv7ybj4lRiaA0ticEtCwyLoxfcOQHL994y1AUKH6gSffu79NLHA/0
             * gender : 1
             * weight : 65
             * birthdate : 1984-12-20
             * max_hr : 185
             * rest_hr : 60
             * target_hr : 122
             * target_hr_high : 166
             * device_serialno : CRA26496MD2KQ
             * device_macaddr : C9:92:4F:82:22:55
             * antplus_serialno : 4926
             * initial_stepcount : -1
             * bindingtime : 2018-01-03 14:06:54
             */

            public int id;
            public String nickname;
            public String avatar;
            public int gender;
            public int weight;
            public int height;
            public float bmi;
            public String birthdate;
            public int max_hr;
            public int rest_hr;
            public int target_hr;
            public int target_hr_high;
            public int alert_hr;
            public String device_serialno;
            public String device_macaddr;
            public String antplus_serialno;
            public int initial_stepcount;
            public String bindingtime;
            public int studentnumber;


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

            public int getGender() {
                return gender;
            }

            public void setGender(int gender) {
                this.gender = gender;
            }

            public int getWeight() {
                return weight;
            }

            public void setWeight(int weight) {
                this.weight = weight;
            }

            public String getBirthdate() {
                return birthdate;
            }

            public void setBirthdate(String birthdate) {
                this.birthdate = birthdate;
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

            public String getDevice_serialno() {
                return device_serialno;
            }

            public void setDevice_serialno(String device_serialno) {
                this.device_serialno = device_serialno;
            }

            public String getDevice_macaddr() {
                return device_macaddr;
            }

            public void setDevice_macaddr(String device_macaddr) {
                this.device_macaddr = device_macaddr;
            }

            public String getAntplus_serialno() {
                return antplus_serialno;
            }

            public void setAntplus_serialno(String antplus_serialno) {
                this.antplus_serialno = antplus_serialno;
            }

            public int getInitial_stepcount() {
                return initial_stepcount;
            }

            public void setInitial_stepcount(int initial_stepcount) {
                this.initial_stepcount = initial_stepcount;
            }

            public String getBindingtime() {
                return bindingtime;
            }

            public void setBindingtime(String bindingtime) {
                this.bindingtime = bindingtime;
            }

            public int getAlert_hr() {
                return alert_hr;
            }

            public void setAlert_hr(int alert_hr) {
                this.alert_hr = alert_hr;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public float getBmi() {
                return bmi;
            }

            public void setBmi(float bmi) {
                this.bmi = bmi;
            }
        }
    }
}
