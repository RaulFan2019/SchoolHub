package cn.fizzo.hub.school.entity.model;

/**
 * Created by FW on 2018/3/26.
 */

public class LessonMover {

    public float bmi;
    public String avatar;
    public String nickname;
    public int studentnumber;
    public int rest_hr;


    public LessonMover() {
    }

    public LessonMover(float bmi, String avatar, String nickname, int studentnumber, int rest_hr) {
        this.bmi = bmi;
        this.avatar = avatar;
        this.nickname = nickname;
        this.studentnumber = studentnumber;
        this.rest_hr = rest_hr;
    }
}
