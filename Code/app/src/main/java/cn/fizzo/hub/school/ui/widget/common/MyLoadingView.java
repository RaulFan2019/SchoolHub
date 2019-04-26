package cn.fizzo.hub.school.ui.widget.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import cn.fizzo.hub.school.R;

/**
 * Created by Raul.fan on 2017/7/20 0020.
 */

public class MyLoadingView extends LinearLayout {


    View vAnim;
    NormalTextView tvLoading;

    RotateAnimation rotateAnimation;

    public MyLoadingView(Context context) {
        super(context);
    }

    public MyLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_loading_data, this, true);

        vAnim = findViewById(R.id.v_anim);
        tvLoading = (NormalTextView) findViewById(R.id.tv_loading);
        Typeface tfNormal = Typeface.createFromAsset(context.getAssets(), "fonts/tvNormal.TTF");
        tvLoading.setTypeface(tfNormal);

        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(context, R.anim.anim_common_rotating);
        Loading();
    }

    /**
     * 加载
     */
    public void Loading() {
        vAnim.setAnimation(rotateAnimation);
        rotateAnimation.start();
    }

    /**
     * 加载错误
     *
     * @param error
     */
    public void LoadError(final String error) {
        //TODO
    }

    /**
     * 加载结束
     */
    public void loadFinish() {
        rotateAnimation.cancel();
        vAnim.clearAnimation();
        this.setVisibility(View.GONE);
    }

}
