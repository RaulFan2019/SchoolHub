package cn.fizzo.hub.school.entity.net;

import java.io.Serializable;
import java.util.List;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/20 10:34
 */
public class GetPeStatusRE {


    /**
     * update_time : 2019-03-21 14:52:23
     * lessons : [{"id":2983,"class_name":"第五区","page_status":1,"select_number":0,"fizzo_box_id":17,"students":[{"id":10153,"number":1,"nickname":"程晓伟-Weil","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKgGCPd19I412R7iaHsKI1HpfBw3E3BE3RK6gHZHlrTuLHFenCC9AYw4q31ibCmAAHXuiaGeC8wTFqicQ/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":116,"target_hr_high":154,"bpm_alert":false},{"id":17718,"number":2,"nickname":"handsome","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIJXliaFPmyIyia4tlaQZicYribvca7R7oFD5IIqReg9PxkOiagib2msiasf6qic5ialawcTzURlCL1RUf3zMw/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":118,"target_hr_high":159,"bpm_alert":false},{"id":147,"number":3,"nickname":"庞金鹏","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLaeDJAUMnu7JrJKibTESBShPwD0ib4uN3ZrLicPGfjjaWqaCQ8MnbbWiaGpp4VNQed3IW6buUQiaiaffgA/132","gender":1,"bmi":0,"bpm_rest":73,"bpm_motion":119,"target_hr_high":161,"bpm_alert":false},{"id":26019,"number":4,"nickname":"沈绉","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eopQJIUv51fVUgvvOkAs6rlK5rgdA9GmroTEhzzbtPlniabWymUYicVh4VxOvssicvibsBnhP3ps6aGHA/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":119,"target_hr_high":161,"bpm_alert":false},{"id":10373,"number":5,"nickname":"舟溪","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLOWRiaXcjicmYiclH5TrIeqNKVThwmvfKHfZCsCnVibnZVoI0R9EcKzl9MibPiaSPETKALK05FibB4Obukw/132","gender":2,"bmi":0,"bpm_rest":60,"bpm_motion":130,"target_hr_high":170,"bpm_alert":false},{"id":32341,"number":6,"nickname":"Aileen Zhang","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL6EicLXHeU06UBfibnqTjRIkAAGPQmibw6w4F3971iaSYDcldT3590I7ajmxvvicMC4dibI9xPiaCZlAibGg/132","gender":2,"bmi":0,"bpm_rest":135,"bpm_motion":122,"target_hr_high":165,"bpm_alert":false},{"id":10154,"number":7,"nickname":"raul","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJyOmDVPSA0xRHUTL4crlE9XoibXbqv7ybj4lRiaA0ticEtCwyLoxfcOQHiauBicBwDWOlpnjich5UFZ3sQ/132","gender":2,"bmi":0,"bpm_rest":60,"bpm_motion":122,"target_hr_high":165,"bpm_alert":false},{"id":15879,"number":8,"nickname":"高旻昱","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLu0fVxKVhOSUt64PQEg6p4KGcBtQcvyYVgPSgbXLBKuRfWZPa5QuneWvDCCnzrkbCkSL7Fq8ORcw/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":125,"target_hr_high":171,"bpm_alert":false},{"id":42917,"number":9,"nickname":"Weber Cheng","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epYzUMRdvOYxbPrFLDa2DEySUGicBpBPeruDc5f45V96TnNDmSCM1gA50kNF606Z6MSXLByvycJMQQ/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":120,"target_hr_high":162,"bpm_alert":false},{"id":45191,"number":10,"nickname":"勤劳的小蜜蜂～","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/SyXHtsta2P1d1FXib28DyWgjHSD0iaNnicyhVicbG64Kar7FztKfMGB0ibR9lYibTvte8qBicLCQH59qricrAzNyB7pewQ/132","gender":2,"bmi":0,"bpm_rest":60,"bpm_motion":125,"target_hr_high":171,"bpm_alert":false},{"id":23075,"number":11,"nickname":"夏贤月","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eq9IXTJkFxMVzouYgpgibuR6dQGib7ibjVBgJ0vfqbhP8JEIDKT8GmjkpOw9hnjBZ4OIlCib1ZvK5BMQw/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":125,"target_hr_high":171,"bpm_alert":false},{"id":10958,"number":12,"nickname":"是倩莹啊","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIaN8PM23YgDZ5FbGSib29CI3ubV5VvegT18XyxZuYeOg1ibMkPFruicdA0zcMfxtcdWCBqZbRK3r76A/132","gender":2,"bmi":0,"bpm_rest":60,"bpm_motion":126,"target_hr_high":173,"bpm_alert":false},{"id":55017,"number":13,"nickname":"吴永前","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/2WukRpoicB7XT0ObiaaGC8vhAvvtl4rrxWQoDfITczZ8RllWPc3rqSZDboeK0JqBBo3VaYdWpxDs6eKe7rh9zXXg/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":120,"target_hr_high":162,"bpm_alert":false}]}]
     */

