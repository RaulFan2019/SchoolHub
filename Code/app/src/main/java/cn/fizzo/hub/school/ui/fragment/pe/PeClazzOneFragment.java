package cn.fizzo.hub.school.ui.fragment.pe;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.config.UrlConfig;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.entity.model.HrFormatter;
import cn.fizzo.hub.school.entity.model.MintTimeFormatter;
import cn.fizzo.hub.school.entity.net.BaseRE;
import cn.fizzo.hub.school.entity.net.GetPeInfoRE;
import cn.fizzo.hub.school.entity.net.GetPeStatusRE;
import cn.fizzo.hub.school.network.BaseResponseParser;
import cn.fizzo.hub.school.network.RequestParamsBuilder;
import cn.fizzo.hub.school.ui.widget.common.CircularImage;
import cn.fizzo.hub.school.ui.widget.common.NormalTextView;
import cn.fizzo.hub.school.ui.widget.common.NumTextView;
import cn.fizzo.hub.school.ui.widget.fizzo.PeAlertView;
import cn.fizzo.hub.school.utils.DeviceU;
import cn.fizzo.hub.school.utils.ImageU;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/21 16:51
 */
public class PeClazzOneFragment extends BasePeFragment {


    private static final String TAG = "PeClazzOneFragment";

    private static final int MSG_GET_INFO = 0x01;
    private static final int MSG_UPDATE_VIEWS = 0x02;

