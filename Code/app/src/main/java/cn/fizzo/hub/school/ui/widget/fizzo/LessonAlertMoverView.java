package cn.fizzo.hub.school.ui.widget.fizzo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.entity.net.GetSportLessonWarningListRE;
import cn.fizzo.hub.school.ui.widget.common.CircularImage;
import cn.fizzo.hub.school.utils.ImageU;

/**
 * Created by FW on 2018/3/23.
 */

public class LessonAlertMoverView extends LinearLayout {

    CircularImage ivAvatar;
    TextView tvNo;
    TextView tvName;

    public LessonAlertMoverView(Context context) {
        super(context);
    }

    public LessonAlertMoverView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_lesson_alert_mover, this, true);
        ivAvatar = (CircularImage) findViewById(R.id.iv_avatar);
        tvNo = (TextView) findViewById(R.id.tv_no);
        tvName = (TextView) findViewById(R.id.tv_name);
    }

    public void updateViewByData(final GetSportLessonWarningListRE.AlertMoversBean moversBean){
        ImageU.loadUserImage(moversBean.avatar,ivAvatar);
        if (moversBean.studentnumber < 10){
            tvNo.setText("0" + moversBean.studentnumber+"");
        }else {
            tvNo.setText(moversBean.studentnumber+"");
        }
        tvName.setText(moversBean.nickname);
    }
}
