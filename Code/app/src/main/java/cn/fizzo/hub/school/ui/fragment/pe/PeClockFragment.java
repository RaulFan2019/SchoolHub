package cn.fizzo.hub.school.ui.fragment.pe;

import android.os.Message;

import butterknife.BindView;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.ui.widget.common.NumTextView;
import cn.fizzo.hub.school.utils.TimeU;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/25 10:11
 */
public class PeClockFragment extends BasePeFragment {

    public static final int MSG_UPDATE_CLOCK = 0x01;
    public static final int INTERVAL_UPDATE_CLOCK = 1000;

    @BindView(R.id.tv_time_big)
    NumTextView tvTimeBig;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pe_clock;
    }

    @Override
    protected void myHandleMsg(Message msg) {
        switch (msg.what) {
            case MSG_UPDATE_CLOCK:
                tvTimeBig.setText(TimeU.NowTime(TimeU.FORMAT_TYPE_5));
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_CLOCK, INTERVAL_UPDATE_CLOCK);
                break;
        }
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void causeGC() {

    }

    @Override
    protected void onVisible() {
        mHandler.sendEmptyMessage(MSG_UPDATE_CLOCK);
    }

    @Override
    protected void onInVisible() {

    }
}
