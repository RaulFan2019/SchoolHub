package cn.fizzo.hub.school.entity.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.fizzo.hub.school.entity.net.GetLessonInfoRE;

/**
 * Created by Raul.fan on 2018/1/4 0004.
 */

public class EffortLessonAE {

    public int id;
    public String lessonname;//课程名称
    public String grade;//年级
    public String classnumber;//班级
    public String starttime;//开始时间
    public int duration;//持续时间
    public int status;//状态
    public int reminder;//1.即将开始，2.进行中，3.刚刚结束
    public int lessonMaxHr;//课程最大心率
    public int selectedStudentnumber;
    public int avgHr;//班级平均心率
    public int warningSize;//报警数量
    public String classname;//班级名称
    public int started_seconds;

    public List<Mover> moverList;

    public static class Mover implements Serializable{
        public GetLessonInfoRE.LessonsBean.MoversBean moversRe;
        public int currHr;
        public int currStep;
        public long lastHrTime;
    }

    public EffortLessonAE() {
        moverList = new ArrayList<>();
    }

    public EffortLessonAE(int id, String lessonname, String grade, String classnumber,
                          String starttime, int duration, int status, int reminder,
                          int lessonMaxHr,int selectedStudentnumber,String classname,int started_seconds) {
        this.id = id;
        this.lessonname = lessonname;
        this.grade = grade;
        this.classnumber = classnumber;
        this.starttime = starttime;
        this.duration = duration;
        this.status = status;
        this.reminder = reminder;
        this.lessonMaxHr = lessonMaxHr;
        this.selectedStudentnumber = selectedStudentnumber;
        this.classname = classname;
        this.started_seconds = started_seconds;
        moverList = new ArrayList<>();
        avgHr = 0;
        warningSize = 0;
    }
}
