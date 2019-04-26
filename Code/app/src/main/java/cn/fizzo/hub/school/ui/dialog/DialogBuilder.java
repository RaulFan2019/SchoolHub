package cn.fizzo.hub.school.ui.dialog;

import android.content.Context;

/**
 * Created by Raul.fan on 2017/6/18 0018.
 */

public class DialogBuilder {

    public DialogInput dialogInput;
    public DialogEditHubGroup dialogEditHubGroup;
    public DialogChoice dialogChoice;
    public DialogAskUpdate dialogAskUpdate;


    public void showInputDialog(final Context context, final String title) {
        if (dialogInput == null) {
            dialogInput = new DialogInput(context);
        }
        dialogInput.show(title);
    }

    public void showInputDialog(final Context context, final String title, final String content) {
        if (dialogInput == null) {
            dialogInput = new DialogInput(context);
        }
        dialogInput.show(title,content);
    }

    /**
     * 设置等待对话框的监听器
     *
     * @param listener
     */
    public void setInputDialogListener(DialogInput.onBtnClickListener listener) {
        dialogInput.setListener(listener);
    }


    public void showEditHubGroupDialog(final Context context) {
        if (dialogEditHubGroup == null) {
            dialogEditHubGroup = new DialogEditHubGroup(context);
        }
        dialogEditHubGroup.show();
    }

    /**
     * 设置等待对话框的监听器
     *
     * @param listener
     */
    public void setEditHubGroupDialogListener(DialogEditHubGroup.onBtnClickListener listener) {
        dialogEditHubGroup.setListener(listener);
    }


    /**
     * 显示选择对话框
     *
     * @param context
     * @param title
     * @param confirm
     */
    public void showChoiceDialog(final Context context, final String title, final String confirm) {
        if (dialogChoice == null) {
            dialogChoice = new DialogChoice(context);
        }
        dialogChoice.show(title, confirm);
    }

    /**
     * 设置选择对话框的监听器
     * @param listener
     */
    public void setChoiceDialogListener(DialogChoice.onBtnClickListener listener) {
        dialogChoice.setListener(listener);
    }

    /**
     * 显示选择对话框
     *
     * @param context
     * @param title
     * @param info
     */
    public void showAskUpdateDialog(final Context context, final String title, final String info) {
        if (dialogAskUpdate == null) {
            dialogAskUpdate = new DialogAskUpdate(context);
        }
        dialogAskUpdate.show(title, info);
    }

    /**
     * 设置选择对话框的监听器
     * @param listener
     */
    public void setAskUpdateDialogListener(DialogAskUpdate.onBtnClickListener listener) {
        dialogAskUpdate.setListener(listener);
    }

}