    private static final int INTERVAL_GET_INFO = 5 * 1000;


    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_avatar_1)
    CircularImage ivAvatar1;
    @BindView(R.id.tv_mover_no_1)
    NormalTextView tvMoverNo1;
    @BindView(R.id.tv_mover_name_1)
    NormalTextView tvMoverName1;
    @BindView(R.id.tv_curr_bpm_1)
    NumTextView tvCurrBpm1;
    @BindView(R.id.tv_rest_bpm_1)
    TextView tvRestBpm1;
    @BindView(R.id.tv_power_1)
    NumTextView tvPower1;

    @BindView(R.id.tv_power_2)
    NumTextView tvPower2;
    @BindView(R.id.iv_avatar_2)
    CircularImage ivAvatar2;
    @BindView(R.id.tv_mover_no_2)
    NormalTextView tvMoverNo2;
    @BindView(R.id.tv_mover_name_2)
    NormalTextView tvMoverName2;
    @BindView(R.id.tv_curr_bpm_2)
    NumTextView tvCurrBpm2;
    @BindView(R.id.tv_rest_bpm_2)
    TextView tvRestBpm2;

    @BindView(R.id.tv_density)
    NumTextView tvDensity;

    @BindView(R.id.chart_hr)
    LineChart chartHr;

    @BindView(R.id.ll_alert_info)
    LinearLayout llAlertInfo;
    @BindView(R.id.ll_alert)
    LinearLayout llAlert;

    /* local data */
    private int mPeId;
    private String mClazzName;
    private int mPlannedDuration;
    List<GetPeStatusRE.LessonsBean.StudentsBean> listStudents;

    private GetPeStatusRE.LessonsBean.StudentsBean mDelegate1;
    private GetPeStatusRE.LessonsBean.StudentsBean mDelegate2;

    private int mOffset = 0;
    private GetPeInfoRE mPeData;

    private List<GetPeInfoRE.BpmsBean> listBpms1 = new ArrayList<>();
    private List<GetPeInfoRE.BpmsBean> listBpms2 = new ArrayList<>();

    private List<GetPeInfoRE.ExercisesBean> listExercises1 = new ArrayList<>();
    private List<GetPeInfoRE.ExercisesBean> listExercises2 = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pe_clazz_one;
    }

    @Override
    protected void myHandleMsg(Message msg) {
        switch (msg.what) {
            case MSG_GET_INFO:
                postGetClazzData();
                mHandler.sendEmptyMessageDelayed(MSG_GET_INFO, INTERVAL_GET_INFO);
                break;
            case MSG_UPDATE_VIEWS:
                updateViews();
                break;
        }
    }

    @Override
    protected void initParams() {
        Bundle bundle = getArguments();
        mPeId = bundle.getInt("lessonId");
        mClazzName = bundle.getString("clazzName");
        mPlannedDuration = bundle.getInt("plannedDuration");
        String[] delegatesNumber = bundle.getString("delegatesNumber").split(",");
        listStudents = (List<GetPeStatusRE.LessonsBean.StudentsBean>) bundle.getSerializable("students");

        mDelegate1 = listStudents.get(Integer.parseInt(delegatesNumber[0]) - 1);
        mDelegate2 = listStudents.get(Integer.parseInt(delegatesNumber[1]) - 1);


        tvTitle.setText(mClazzName);
        tvMoverName1.setText(mDelegate1.nickname);
        tvRestBpm1.setText("静息心率：" + mDelegate1.bpm_rest);
        if (mDelegate1.number < 10) {
            tvMoverNo1.setText("0" + mDelegate1.number);
        } else {
            tvMoverNo1.setText(mDelegate1.number + "");
        }
        ImageU.loadUserImage(mDelegate1.avatar, ivAvatar1);

        tvMoverName2.setText(mDelegate2.nickname);
        if (mDelegate2.number < 10) {
            tvMoverNo2.setText("0" + mDelegate2.number);
        } else {
            tvMoverNo2.setText(mDelegate2.number + "");
        }
        tvRestBpm2.setText("静息心率：" + mDelegate2.bpm_rest);
        ImageU.loadUserImage(mDelegate2.avatar, ivAvatar2);

        initHrChart();
    }

    @Override
    protected void causeGC() {
        if (chartHr != null) {
            chartHr.clear();
        }
        listBpms1.clear();
        listBpms2.clear();
        listExercises1.clear();
        listExercises2.clear();
        listStudents.clear();
    }

    @Override
    protected void onVisible() {
        mHandler.sendEmptyMessage(MSG_GET_INFO);
    }

    @Override
    protected void onInVisible() {

    }

    /**
     * 获取班级数据
     */
    private void postGetClazzData() {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                String url = SPDataApp.getServiceIp(getActivity()) + UrlConfig.URL_GET_PE_DATA;
                RequestParams params = RequestParamsBuilder.buildGetPeDelegatesData(getActivity(), url, mPeId, mOffset);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
//                        LogU.v(TAG, "onSuccess:" + result.result);
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                            mPeData = JSON.parseObject(result.result, GetPeInfoRE.class);
                            listBpms1.addAll(mPeData.delegates_data.delegates.get(0).bpms);
                            listExercises1.clear();
                            listExercises1.addAll(mPeData.delegates_data.delegates.get(0).exercises);
                            listBpms2.addAll(mPeData.delegates_data.delegates.get(1).bpms);
                            listExercises2.clear();
                            listExercises2.addAll(mPeData.delegates_data.delegates.get(1).exercises);


                            mOffset = mPeData.delegates_data.offset_to++;
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
     * 更新页面
     */
    private void updateViews() {
        tvDensity.setText(mPeData.lesson_basic_data.density + "");
        //第一个代表
        tvCurrBpm1.setText(mPeData.delegates_data.delegates.get(0).bpm + "");
        tvPower1.setText(mPeData.delegates_data.delegates.get(0).resthr_intensity + "");
        //第二个代表
        tvCurrBpm2.setText(mPeData.delegates_data.delegates.get(1).bpm + "");
        tvPower2.setText(mPeData.delegates_data.delegates.get(1).resthr_intensity + "");
        //是否有报警
        if (mPeData.alert_students.equals("")) {
            llAlertInfo.setVisibility(View.INVISIBLE);
        } else {
            llAlertInfo.setVisibility(View.VISIBLE);
            llAlertInfo.removeAllViews();
            String[] alerts = mPeData.alert_students.split(",");
            for (int i = 0; i < alerts.length && i < 8; i++) {
                PeAlertView alertView = new PeAlertView(getActivity());
                alertView.updateViewByData(listStudents.get(Integer.parseInt(alerts[i]) - 1));
                llAlertInfo.addView(alertView);
            }
        }
        setChartData();
    }

    /**
     * 初始化chart
     */
    private void initHrChart() {
        float mChartMarginTop = DeviceU.dpToPixel(30);
        float mChartMarginBottom = DeviceU.dpToPixel(50);
        float mChartMarginLeft = DeviceU.dpToPixel(140);
        float mChartMarginRight = DeviceU.dpToPixel(130);

        chartHr.getLegend().setEnabled(false);//设置图例是否显示
        // no description text
        chartHr.getDescription().setEnabled(false);
        // enable touch gestures
        chartHr.setTouchEnabled(true);

        // enable scaling and dragging
        chartHr.setDragEnabled(true);
        chartHr.setScaleEnabled(false);
        chartHr.setDrawGridBackground(false);
        chartHr.setHighlightPerDragEnabled(true);

        // set an alternative background color
        chartHr.setBackgroundColor(Color.TRANSPARENT);
        chartHr.setViewPortOffsets(mChartMarginLeft, mChartMarginTop, mChartMarginRight, mChartMarginBottom);

        XAxis xAxis = chartHr.getXAxis();
        xAxis.setTextSize(DeviceU.dpToPixel(20));
        xAxis.setTextColor(getActivity().getResources().getColor(R.color.chart_ruler));
        xAxis.setAxisMaximum(mPlannedDuration);
        xAxis.setAxisMinimum(0);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new MintTimeFormatter());
        xAxis.setLabelCount(mPlannedDuration/60/5);
        xAxis.setYOffset(10);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = chartHr.getAxisLeft();
        leftAxis.setTextSize(DeviceU.dpToPixel(20));
        leftAxis.setTextColor(getActivity().getResources().getColor(R.color.chart_ruler));
        leftAxis.setValueFormatter(new HrFormatter());
        leftAxis.setAxisMaximum(160f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridDashedLine(new DashPathEffect(new float[]{4.0f, 3.0f}, 0));
        leftAxis.setGridColor(Color.parseColor("#9A9A9A"));
        leftAxis.setXOffset(26);
        leftAxis.setLabelCount(5);
        leftAxis.setGranularityEnabled(false);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisLineWidth(2.6f);
        leftAxis.setZeroLineColor(Color.parseColor("#9A9A9A"));
        leftAxis.setZeroLineWidth(2.6f);

        LimitLine alertLl = new LimitLine(140f, "200");
        alertLl.setLineWidth(2.6f);
        alertLl.enableDashedLine(10f, 10f, 0f);
        alertLl.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        alertLl.setTextSize(20f);
        alertLl.setLineColor(Color.parseColor("#FF4612"));
        alertLl.setTextColor(Color.parseColor("#FF4612"));
        leftAxis.addLimitLine(alertLl);

        YAxis rightAxis = chartHr.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setDrawZeroLine(true);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawAxisLine(false);
    }

    /**
     * 设置数据
     */
    private void setChartData() {
        ArrayList<Entry> boyValues = new ArrayList<Entry>();
        ArrayList<Entry> girlValues = new ArrayList<Entry>();
        //男孩数据
        int interval;
        int index = 0;
        if (listBpms1 != null && listBpms1.size() > 0) {
            interval = listBpms1.size() / 300 + 1;
            for (GetPeInfoRE.BpmsBean hr : listBpms1) {
                if (index % interval == 0) {
                    boyValues.add(new Entry(hr.offset, hr.bpm - 60));
                }
                index++;
            }
        }

        LineDataSet boyHrSet = new LineDataSet(boyValues, "boy hr");
        boyHrSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        boyHrSet.setColor(Color.parseColor("#1296ff"));
        boyHrSet.setLineWidth(4f);
        boyHrSet.setDrawCircles(false);
        boyHrSet.setDrawValues(false);
        boyHrSet.setHighLightColor(Color.TRANSPARENT);
        boyHrSet.setDrawCircleHole(false);
        boyHrSet.setDrawFilled(false);


        //女孩数据
        index = 0;
        if (listBpms2 != null && listBpms2.size() > 0) {
            for (GetPeInfoRE.BpmsBean hr : listBpms2) {
                interval = listBpms2.size() / 300 + 1;
                if (index % interval == 0) {
                    girlValues.add(new Entry(hr.offset, hr.bpm - 60));
                }
                index++;
            }
        }

        LineDataSet girlHrSet = new LineDataSet(girlValues, "girl hr");
        girlHrSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        girlHrSet.setColor(Color.parseColor("#ff44D8"));
        girlHrSet.setLineWidth(4f);
        girlHrSet.setDrawCircles(false);
        girlHrSet.setDrawValues(false);
        girlHrSet.setHighLightColor(Color.TRANSPARENT);
        girlHrSet.setDrawCircleHole(false);
        girlHrSet.setDrawFilled(false);

        LineData data = new LineData(boyHrSet);
        data.addDataSet(girlHrSet);

        //绘制男孩填充区
        int indexStart = 0;
        for (GetPeInfoRE.ExercisesBean exercisesBean : listExercises2) {
            ArrayList<Entry> Values = new ArrayList<Entry>();

            //寻找每段填充数据
            for (; indexStart < boyValues.size(); indexStart++) {
                if (boyValues.get(indexStart).getX() > exercisesBean.offset_from) {
                    Values.add(new Entry(boyValues.get(indexStart).getX(), boyValues.get(indexStart).getY()));
                }
                if (boyValues.get(indexStart).getX() > exercisesBean.offset_to) {
                    break;
                }
            }
            LineDataSet set = new LineDataSet(Values, "sport hr");
            set.setAxisDependency(YAxis.AxisDependency.LEFT);
            set.setColor(Color.parseColor("#FF4612"));
            set.setLineWidth(0f);
            set.setDrawCircles(false);
            set.setDrawValues(false);
            set.setHighLightColor(Color.TRANSPARENT);
            set.setDrawCircleHole(false);
            set.setDrawFilled(true);
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable hrDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.chart_fade_boy);
                set.setFillDrawable(hrDrawable);
            } else {
                set.setFillColor(Color.parseColor("#FF4612"));
            }
            data.addDataSet(set);
        }

        //绘制女孩填充区
        indexStart = 0;
