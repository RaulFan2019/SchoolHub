package cn.fizzo.hub.school.entity.event;

import cn.fizzo.hub.school.entity.adapter.EffortLessonAE;

/**
 * Created by Raul.fan on 2018/1/8 0008.
 */

public class SportLessonDataEE {

    public EffortLessonAE lessonAE;
    public int currAvgHr;
    public int warningSize;

    public SportLessonDataEE() {
    }

    public SportLessonDataEE(EffortLessonAE lessonAE, int currAvgHr,int warningSize) {
        super();
        this.lessonAE = lessonAE;
        this.currAvgHr = currAvgHr;
        this.warningSize = warningSize;
    }
}
