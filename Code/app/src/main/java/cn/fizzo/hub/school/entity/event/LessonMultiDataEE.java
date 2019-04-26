package cn.fizzo.hub.school.entity.event;

import java.util.List;

import cn.fizzo.hub.school.entity.adapter.EffortLessonAE;

/**
 * Created by Raul on 2018/4/23.
 * 多个班级的数据事件
 */
public class LessonMultiDataEE {

    public List<EffortLessonAE> listLesson;

    public LessonMultiDataEE() {
    }

    public LessonMultiDataEE(List<EffortLessonAE> listLesson) {
        super();
        this.listLesson = listLesson;
    }
}