//        LogU.v(TAG,"listExercises size:" + listExercises.size());
        for (GetPeInfoRE.ExercisesBean exercisesBean : listExercises2) {
//            LogU.v(TAG, "exercisesBean.startoffset:" + exercisesBean.startoffset);
            ArrayList<Entry> Values = new ArrayList<Entry>();

            //寻找每段填充数据
            for (; indexStart < girlValues.size(); indexStart++) {
                if (girlValues.get(indexStart).getX() > exercisesBean.offset_from) {
                    Values.add(new Entry(girlValues.get(indexStart).getX(), girlValues.get(indexStart).getY()));
                }
                if (girlValues.get(indexStart).getX() > exercisesBean.offset_to) {
                    break;
                }
            }
            LineDataSet set = new LineDataSet(Values, "sport hr");
            set.setAxisDependency(YAxis.AxisDependency.LEFT);
            set.setColor(Color.parseColor("#FF4612"));
            set.setLineWidth(0f);
            set.setDrawCircles(false);
            set.setDrawValues(false);
            set.setHighLightColor(Color.TRANSPARENT);
            set.setDrawCircleHole(false);
            set.setDrawFilled(true);
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable hrDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.chart_fade_girl);
                set.setFillDrawable(hrDrawable);
            } else {
                set.setFillColor(Color.parseColor("#FF4612"));
            }
            data.addDataSet(set);
        }

        if (chartHr.getData() != null
                && chartHr.getData().getDataSetCount() > 0) {
            chartHr.setData(data);
            chartHr.notifyDataSetChanged();
            chartHr.invalidate();
        } else {
            chartHr.setData(data);
            // get the legend (only possible after setting data)
            Legend l = chartHr.getLegend();
            // modify the legend ...
            l.setForm(Legend.LegendForm.CIRCLE);
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);

            chartHr.invalidate();
            chartHr.animateX(1000);
        }
    }

}
