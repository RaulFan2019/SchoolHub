package cn.fizzo.hub.school.ui.widget.common;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Raul.fan on 2018/1/2 0002.
 */

public class NumTextView extends TextView {


    private Typeface tfNum;

    public NumTextView(Context context) {
        super(context);
        tfNum = Typeface.createFromAsset(context.getAssets(), "fonts/tvNum.otf");
        this.setTypeface(tfNum);
    }

    public NumTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        tfNum = Typeface.createFromAsset(context.getAssets(), "fonts/tvNum.otf");
        this.setTypeface(tfNum);
    }

    public NumTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tfNum = Typeface.createFromAsset(context.getAssets(), "fonts/tvNum.otf");
        this.setTypeface(tfNum);
    }
}
