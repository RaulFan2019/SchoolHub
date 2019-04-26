package cn.fizzo.hub.school.ui.widget.fizzo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.entity.net.GetSportLessonWarningListRE;

/**
 * Created by FW on 2018/3/23.
 */

public class LessonFourAlertMoverView extends LinearLayout {

    TextView tvNo;
    TextView tvName;

    public LessonFourAlertMoverView(Context context) {
        super(context);
    }

    public LessonFourAlertMoverView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_lesson_four_alert_mover, this, true);
        tvNo = (TextView) findViewById(R.id.tv_no);
        tvName = (TextView) findViewById(R.id.tv_name);
    }

    public void updateViewByData(final GetSportLessonWarningListRE.AlertMoversBean moversBean){
        if (moversBean.studentnumber < 10){
            tvNo.setText("0" + moversBean.studentnumber+"");
        }else {
            tvNo.setText(moversBean.studentnumber+"");
        }
        tvName.setText(moversBean.nickname);
    }
}
