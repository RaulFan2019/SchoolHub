package cn.fizzo.hub.school.entity.adapter;

import cn.fizzo.hub.school.entity.net.GetPeStatusRE;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/25 17:13
 */
public class PeWallStudentAE {

    public GetPeStatusRE.LessonsBean.StudentsBean basicInfo;
    public int bpm;
    public String stepCount;

    public PeWallStudentAE() {
    }

    public PeWallStudentAE(GetPeStatusRE.LessonsBean.StudentsBean basicInfo) {
        this.basicInfo = basicInfo;
    }

    public void updateSportData(int bpm, String stepCount) {
        this.bpm = bpm;
        this.stepCount = stepCount;
    }
}