    public String update_time;
    public List<LessonsBean> lessons;

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public List<LessonsBean> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonsBean> lessons) {
        this.lessons = lessons;
    }

    public static class LessonsBean {
        /**
         * id : 2983
         * class_name : 第五区
         * page_status : 1
         * select_number : 0
         * fizzo_box_id : 17
         * students : [{"id":10153,"number":1,"nickname":"程晓伟-Weil","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKgGCPd19I412R7iaHsKI1HpfBw3E3BE3RK6gHZHlrTuLHFenCC9AYw4q31ibCmAAHXuiaGeC8wTFqicQ/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":116,"target_hr_high":154,"bpm_alert":false},{"id":17718,"number":2,"nickname":"handsome","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIJXliaFPmyIyia4tlaQZicYribvca7R7oFD5IIqReg9PxkOiagib2msiasf6qic5ialawcTzURlCL1RUf3zMw/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":118,"target_hr_high":159,"bpm_alert":false},{"id":147,"number":3,"nickname":"庞金鹏","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLaeDJAUMnu7JrJKibTESBShPwD0ib4uN3ZrLicPGfjjaWqaCQ8MnbbWiaGpp4VNQed3IW6buUQiaiaffgA/132","gender":1,"bmi":0,"bpm_rest":73,"bpm_motion":119,"target_hr_high":161,"bpm_alert":false},{"id":26019,"number":4,"nickname":"沈绉","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eopQJIUv51fVUgvvOkAs6rlK5rgdA9GmroTEhzzbtPlniabWymUYicVh4VxOvssicvibsBnhP3ps6aGHA/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":119,"target_hr_high":161,"bpm_alert":false},{"id":10373,"number":5,"nickname":"舟溪","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLOWRiaXcjicmYiclH5TrIeqNKVThwmvfKHfZCsCnVibnZVoI0R9EcKzl9MibPiaSPETKALK05FibB4Obukw/132","gender":2,"bmi":0,"bpm_rest":60,"bpm_motion":130,"target_hr_high":170,"bpm_alert":false},{"id":32341,"number":6,"nickname":"Aileen Zhang","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTL6EicLXHeU06UBfibnqTjRIkAAGPQmibw6w4F3971iaSYDcldT3590I7ajmxvvicMC4dibI9xPiaCZlAibGg/132","gender":2,"bmi":0,"bpm_rest":135,"bpm_motion":122,"target_hr_high":165,"bpm_alert":false},{"id":10154,"number":7,"nickname":"raul","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJyOmDVPSA0xRHUTL4crlE9XoibXbqv7ybj4lRiaA0ticEtCwyLoxfcOQHiauBicBwDWOlpnjich5UFZ3sQ/132","gender":2,"bmi":0,"bpm_rest":60,"bpm_motion":122,"target_hr_high":165,"bpm_alert":false},{"id":15879,"number":8,"nickname":"高旻昱","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLu0fVxKVhOSUt64PQEg6p4KGcBtQcvyYVgPSgbXLBKuRfWZPa5QuneWvDCCnzrkbCkSL7Fq8ORcw/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":125,"target_hr_high":171,"bpm_alert":false},{"id":42917,"number":9,"nickname":"Weber Cheng","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epYzUMRdvOYxbPrFLDa2DEySUGicBpBPeruDc5f45V96TnNDmSCM1gA50kNF606Z6MSXLByvycJMQQ/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":120,"target_hr_high":162,"bpm_alert":false},{"id":45191,"number":10,"nickname":"勤劳的小蜜蜂～","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/SyXHtsta2P1d1FXib28DyWgjHSD0iaNnicyhVicbG64Kar7FztKfMGB0ibR9lYibTvte8qBicLCQH59qricrAzNyB7pewQ/132","gender":2,"bmi":0,"bpm_rest":60,"bpm_motion":125,"target_hr_high":171,"bpm_alert":false},{"id":23075,"number":11,"nickname":"夏贤月","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eq9IXTJkFxMVzouYgpgibuR6dQGib7ibjVBgJ0vfqbhP8JEIDKT8GmjkpOw9hnjBZ4OIlCib1ZvK5BMQw/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":125,"target_hr_high":171,"bpm_alert":false},{"id":10958,"number":12,"nickname":"是倩莹啊","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIaN8PM23YgDZ5FbGSib29CI3ubV5VvegT18XyxZuYeOg1ibMkPFruicdA0zcMfxtcdWCBqZbRK3r76A/132","gender":2,"bmi":0,"bpm_rest":60,"bpm_motion":126,"target_hr_high":173,"bpm_alert":false},{"id":55017,"number":13,"nickname":"吴永前","avatar":"http://thirdwx.qlogo.cn/mmopen/vi_32/2WukRpoicB7XT0ObiaaGC8vhAvvtl4rrxWQoDfITczZ8RllWPc3rqSZDboeK0JqBBo3VaYdWpxDs6eKe7rh9zXXg/132","gender":1,"bmi":0,"bpm_rest":60,"bpm_motion":120,"target_hr_high":162,"bpm_alert":false}]
         */

        public int id;
        public String class_name;
        public int page_status;
        public int select_number;
        public int fizzo_box_id;
        public String delegates_number;
        public int planned_duration;
        public List<StudentsBean> students;
        public List<SegmentsBean> segments;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public int getPage_status() {
            return page_status;
        }

        public void setPage_status(int page_status) {
            this.page_status = page_status;
        }

        public int getSelect_number() {
            return select_number;
        }

        public void setSelect_number(int select_number) {
            this.select_number = select_number;
        }

        public int getFizzo_box_id() {
            return fizzo_box_id;
        }

        public void setFizzo_box_id(int fizzo_box_id) {
            this.fizzo_box_id = fizzo_box_id;
        }

        public List<StudentsBean> getStudents() {
            return students;
        }

        public void setStudents(List<StudentsBean> students) {
            this.students = students;
        }

        public int getPlanned_duration() {
            return planned_duration;
        }

        public void setPlanned_duration(int planned_duration) {
            this.planned_duration = planned_duration;
        }

        public String getDelegates_number() {
            return delegates_number;
        }

        public void setDelegates_number(String delegates_number) {
            this.delegates_number = delegates_number;
        }

        public List<SegmentsBean> getSegments() {
            return segments;
        }

        public void setSegments(List<SegmentsBean> segments) {
            this.segments = segments;
        }

        public static class StudentsBean implements Serializable{
            /**
             * id : 10153
             * number : 1
             * nickname : 程晓伟-Weil
             * avatar : http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKgGCPd19I412R7iaHsKI1HpfBw3E3BE3RK6gHZHlrTuLHFenCC9AYw4q31ibCmAAHXuiaGeC8wTFqicQ/132
             * gender : 1
             * bmi : 0
             * bpm_rest : 60
             * bpm_motion : 116
             * target_hr_high : 154
             * bpm_alert : false
             */

            public int id;
            public int number;
            public String nickname;
            public String avatar;
            public int gender;
            public int bmi;
            public int bpm_rest;
            public int bpm_motion;
            public int bpm_alert;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
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

            public int getBmi() {
                return bmi;
            }

            public void setBmi(int bmi) {
                this.bmi = bmi;
            }

            public int getBpm_rest() {
                return bpm_rest;
            }

            public void setBpm_rest(int bpm_rest) {
                this.bpm_rest = bpm_rest;
            }

            public int getBpm_motion() {
                return bpm_motion;
            }

            public void setBpm_motion(int bpm_motion) {
                this.bpm_motion = bpm_motion;
            }

            public int isBpm_alert() {
                return bpm_alert;
            }

            public void setBpm_alert(int bpm_alert) {
                this.bpm_alert = bpm_alert;
            }
        }

        public static class SegmentsBean {
            public int offset;
            public String name;

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
