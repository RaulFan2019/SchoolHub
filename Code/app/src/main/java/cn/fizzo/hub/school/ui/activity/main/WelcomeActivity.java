package cn.fizzo.hub.school.ui.activity.main;

import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import butterknife.BindView;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.service.SendCrashLogService;
import cn.fizzo.hub.school.ui.activity.BaseActivity;

/**
 * Created by Raul.fan on 2017/12/26 0026.
 */

public class WelcomeActivity extends BaseActivity {


    private Animation mRotateAnim;//旋转动画

    /* view */
    @BindView(R.id.v_anim)
    View vAnim;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_welcome;
    }

    @Override
    protected void myHandleMsg(Message msg) {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        mRotateAnim = AnimationUtils.loadAnimation(this, R.anim.anim_main_welcome_rotating);
        mRotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                launchNext();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    @Override
    protected void doMyCreate() {
        startIntentService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        animLaunch();
    }


    @Override
    protected void causeGC() {

    }


    /**
     * 启动一些暂时工作的服务
     */
    private void startIntentService() {
        Intent crashIntent = new Intent(this, SendCrashLogService.class);
        startService(crashIntent);
    }


    private void launchNext() {
        startActivity(MainActivity.class);
    }

    /**
     * 页面启动动画
     */
    private void animLaunch() {
        if (vAnim == null || mRotateAnim == null) {
            launchNext();
            return;
        }
        vAnim.startAnimation(mRotateAnim);
    }

}
