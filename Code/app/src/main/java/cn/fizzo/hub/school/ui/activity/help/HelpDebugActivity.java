package cn.fizzo.hub.school.ui.activity.help;

import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.fizzo.hub.school.LocalApp;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.data.DBDataConsole;
import cn.fizzo.hub.school.data.DBDataStore;
import cn.fizzo.hub.school.data.SPDataStore;
import cn.fizzo.hub.school.entity.db.ConsoleDE;
import cn.fizzo.hub.school.entity.db.StoreDE;
import cn.fizzo.hub.school.ui.activity.BaseActivity;
import cn.fizzo.hub.school.ui.widget.common.CircularImage;
import cn.fizzo.hub.school.ui.widget.common.NormalTextView;
import cn.fizzo.hub.school.utils.ImageU;
import cn.fizzo.hub.sdk.Fh;
import cn.fizzo.hub.sdk.entity.AntPlusInfo;
import cn.fizzo.hub.sdk.observer.NotifyNewAntsListener;

/**
 * Created by Raul.fan on 2018/1/5 0005.
 * 调试页面
 */
public class HelpDebugActivity extends BaseActivity implements NotifyNewAntsListener {


    private static final int MSG_SHOW_NO_SIGN = 0x01;

    private static final int INTERVAL_NO_SIGN = 5 * 1000;


    @BindView(R.id.iv_store)
    CircularImage ivStore;
    @BindView(R.id.tv_store_name)
    NormalTextView tvStoreName;
    @BindView(R.id.tv_hub)
    NormalTextView tvHub;
    @BindView(R.id.tv_service)
    NormalTextView tvService;
    @BindView(R.id.tv_serial_no)
    NormalTextView tvSerialNo;
    @BindView(R.id.tv_sign)
    NormalTextView tvSign;

    private StoreDE mStoreDe;
    private ConsoleDE mConsoleDe;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_debug;
    }

    private List<ShowHr> mShowHrList = new ArrayList<>();

    /**
     * 消息事件
     */
    @Override
    protected void myHandleMsg(Message msg) {
        switch (msg.what) {
            //更新时钟
            case MSG_SHOW_NO_SIGN:
                tvSign.setText("NO SIGN");
                mShowHrList.clear();
                mHandler.sendEmptyMessageDelayed(MSG_SHOW_NO_SIGN, INTERVAL_NO_SIGN);
                break;

        }
    }

    @Override
    public void notifyAnts(List<AntPlusInfo> ants) {
        for (AntPlusInfo antPlusInfo : ants) {
            boolean found = false;
            //检查列表中是否存在
            for (ShowHr showHr : mShowHrList) {
                if (showHr.ant.equals(antPlusInfo.serialNo)) {
                    showHr.hr = antPlusInfo.hr;
                    showHr.rssi = antPlusInfo.rssi;
                    showHr.step = antPlusInfo.step;
                    found = true;
                    break;
                }
            }
            //不存在就新增
            if (!found) {
                ShowHr show = new ShowHr(antPlusInfo.serialNo, antPlusInfo.hr, antPlusInfo.rssi, antPlusInfo.step);
                mShowHrList.add(show);
            }
        }
        showHrList();
    }

    @Override
    protected void initData() {
        mStoreDe = DBDataStore.getStoreInfo(SPDataStore.getStoreId(HelpDebugActivity.this));
        mConsoleDe = DBDataConsole.getConsoleInfo();
    }

    @Override
    protected void initViews() {
        ImageU.loadStoreImage(mStoreDe.logo, ivStore);

        tvHub.setText(LocalApp.getInstance().getCpuSerial());
        if (mStoreDe != null) {
            tvStoreName.setText(mStoreDe.name);
            tvSerialNo.setText(LocalApp.getInstance().getCpuSerial().substring(LocalApp.getInstance().getCpuSerial().length() -8));
        }
    }

    @Override
    protected void doMyCreate() {
        mHandler.sendEmptyMessageDelayed(MSG_SHOW_NO_SIGN, INTERVAL_NO_SIGN);
        Fh.getManager().registerNotifyNewAntsListener(this);
    }

    @Override
    protected void causeGC() {
        Fh.getManager().unRegisterNotifyNewAntsListener(this);
        mHandler.removeCallbacksAndMessages(null);
        mShowHrList.clear();
    }



    private void showHrList() {
        String showString = "";
        for (ShowHr showHr : mShowHrList) {
            showString += showHr.ant + " hr:" + showHr.hr + ",step:" + showHr.step + ",rssi:" + showHr.rssi + "\n";
        }
        tvSign.setText(showString);
        mHandler.removeMessages(MSG_SHOW_NO_SIGN);
        mHandler.sendEmptyMessageDelayed(MSG_SHOW_NO_SIGN, INTERVAL_NO_SIGN);
    }


    class ShowHr {
        public String ant;
        public int hr;
        public int rssi;
        public int step;

        public ShowHr(String ant, int hr, int rssi, int step) {
            this.ant = ant;
            this.hr = hr;
            this.rssi = rssi;
            this.step = step;
        }
    }
}
