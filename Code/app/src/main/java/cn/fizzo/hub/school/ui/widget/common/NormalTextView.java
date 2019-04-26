package cn.fizzo.hub.school.ui.widget.common;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Raul.fan on 2018/1/2 0002.
 */

public class NormalTextView extends TextView {

    protected Typeface tfNormal;

    public NormalTextView(Context context) {
        super(context);
        tfNormal = Typeface.createFromAsset(context.getAssets(), "fonts/tvNormal.TTF");
        this.setTypeface(tfNormal);
    }

    public NormalTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tfNormal = Typeface.createFromAsset(context.getAssets(), "fonts/tvNormal.TTF");
        this.setTypeface(tfNormal);
    }

    public NormalTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tfNormal = Typeface.createFromAsset(context.getAssets(), "fonts/tvNormal.TTF");
        this.setTypeface(tfNormal);
    }

}
