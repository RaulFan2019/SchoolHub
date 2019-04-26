package cn.fizzo.hub.school.ui.fragment.pe;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.open.androidtvwidget.leanback.recycle.GridLayoutManagerTV;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.config.UrlConfig;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.entity.adapter.PeWallStudentAE;
import cn.fizzo.hub.school.entity.net.BaseRE;
import cn.fizzo.hub.school.entity.net.GetPeInfoRE;
import cn.fizzo.hub.school.entity.net.GetPeStatusRE;
import cn.fizzo.hub.school.network.BaseResponseParser;
import cn.fizzo.hub.school.network.RequestParamsBuilder;
import cn.fizzo.hub.school.ui.adapter.PeWallTwoAdapter;
import cn.fizzo.hub.school.ui.widget.fizzo.PeAlertTwoView;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/21 16:51
 */
public class PeClazzTwoFragment extends BasePeFragment {

    private static final String TAG = "PeClazzTwoFragment";

    /* contains */
    private static final int FULL_SIZE = 30;

    private static final int MSG_GET_PE_DATA = 0x01;
    private static final int MSG_UPDATE_VIEWS = 0x02;
    private static final int MSG_UPDATE_PAGE = 0x03;

    private static final int INTERVAL_GET_LESSON_DATA = 2 * 1000;
    private static final int INTERVAL_UPDATE_PAGE = 10 * 1000;


    @BindView(R.id.tv_clazz_name)
    TextView tvClazzName;
    @BindView(R.id.tv_power)
    TextView tvPower;
    @BindView(R.id.tv_density)
    TextView tvDensity;
    @BindView(R.id.tv_bpm)
    TextView tvBpm;
    @BindView(R.id.tv_alert_times)
    TextView tvAlertTimes;
    @BindView(R.id.rv_mover)
    RecyclerView rvMover;
    @BindView(R.id.ll_alert_top)
    LinearLayout llAlertTop;
    @BindView(R.id.ll_alert_bottom)
    LinearLayout llAlertBottom;
    @BindView(R.id.ll_alert)
    LinearLayout llAlert;

    /* local data */
    private int mPeId;
    private String mClazzName;

    private int mPage = 0;
    private List<PeWallStudentAE> listStudents = new ArrayList<>();
    private GetPeInfoRE mPeData;

    private PeWallTwoAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pe_clazz_two;
    }

    @Override
    protected void myHandleMsg(Message msg) {
        switch (msg.what) {
            case MSG_GET_PE_DATA:
                getLessonInfo();
                mHandler.sendEmptyMessageDelayed(MSG_GET_PE_DATA, INTERVAL_GET_LESSON_DATA);
                break;
            case MSG_UPDATE_VIEWS:
                updateViews();
                break;
            case MSG_UPDATE_PAGE:
                if (listStudents.size() > FULL_SIZE) {
                    mPage++;
                    if (mPage > (listStudents.size() / FULL_SIZE)) {
                        mPage = 0;
                    }
                    //适配器
                    adapter = new PeWallTwoAdapter(getActivity(), listStudents, mPage);
                    rvMover.setAdapter(adapter);
                }
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_PAGE, INTERVAL_UPDATE_PAGE);
                break;
        }
    }

    @Override
    protected void initParams() {
        Bundle bundle = getArguments();
        mPeId = bundle.getInt("lessonId");
        mClazzName = bundle.getString("clazzName");
        List<GetPeStatusRE.LessonsBean.StudentsBean> students = (List<GetPeStatusRE.LessonsBean.StudentsBean>) bundle.getSerializable("students");

        for (int i = 0; i < students.size(); i++) {
            listStudents.add(new PeWallStudentAE(students.get(i)));
        }
        tvClazzName.setText(mClazzName);

        GridLayoutManagerTV gridlayoutManager = new GridLayoutManagerTV(getActivity(), 5);
        gridlayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        rvMover.setLayoutManager(gridlayoutManager);

        //适配器
        adapter = new PeWallTwoAdapter(getActivity(), listStudents, mPage);
        rvMover.setAdapter(adapter);


        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_PAGE, INTERVAL_UPDATE_PAGE);

    }

    @Override
    protected void causeGC() {
        listStudents.clear();
    }

    @Override
    protected void onVisible() {
        mHandler.sendEmptyMessage(MSG_GET_PE_DATA);
    }

    @Override
    protected void onInVisible() {

    }

    /**
     * 获取课程信息
     */
    private void getLessonInfo() {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                String url = SPDataApp.getServiceIp(getActivity()) + UrlConfig.URL_GET_PE_DATA;
                RequestParams params = RequestParamsBuilder.buildGetPeWallData(getActivity(), url, mPeId);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
//                        LogU.v(TAG, "onSuccess:" + result.result);
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                            mPeData = JSON.parseObject(result.result, GetPeInfoRE.class);
                            String[] bpms = mPeData.all_students.bpm.split(",");
                            String[] stepCount = mPeData.all_students.step_count.split(",");
                            for (int i = 0; i < listStudents.size() && i < bpms.length; i++) {
                                listStudents.get(i).updateSportData(Integer.parseInt(bpms[i]), stepCount[i]);
                            }
                            mHandler.sendEmptyMessage(MSG_UPDATE_VIEWS);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
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
     * 更新
     */
    private void updateViews() {
        tvBpm.setText(mPeData.lesson_basic_data.bpm_avg + "");
        tvAlertTimes.setText(mPeData.lesson_basic_data.alert_times + "");
        tvDensity.setText(mPeData.lesson_basic_data.density + "%");
        tvPower.setText(mPeData.lesson_basic_data.resthr_intensity + "");
        adapter.notifyDataSetChanged();

        //是否有报警
        if (mPeData.alert_students.equals("")) {
            llAlertTop.setVisibility(View.INVISIBLE);
            llAlertBottom.setVisibility(View.INVISIBLE);
        } else {
//            LogU.v(TAG,"mPeData.alert_students:" + mPeData.alert_students);
            String[] alerts = mPeData.alert_students.split(",");
            llAlertTop.setVisibility(View.VISIBLE);
            llAlertTop.removeAllViews();
            llAlertBottom.setVisibility(View.VISIBLE);
            llAlertBottom.removeAllViews();

            for (int i = 0; i < alerts.length && i < 4; i++) {
                PeAlertTwoView alertView = new PeAlertTwoView(getActivity());
                alertView.updateViewByData(listStudents.get(Integer.parseInt(alerts[i]) - 1).basicInfo);
                llAlertTop.addView(alertView);
            }
            if (alerts.length > 4) {
                for (int i = 4; i < alerts.length && i < 8; i++) {
                    PeAlertTwoView alertView = new PeAlertTwoView(getActivity());
                    alertView.updateViewByData(listStudents.get(Integer.parseInt(alerts[i]) - 1).basicInfo);
                    llAlertBottom.addView(alertView);
                }
            }
        }
    }

}
