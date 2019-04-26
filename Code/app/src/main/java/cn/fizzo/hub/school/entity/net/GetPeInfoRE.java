package cn.fizzo.hub.school.entity.net;

import java.util.List;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/25 15:54
 */
public class GetPeInfoRE {

    public int started_duration;
    public AllStudentsBean all_students;
    public String alert_students;
    public LessonBasicDataBean lesson_basic_data;
    public DelegatesDataBean delegates_data;
    public SelectedStudentBean selected_student;
    public ClazzBpmsBean class_bpms;

    public int getStarted_duration() {
        return started_duration;
    }

    public void setStarted_duration(int started_duration) {
        this.started_duration = started_duration;
    }

    public AllStudentsBean getAll_students() {
        return all_students;
    }

    public void setAll_students(AllStudentsBean all_students) {
        this.all_students = all_students;
    }

    public String getAlert_students() {
        return alert_students;
    }

    public void setAlert_students(String alert_students) {
        this.alert_students = alert_students;
    }

    public LessonBasicDataBean getLesson_basic_data() {
        return lesson_basic_data;
    }

    public void setLesson_basic_data(LessonBasicDataBean lesson_basic_data) {
        this.lesson_basic_data = lesson_basic_data;
    }

    public DelegatesDataBean getDelegates_data() {
        return delegates_data;
    }

    public void setDelegates_data(DelegatesDataBean delegates_data) {
        this.delegates_data = delegates_data;
    }

    public SelectedStudentBean getSelected_student() {
        return selected_student;
    }

    public void setSelected_student(SelectedStudentBean selected_student) {
        this.selected_student = selected_student;
    }

    public ClazzBpmsBean getClass_bpms() {
        return class_bpms;
    }

    public void setClass_bpms(ClazzBpmsBean class_bpms) {
        this.class_bpms = class_bpms;
    }

    public static class AllStudentsBean {
        public String number;
        public String bpm;
        public String step_count;
        public String step_frequency;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getBpm() {
            return bpm;
        }

        public void setBpm(String bpm) {
            this.bpm = bpm;
        }

        public String getStep_count() {
            return step_count;
        }

        public void setStep_count(String step_count) {
            this.step_count = step_count;
        }

        public String getStep_frequency() {
            return step_frequency;
        }

        public void setStep_frequency(String step_frequency) {
            this.step_frequency = step_frequency;
        }
    }

    public static class LessonBasicDataBean {
        public double resthr_intensity;
        public int density;
        public int bpm_avg;
        public int alert_times;

        public double getResthr_intensity() {
            return resthr_intensity;
        }

        public void setResthr_intensity(double resthr_intensity) {
            this.resthr_intensity = resthr_intensity;
        }

        public int getDensity() {
            return density;
        }

        public void setDensity(int density) {
            this.density = density;
        }

        public int getBpm_avg() {
            return bpm_avg;
        }

        public void setBpm_avg(int bpm_avg) {
            this.bpm_avg = bpm_avg;
        }

        public int getAlert_times() {
            return alert_times;
        }

        public void setAlert_times(int alert_times) {
            this.alert_times = alert_times;
        }
    }

    public static class DelegatesDataBean {
        public int offset_to;
        public List<DelegatesBean> delegates;

        public int getOffset_to() {
            return offset_to;
        }

        public void setOffset_to(int offset_to) {
            this.offset_to = offset_to;
        }

        public List<DelegatesBean> getDelegates() {
            return delegates;
        }

        public void setDelegates(List<DelegatesBean> delegates) {
            this.delegates = delegates;
        }

        public static class DelegatesBean {
            public int number;
            public int bpm;
            public double resthr_intensity;
            public List<BpmsBean> bpms;
            public List<ExercisesBean> exercises;

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getBpm() {
                return bpm;
            }

            public void setBpm(int bpm) {
                this.bpm = bpm;
            }

            public double getResthr_intensity() {
                return resthr_intensity;
            }

            public void setResthr_intensity(double resthr_intensity) {
                this.resthr_intensity = resthr_intensity;
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

        }
    }

    public static class SelectedStudentBean {
        public int bpm;
        public int bpm_max;
        public int bpm_avg;
        public int density;
        public double resthr_intensity;
        public int offset_to;
        public List<BpmsBean> bpms;
        public List<ExercisesBean> exercises;

        public int getBpm() {
            return bpm;
        }

        public void setBpm(int bpm) {
            this.bpm = bpm;
        }

        public int getBpm_max() {
            return bpm_max;
        }

        public void setBpm_max(int bpm_max) {
            this.bpm_max = bpm_max;
        }

        public int getBpm_avg() {
            return bpm_avg;
        }

        public void setBpm_avg(int bpm_avg) {
            this.bpm_avg = bpm_avg;
        }

        public int getDensity() {
            return density;
        }

        public void setDensity(int density) {
            this.density = density;
        }

        public double getResthr_intensity() {
            return resthr_intensity;
        }

        public void setResthr_intensity(double resthr_intensity) {
            this.resthr_intensity = resthr_intensity;
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
    }

    public static class BpmsBean {
        public int offset;
        public int bpm;

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public int getBpm() {
            return bpm;
        }

        public void setBpm(int bpm) {
            this.bpm = bpm;
        }
    }

    public static class ExercisesBean {
        public int offset_from;
        public int offset_to;

        public int getOffset_from() {
            return offset_from;
        }

        public void setOffset_from(int offset_from) {
            this.offset_from = offset_from;
        }

        public int getOffset_to() {
            return offset_to;
        }

        public void setOffset_to(int offset_to) {
            this.offset_to = offset_to;
        }
    }

    public static class ClazzBpmsBean{
        public int offset_to;
        public List<BpmsBean> bpms;

        public int getOffset_to() {
            return offset_to;
        }

        public void setOffset_to(int offset_to) {
            this.offset_to = offset_to;
        }

        public List<BpmsBean> getBpms() {
            return bpms;
        }

        public void setBpms(List<BpmsBean> bpms) {
            this.bpms = bpms;
        }
    }
}
