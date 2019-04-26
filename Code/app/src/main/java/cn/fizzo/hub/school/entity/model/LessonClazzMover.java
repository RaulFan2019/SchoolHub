package cn.fizzo.hub.school.entity.model;

/**
 * Created by FW on 2018/3/23.
 */

public class LessonClazzMover {

    public String avatar;
    public String nickname;
    public int studentnumber;
    public int cur_bpm;
    public double resthr_intensity;
    public int avg_bpm;
    public int max_bpm;
    public int restHr;

    public LessonClazzMover() {
    }

    public LessonClazzMover(String avatar, String nickname, int studentnumber, int cur_bpm,
                            double resthr_intensity, int avg_bpm, int max_bpm,int restHr) {
        this.avatar = avatar;
        this.nickname = nickname;
        this.studentnumber = studentnumber;
        this.cur_bpm = cur_bpm;
        this.resthr_intensity = resthr_intensity;
        this.avg_bpm = avg_bpm;
        this.max_bpm = max_bpm;
        this.restHr = restHr;
    }
}
