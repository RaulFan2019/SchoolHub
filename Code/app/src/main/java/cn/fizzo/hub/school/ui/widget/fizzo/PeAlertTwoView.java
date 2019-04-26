package cn.fizzo.hub.school.ui.widget.fizzo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.entity.net.GetPeStatusRE;


/**
 * Created by FW on 2018/3/23.
 */

public class PeAlertTwoView extends LinearLayout {

    TextView tvNo;
    TextView tvName;

    public PeAlertTwoView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_pe_alert_two, this, true);
        tvNo = (TextView) findViewById(R.id.tv_no);
        tvName = (TextView) findViewById(R.id.tv_name);
    }

    public PeAlertTwoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_pe_alert_two, this, true);
        tvNo = (TextView) findViewById(R.id.tv_no);
        tvName = (TextView) findViewById(R.id.tv_name);

    }

    public void updateViewByData(final GetPeStatusRE.LessonsBean.StudentsBean moversBean) {
        if (moversBean.number < 10) {
            tvNo.setText("0" + moversBean.number + "");
        } else {
            tvNo.setText(moversBean.number + "");
        }
        tvName.setText("/" + moversBean.nickname);
    }
}
