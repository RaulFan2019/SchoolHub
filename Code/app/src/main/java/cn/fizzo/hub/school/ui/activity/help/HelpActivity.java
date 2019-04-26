package cn.fizzo.hub.school.ui.activity.help;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fizzo.hub.school.LocalApp;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.data.DBData;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.ui.activity.BaseActivity;
import cn.fizzo.hub.school.ui.dialog.DialogBuilder;
import cn.fizzo.hub.school.ui.dialog.DialogChoice;
import cn.fizzo.hub.school.ui.dialog.DialogInput;
import cn.fizzo.hub.school.ui.widget.common.NormalTextView;
import cn.fizzo.hub.school.utils.SystemU;

/**
 * Created by Raul.fan on 2018/1/3 0003.
 */

public class HelpActivity extends BaseActivity {

    private static final String TAG = "HelpActivity";

    /* view */
    @BindView(R.id.tv_version)
    NormalTextView tvVersion;
    @BindView(R.id.tv_hw_version)
    NormalTextView tvHwVersion;
    @BindView(R.id.tv_set_service_ip)
    NormalTextView tvServiceIp;

    /* data */
    private DialogBuilder mDialogBuilder;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_help;
    }

    @Override
    protected void myHandleMsg(Message msg) {

    }

    @OnClick({R.id.ll_version,R.id.ll_hw_version, R.id.ll_device_test, R.id.ll_data_reset,
            R.id.ll_about_us,R.id.ll_data_set_ip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //版本信息
            case R.id.ll_version:
                startActivity(HelpUpdateActivity.class);
                break;
                //硬件信息
            case R.id.ll_hw_version:
                if (LocalApp.getInstance().getHwVer().equals("unKnow")){
                    Toast.makeText(HelpActivity.this,"该固件不能升级",Toast.LENGTH_LONG).show();
                }else {
//                    startActivity(HelpHwUpdateActivity.class);
                }
//                ShellU.execCmd("pm install -r /storage/emulated/0/fizzo/school/download/app-release.apk",true);
//                ShellU.execCmd("reboot",true);
//                String cmd = " arp -n | grep b0:25:aa:1d:93:2b | cut -d ' ' -f 2 | cut -d '(' -f 2 | cut -d ')' -f 1";
//                String cmd = "arp -n | grep b1:25:aa:1d:93:2b | awk -F '[()]' '{print $2}'";
//                ShellU.CommandResult commandResult = ShellU.execCmd(cmd,true);
//                LogU.v(TAG,"ip:" + commandResult.successMsg);
                break;
            //设备调试
            case R.id.ll_device_test:
                startActivity(HelpDebugActivity.class);
                break;
            //数据重置
            case R.id.ll_data_reset:
                showCleanDataDialog();
                break;
            //关于我们
            case R.id.ll_about_us:
                startActivity(HelpAboutUsActivity.class);
                break;
                //设置IP
            case R.id.ll_data_set_ip:
                showSetServiceIpDialog();
                break;
        }
    }

    @Override
    protected void initData() {
        mDialogBuilder = new DialogBuilder();
    }

    @Override
    protected void initViews() {
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(getPackageName(), PackageManager.GET_ACTIVITIES);
            tvVersion.setText(pi.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tvServiceIp.setText(SPDataApp.getServiceIp(HelpActivity.this));

    }

    @Override
    protected void doMyCreate() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        tvHwVersion.setText(LocalApp.getInstance().getHwVer().split("-")[0]);
    }

    @Override
    protected void causeGC() {

    }




    /**
     * 显示是否清除数据的对话框
     */
    private void showCleanDataDialog() {
        mDialogBuilder.showChoiceDialog(HelpActivity.this, "确认数据修复操作？", "确认");
        mDialogBuilder.setChoiceDialogListener(new DialogChoice.onBtnClickListener() {
            @Override
            public void onConfirmBtnClick() {
                cleanInternalDbs();
                SystemU.reboot();
            }

            @Override
            public void onCancelBtnClick() {

            }
        });

    }

    private void showSetServiceIpDialog(){
        mDialogBuilder.showInputDialog(HelpActivity.this,"设置服务器IP",SPDataApp.getServiceIp(HelpActivity.this));
        mDialogBuilder.setInputDialogListener(new DialogInput.onBtnClickListener() {
            @Override
            public void onOkClick(String etStr) {
                SPDataApp.setServiceIp(HelpActivity.this,etStr);
                tvServiceIp.setText(SPDataApp.getServiceIp(HelpActivity.this));
            }
        });
    }

    private void cleanInternalDbs() {
        DBData.cleanAllDBData();
    }


}
