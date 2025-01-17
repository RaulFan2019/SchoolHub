package cn.fizzo.hub.school.ui.widget.common;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;

/**
 * Created by Raul.Fan on 2017/4/11.
 */

public class SpacingNormalTextView extends android.support.v7.widget.AppCompatTextView {

    private float letterSpacing = LetterSpacing.NORMALBIG;
    private CharSequence originalText = "";

    protected Typeface tfNormal;

    public SpacingNormalTextView(Context context) {
        super(context);
        tfNormal = Typeface.createFromAsset(context.getAssets(), "fonts/tvNormal.TTF");
        this.setTypeface(tfNormal);
    }

    public SpacingNormalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        originalText = super.getText();
        applyLetterSpacing();
        tfNormal = Typeface.createFromAsset(context.getAssets(), "fonts/tvNormal.TTF");
        this.setTypeface(tfNormal);
        this.invalidate();
    }

    public SpacingNormalTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        tfNormal = Typeface.createFromAsset(context.getAssets(), "fonts/tvNormal.TTF");
        this.setTypeface(tfNormal);
    }

    public float getLetterSpacing() {
        return letterSpacing;
    }

    public void setLetterSpacing(float letterSpacing) {
        this.letterSpacing = letterSpacing;
        applyLetterSpacing();
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        originalText = text;
        applyLetterSpacing();
    }

    @Override
    public CharSequence getText() {
        return originalText;
    }

    /**
     * 字距为任何字符串（技术上，一个简单的方法为CharSequence不使用）的TextView
     */
    private void applyLetterSpacing() {
        if (this == null || this.originalText == null) return;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < originalText.length(); i++) {
            String c = "" + originalText.charAt(i);
            builder.append(c);
            if (i + 1 < originalText.length()) {
                builder.append("\u00A0");
            }
        }
        SpannableString finalText = new SpannableString(builder.toString());
        if (builder.toString().length() > 1) {
            for (int i = 1; i < builder.toString().length(); i += 2) {
                finalText.setSpan(new ScaleXSpan((letterSpacing + 1) / 100), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        super.setText(finalText, BufferType.SPANNABLE);
    }

    public class LetterSpacing {
        public final static float NORMAL = 0;
        public final static float NORMALBIG = (float) 0.025;
        public final static float BIG = (float) 0.05;
        public final static float BIGGEST = (float) 0.2;
    }
}
