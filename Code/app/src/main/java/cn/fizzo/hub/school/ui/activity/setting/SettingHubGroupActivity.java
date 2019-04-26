package cn.fizzo.hub.school.ui.activity.setting;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.config.UrlConfig;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.entity.net.BaseRE;
import cn.fizzo.hub.school.entity.net.GetHubGroupRE;
import cn.fizzo.hub.school.network.BaseResponseParser;
import cn.fizzo.hub.school.network.HttpExceptionHelper;
import cn.fizzo.hub.school.network.RequestParamsBuilder;
import cn.fizzo.hub.school.ui.activity.BaseActivity;
import cn.fizzo.hub.school.ui.adapter.SettingHubGroupListAdapter;
import cn.fizzo.hub.school.ui.dialog.DialogBuilder;
import cn.fizzo.hub.school.ui.dialog.DialogEditHubGroup;
import cn.fizzo.hub.school.ui.dialog.DialogInput;
import cn.fizzo.hub.school.ui.widget.common.MyLoadingView;

/**
 * Created by Raul.fan on 2018/1/9 0009.
 */

public class SettingHubGroupActivity extends BaseActivity {


    /* contains */
    private static final int MSG_GET_LIST_OK = 0x01;
    private static final int MSG_GET_LIST_ERROR = 0x02;
    private static final int MSG_CREATE_GROUP_OK = 0x03;
    private static final int MSG_CREATE_GROUP_ERROR = 0x04;
    private static final int MSG_JOIN_GROUP_OK = 0x05;
    private static final int MSG_JOIN_GROUP_ERROR = 0x06;
    private static final int MSG_DELETE_GROUP_OK = 0x07;
    private static final int MSG_DELETE_GROUP_ERROR = 0x08;
    private static final int MSG_EDIT_GROUP_NAME_OK = 0x09;
    private static final int MSG_EDIT_GROUP_NAME_ERROR = 0x10;


    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.v_loading)
    MyLoadingView vLoading;


    /* data */
    private List<GetHubGroupRE.GroupsBean> listGroup = new ArrayList<>();
    private DialogBuilder mDialogBuilder;
    private SettingHubGroupListAdapter mAdapter;

    private GetHubGroupRE.GroupsBean mCurrActionGroup;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_hub_group;
    }

    @Override
    protected void myHandleMsg(Message msg) {
        switch (msg.what) {
            case MSG_GET_LIST_ERROR:
                Toast.makeText(SettingHubGroupActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                vLoading.loadFinish();
                finish();
                break;
            case MSG_GET_LIST_OK:
                vLoading.loadFinish();
                mAdapter.notifyDataSetChanged();
                lv.requestFocus();
                break;
            case MSG_CREATE_GROUP_ERROR:
                Toast.makeText(SettingHubGroupActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                break;
            case MSG_CREATE_GROUP_OK:
                mAdapter.notifyDataSetChanged();
                break;
            case MSG_JOIN_GROUP_ERROR:
                Toast.makeText(SettingHubGroupActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                break;
            case MSG_JOIN_GROUP_OK:
                mAdapter.notifyDataSetChanged();
                break;
            case MSG_DELETE_GROUP_ERROR:
                Toast.makeText(SettingHubGroupActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                break;
            case MSG_DELETE_GROUP_OK:
                mAdapter.notifyDataSetChanged();
                break;
            case MSG_EDIT_GROUP_NAME_ERROR:
                Toast.makeText(SettingHubGroupActivity.this, (String) msg.obj, Toast.LENGTH_LONG).show();
                break;
            case MSG_EDIT_GROUP_NAME_OK:
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    @OnClick(R.id.ll_create)
    public void onViewClicked() {
        mDialogBuilder.showInputDialog(SettingHubGroupActivity.this, "设置HUB组名称");
        mDialogBuilder.setInputDialogListener(new DialogInput.onBtnClickListener() {
            @Override
            public void onOkClick(String etStr) {
                postCreateHUBGroup(etStr);
            }
        });
    }


    @Override
    protected void initData() {
        mDialogBuilder = new DialogBuilder();
    }

    @Override
    protected void initViews() {
        mAdapter = new SettingHubGroupListAdapter(SettingHubGroupActivity.this, listGroup);
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrActionGroup = listGroup.get(position);
                mDialogBuilder.showEditHubGroupDialog(SettingHubGroupActivity.this);
                mDialogBuilder.setEditHubGroupDialogListener(new DialogEditHubGroup.onBtnClickListener() {
                    @Override
                    public void onJoinClick() {
                        postJoinGroup();
                    }

                    @Override
                    public void onEditNameClick() {
                        if (mCurrActionGroup.id != 0) {
                            showEditGroupNameDialog();
                        } else {
                            Toast.makeText(SettingHubGroupActivity.this, "此组不能修改", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onDeleteClick() {
                        if (mCurrActionGroup.id != 0) {
                            postDeleteGroup();
                        } else {
                            Toast.makeText(SettingHubGroupActivity.this, "此组不能删除", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void doMyCreate() {
        postGetGroupList();
    }

    @Override
    protected void causeGC() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (listGroup != null){
            listGroup.clear();
        }
    }

    /**
     * 创建HUB组
     */
    private void postCreateHUBGroup(final String groupName) {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                RequestParams params = RequestParamsBuilder.buildSettingHubGroupCreateRP(SettingHubGroupActivity.this,
                        SPDataApp.getServiceIp(SettingHubGroupActivity.this) + UrlConfig.URL_CREATE_HUB_GROUP,
                        groupName);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                            GetHubGroupRE re = JSON.parseObject(result.result, GetHubGroupRE.class);
                            listGroup.clear();
                            listGroup.addAll(re.groups);
                            mHandler.sendEmptyMessage(MSG_CREATE_GROUP_OK);
                        } else {
                            Message msg = new Message();
                            msg.what = MSG_CREATE_GROUP_ERROR;
                            msg.obj = result.errormsg;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Message msg = new Message();
                        msg.what = MSG_CREATE_GROUP_ERROR;
                        msg.obj = HttpExceptionHelper.getErrorMsg(ex);
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }


    /**
     * 加入HUB组
     */
    private void postJoinGroup() {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                RequestParams params = RequestParamsBuilder.buildSettingHubGroupJoinRP(SettingHubGroupActivity.this,
                        SPDataApp.getServiceIp(SettingHubGroupActivity.this) + UrlConfig.URL_JOIN_HUB_GROUP,
                        mCurrActionGroup.id);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                            GetHubGroupRE re = JSON.parseObject(result.result, GetHubGroupRE.class);
                            listGroup.clear();
                            listGroup.addAll(re.groups);
                            mHandler.sendEmptyMessage(MSG_JOIN_GROUP_OK);
                        } else {
                            Message msg = new Message();
                            msg.what = MSG_JOIN_GROUP_ERROR;
                            msg.obj = result.errormsg;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Message msg = new Message();
                        msg.what = MSG_JOIN_GROUP_ERROR;
                        msg.obj = HttpExceptionHelper.getErrorMsg(ex);
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }

    /**
     * 修改HUB组名称
     *
     * @param name
     */
    private void postEditGroupName(final String name) {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                RequestParams params = RequestParamsBuilder.buildSettingHubGroupEditNameRP(SettingHubGroupActivity.this,
                        SPDataApp.getServiceIp(SettingHubGroupActivity.this) + UrlConfig.URL_EDIT_HUB_GROUP_NAME,
                        mCurrActionGroup.id, name);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                            GetHubGroupRE re = JSON.parseObject(result.result, GetHubGroupRE.class);
                            listGroup.clear();
                            listGroup.addAll(re.groups);
                            mHandler.sendEmptyMessage(MSG_EDIT_GROUP_NAME_OK);
                        } else {
                            Message msg = new Message();
                            msg.what = MSG_EDIT_GROUP_NAME_ERROR;
                            msg.obj = result.errormsg;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Message msg = new Message();
                        msg.what = MSG_EDIT_GROUP_NAME_ERROR;
                        msg.obj = HttpExceptionHelper.getErrorMsg(ex);
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }


    /**
     * 删除组
     */
    private void postDeleteGroup() {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                RequestParams params = RequestParamsBuilder.buildSettingHubGroupDeleteRP(SettingHubGroupActivity.this,
                        SPDataApp.getServiceIp(SettingHubGroupActivity.this) + UrlConfig.URL_DELETE_HUB_GROUP,
                        mCurrActionGroup.id);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                            GetHubGroupRE re = JSON.parseObject(result.result, GetHubGroupRE.class);
                            listGroup.clear();
                            listGroup.addAll(re.groups);
                            mHandler.sendEmptyMessage(MSG_DELETE_GROUP_OK);
                        } else {
                            Message msg = new Message();
                            msg.what = MSG_DELETE_GROUP_ERROR;
                            msg.obj = result.errormsg;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Message msg = new Message();
                        msg.what = MSG_DELETE_GROUP_ERROR;
                        msg.obj = HttpExceptionHelper.getErrorMsg(ex);
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }

    /**
     * 获取HUB组列表
     */
    private void postGetGroupList() {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                RequestParams params = RequestParamsBuilder.buildSettingHubGroupGetListRP(SettingHubGroupActivity.this,
                        SPDataApp.getServiceIp(SettingHubGroupActivity.this) + UrlConfig.URL_GET_HUB_GROUP_LIST);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                            GetHubGroupRE re = JSON.parseObject(result.result, GetHubGroupRE.class);
                            listGroup.clear();
                            listGroup.addAll(re.groups);
                            mHandler.sendEmptyMessage(MSG_GET_LIST_OK);
                        } else {
                            Message msg = new Message();
                            msg.what = MSG_GET_LIST_ERROR;
                            msg.obj = result.errormsg;
                            mHandler.sendMessage(msg);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Message msg = new Message();
                        msg.what = MSG_GET_LIST_ERROR;
                        msg.obj = HttpExceptionHelper.getErrorMsg(ex);
                        mHandler.sendMessage(msg);
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });
    }


    /**
     * 现实修改组名的对话框
     */
    private void showEditGroupNameDialog() {
        mDialogBuilder.showInputDialog(SettingHubGroupActivity.this, "设置HUB组名称");
        mDialogBuilder.setInputDialogListener(new DialogInput.onBtnClickListener() {
            @Override
            public void onOkClick(String etStr) {
                if (etStr.equals("")) {
                    Toast.makeText(SettingHubGroupActivity.this, "名称不能为空",Toast.LENGTH_LONG).show();
                } else {
                    postEditGroupName(etStr);
                }
            }
        });
    }

}
