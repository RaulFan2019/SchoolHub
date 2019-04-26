package cn.fizzo.hub.school.ui.activity.help;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Message;

import butterknife.BindView;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.ui.activity.BaseActivity;
import cn.fizzo.hub.school.ui.widget.common.NormalTextView;

/**
 * Created by Raul.fan on 2018/1/5 0005.
 */

public class HelpAboutUsActivity extends BaseActivity {


    @BindView(R.id.tv_version)
    NormalTextView tvVersion;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_help_about_us;
    }

    @Override
    protected void myHandleMsg(Message msg) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            tvVersion.setText("Ver:" + pi.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doMyCreate() {

    }

    @Override
    protected void causeGC() {

    }


}
