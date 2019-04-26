package cn.fizzo.hub.school.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.ui.widget.common.NormalTextView;


/**
 * Created by Raul.fan on 2017/10/10 0010.
 */

public class DialogChoice {

    public Dialog mDialog;

    NormalTextView tvTitle;
    Button btnConfirm;
    Button btnCancel;

    onBtnClickListener mListener;


    public interface onBtnClickListener {
        void onConfirmBtnClick();

        void onCancelBtnClick();
    }


    /**
     * 初始化
     *
     * @param context
     */
    public DialogChoice(Context context) {
        mDialog = new Dialog(context, R.style.DialogTheme);
        mDialog.setContentView(R.layout.dialog_choice);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);

        tvTitle = (NormalTextView) mDialog.findViewById(R.id.tv_title);
        btnConfirm = (Button) mDialog.findViewById(R.id.btn_confirm);
        btnCancel = (Button) mDialog.findViewById(R.id.btn_cancel);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onConfirmBtnClick();
                    mDialog.dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onCancelBtnClick();
                    mDialog.dismiss();
                }
            }
        });
    }

    /**
     * 显示
     */
    public void show(final String title, final String confirm) {
        tvTitle.setText(title);
        btnConfirm.setText(confirm);
        mDialog.show();
    }


    /**
     * 设置监听器
     *
     * @param listener
     */
    public void setListener(onBtnClickListener listener) {
        mListener = listener;
    }


}
