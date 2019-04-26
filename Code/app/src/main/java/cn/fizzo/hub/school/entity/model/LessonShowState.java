package cn.fizzo.hub.school.entity.model;

/**
 * Created by Raul on 2018/4/23.
 */

public class LessonShowState {

    public int state;
    public boolean showAlert;

    public LessonShowState(int state, boolean showAlert) {
        this.state = state;
        this.showAlert = showAlert;
    }
}
