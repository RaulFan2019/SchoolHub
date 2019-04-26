package cn.fizzo.hub.school.ui.activity.main;

import android.graphics.RectF;
import android.os.Message;
import android.view.View;

import com.open.androidtvwidget.bridge.RecyclerViewBridge;
import com.open.androidtvwidget.leanback.recycle.LinearLayoutManagerTV;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.MainUpView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.config.SPEnums;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.entity.model.MainMenuItem;
import cn.fizzo.hub.school.ui.activity.BaseActivity;
import cn.fizzo.hub.school.ui.activity.help.HelpActivity;
import cn.fizzo.hub.school.ui.activity.pe.PeActivity;
import cn.fizzo.hub.school.ui.activity.setting.SettingHubGroupActivity;
import cn.fizzo.hub.school.ui.adapter.MainMenuRvAdapter;
import cn.fizzo.hub.school.ui.adapter.MainMenuRvPresenter;
import cn.fizzo.hub.school.utils.NetworkU;
import cn.fizzo.hub.sdk.Fh;

/**
 * Created by Raul.fan on 2017/12/31 0031.
 */

public class MainActivity extends BaseActivity implements RecyclerViewTV.OnItemListener {

    private static final String TAG = "MainActivity";

    /* msg */
    private static final int MSG_REQUEST_MENU_FOCUS = 0x01;

    /* View */
    @BindView(R.id.rv_menu)
    RecyclerViewTV rvMenu;
    @BindView(R.id.upView)
    MainUpView upView;

    private MainMenuRvPresenter mRecyclerViewPresenter;
    private MainMenuRvAdapter adapter;//adapter

    private RecyclerViewBridge mRecyclerViewBridge;
    private View oldView;

    /* data */
    private List<MainMenuItem> listMenuItems = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void myHandleMsg(Message msg) {
        switch (msg.what) {
            case MSG_REQUEST_MENU_FOCUS:
                rvMenu.requestFocus();
                break;
        }
    }

    @Override
    protected void initData() {
        listMenuItems.add(new MainMenuItem(R.drawable.ic_main_lesson, "课程锻炼"));
        listMenuItems.add(new MainMenuItem(R.drawable.ic_main_wifi, "WIFI设置"));
        listMenuItems.add(new MainMenuItem(R.drawable.ic_main_hub_group, "HUB组设置"));
        listMenuItems.add(new MainMenuItem(R.drawable.ic_main_help, "帮助"));
    }

    @Override
    protected void initViews() {
        initBridge();
        initRv();
    }

    @Override
    protected void doMyCreate() {
        mHandler.sendEmptyMessageDelayed(MSG_REQUEST_MENU_FOCUS, 500);
//        LocalApp.getInstance().getEventBus().register(this);

        //若崩溃之前是自由锻炼页面
        if (SPDataApp.getLastPageBeforeCrash(MainActivity.this) == SPEnums.PAGE_SPORT_PE) {
//            startActivity(LessonMultiActivity.class);
            startActivity(PeActivity.class);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Fh.getManager().getHwVersion();
        SPDataApp.setLastPageBeforeCrash(MainActivity.this, SPEnums.PAGE_MAIN);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void causeGC() {
//        LocalApp.getInstance().getEventBus().unregister(this);
    }


    private void initBridge() {
        upView.setEffectBridge(new RecyclerViewBridge());
        // 注意这里，需要使用 RecyclerViewBridge 的移动边框 Bridge.
        mRecyclerViewBridge = (RecyclerViewBridge) upView.getEffectBridge();
        mRecyclerViewBridge.setUpRectResource(android.R.color.transparent);
        RectF receF = new RectF(0, 0, 0, 0);
        mRecyclerViewBridge.setDrawUpRectPadding(receF);
    }

    private void initRv() {
        LinearLayoutManagerTV gridlayoutManager = new LinearLayoutManagerTV(this); // 解决快速长按焦点丢失问题.
        gridlayoutManager.setOrientation(LinearLayoutManagerTV.HORIZONTAL);
        rvMenu.setLayoutManager(gridlayoutManager);
        rvMenu.setFocusable(false);
        rvMenu.setSelectedItemAtCentered(true); // 设置item在中间移动.
        mRecyclerViewPresenter = new MainMenuRvPresenter(listMenuItems);
        adapter = new MainMenuRvAdapter(mRecyclerViewPresenter);
        rvMenu.setAdapter(adapter);
        rvMenu.setOnItemListener(this);
        rvMenu.setOnItemClickListener(new RecyclerViewTV.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerViewTV parent, View itemView, int position) {
                switch (position) {
                    //课程锻炼
                    case 0:
                        SPDataApp.setLastPageBeforeCrash(MainActivity.this, SPEnums.PAGE_SPORT_PE);
//                        startActivity(LessonMultiActivity.class);
                        startActivity(PeActivity.class);
                        break;
                    //WIFI 设置
                    case 1:
                        NetworkU.openSetting(MainActivity.this);
                        break;
                    //HUB 组设置
                    case 2:
                        startActivity(SettingHubGroupActivity.class);
                        break;
                    //帮助
                    case 3:
                        startActivity(HelpActivity.class);
                        break;
                }
            }
        });
    }

    @Override
    public void onItemPreSelected(RecyclerViewTV parent, View itemView, int position) {
        mRecyclerViewBridge.setUnFocusView(oldView);
    }

    @Override
    public void onItemSelected(RecyclerViewTV parent, View itemView, int position) {
        mRecyclerViewBridge.setFocusView(itemView, 1.05f);
        oldView = itemView;
    }

    @Override
    public void onReviseFocusFollow(RecyclerViewTV parent, View itemView, int position) {
        mRecyclerViewBridge.setFocusView(itemView, 1.05f);
        oldView = itemView;
    }
}
