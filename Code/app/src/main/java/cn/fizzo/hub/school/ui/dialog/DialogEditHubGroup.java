package cn.fizzo.hub.school.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import cn.fizzo.hub.school.R;

/**
 * Created by Raul.fan on 2017/9/14 0014.
 */

public class DialogEditHubGroup {


    public Dialog mDialog;

    Button btnJoin;
    Button btnEditName;
    Button btnDelete;


    onBtnClickListener mListener;


    public interface onBtnClickListener {
        void onJoinClick();

        void onEditNameClick();

        void onDeleteClick();
    }


    /**
     * 初始化
     *
     * @param context
     */
    public DialogEditHubGroup(Context context) {
        mDialog = new Dialog(context, R.style.DialogTheme);
        mDialog.setContentView(R.layout.dialog_edit_hub_group);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);

        btnJoin = (Button) mDialog.findViewById(R.id.btn_join);
        btnEditName = (Button) mDialog.findViewById(R.id.btn_edit_name);
        btnDelete = (Button) mDialog.findViewById(R.id.btn_delete);

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onJoinClick();
                    mDialog.dismiss();
                }
            }
        });

        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onEditNameClick();
                    mDialog.dismiss();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDeleteClick();
                    mDialog.dismiss();
                }
            }
        });
    }

    /**
     * 显示
     */
    public void show() {
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
