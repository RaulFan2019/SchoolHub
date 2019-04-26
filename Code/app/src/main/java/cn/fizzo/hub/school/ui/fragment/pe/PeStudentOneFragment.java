package cn.fizzo.hub.school.ui.fragment.pe;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.ContextCompat;
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
import cn.fizzo.hub.school.utils.DeviceU;
import cn.fizzo.hub.school.utils.ImageU;
import cn.fizzo.hub.school.utils.LogU;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/21 17:19
 */
public class PeStudentOneFragment extends BasePeFragment {

    private static final String TAG = "PeStudentOneFragment";

    private static final int MSG_GET_INFO = 0x01;
    private static final int MSG_UPDATE_VIEWS = 0x02;

    private static final int INTERVAL_GET_INFO = 5 * 1000;

    @BindView(R.id.tv_rest_bpm)
    TextView tvRestBpm;
    @BindView(R.id.iv_avatar)
    CircularImage ivAvatar;
    @BindView(R.id.tv_mover_no)
    NormalTextView tvMoverNo;
    @BindView(R.id.tv_mover_name)
    NormalTextView tvMoverName;
    @BindView(R.id.tv_bmi)
    TextView tvBmi;
    @BindView(R.id.tv_curr_hr)
    NumTextView tvCurrHr;
    @BindView(R.id.tv_bpm_max)
    TextView tvBpmMax;
    @BindView(R.id.tv_bpm_avg)
    TextView tvBpmAvg;
    @BindView(R.id.tv_density)
    NumTextView tvDensity;
    @BindView(R.id.tv_power)
    NumTextView tvPower;
    @BindView(R.id.chart_hr)
    LineChart chartHr;
    @BindView(R.id.ll_alert_info)
    LinearLayout llAlertInfo;
    @BindView(R.id.ll_alert)
    LinearLayout llAlert;

    private int mPeId;
    private GetPeStatusRE.LessonsBean.StudentsBean mStudent;
    private GetPeInfoRE mPeData;
    private int mOffset = 0;
    private int mPlannedDuration;

    private List<GetPeInfoRE.BpmsBean> listBpms = new ArrayList<>();
    private List<GetPeInfoRE.ExercisesBean> listExercises = new ArrayList<>();//填充的数据

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pe_student_one;
    }

    @Override
    protected void myHandleMsg(Message msg) {
        switch (msg.what) {
            case MSG_GET_INFO:
                postGetStudentData();
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
        mStudent = (GetPeStatusRE.LessonsBean.StudentsBean) bundle.getSerializable("student");
        mPlannedDuration = bundle.getInt("plannedDuration");

        tvBmi.setText("BMI：" + mStudent.bmi);
        tvBpmMax.setText("最高心率：" + mStudent.bpm_alert);
        tvRestBpm.setText("静息心率：" + mStudent.bpm_rest);
        tvMoverName.setText(mStudent.nickname);
        ImageU.loadUserImage(mStudent.avatar, ivAvatar);

        initHrChart();

    }

    @Override
    protected void causeGC() {
        listBpms.clear();
        listExercises.clear();
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
    private void postGetStudentData() {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                String url = SPDataApp.getServiceIp(getActivity()) + UrlConfig.URL_GET_PE_DATA;
                RequestParams params = RequestParamsBuilder.buildGetPeStudentData(getActivity(), url, mPeId
                        , mStudent.number, mOffset);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
                        LogU.v(TAG, "onSuccess:" + result.result);
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                            mPeData = JSON.parseObject(result.result, GetPeInfoRE.class);
                            listBpms.addAll(mPeData.selected_student.bpms);
                            listExercises.clear();
                            if (listExercises != null){
                                listExercises.addAll(mPeData.selected_student.exercises);
                            }
                            mOffset = mPeData.selected_student.offset_to++;
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
        xAxis.setLabelCount(mPlannedDuration / 60 / 5);
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
     * 更新页面
     */
    private void updateViews() {
        tvBpmMax.setText("最高心率：" + mPeData.selected_student.bpm_max);
        tvBpmAvg.setText("平均心率：" + mPeData.selected_student.bpm_avg);
        tvPower.setText(mPeData.selected_student.resthr_intensity + "");
        tvCurrHr.setText(mPeData.selected_student.bpm + "");
        if (mPeData.selected_student.density < 10) {
            tvDensity.setText(mPeData.selected_student.density + "");
        } else {
            tvDensity.setText((int) mPeData.selected_student.density + "");
        }
        setChartData();
    }

    /**
     * 设置数据
     */
    private void setChartData() {
        ArrayList<Entry> hrValues = new ArrayList<Entry>();

        if (listBpms != null && listBpms.size() > 0) {
            int index = 0;
            int interval = listBpms.size() / 300 + 1;
            for (GetPeInfoRE.BpmsBean hr : listBpms) {
                if (index % interval == 0) {
                    hrValues.add(new Entry(hr.offset, hr.bpm - 60));
                }
                index++;
            }
        }

        LineDataSet hrSet = new LineDataSet(hrValues, "DataSet hr");
        hrSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        hrSet.setColor(Color.parseColor("#FF4612"));
        hrSet.setLineWidth(4f);
        hrSet.setDrawCircles(false);
        hrSet.setDrawValues(false);
        hrSet.setHighLightColor(Color.TRANSPARENT);
        hrSet.setDrawCircleHole(false);
        hrSet.setDrawFilled(false);

        LineData data = new LineData(hrSet);

        //绘制填充区
        int indexStart = 0;
        for (GetPeInfoRE.ExercisesBean exercisesBean : listExercises) {
            ArrayList<Entry> Values = new ArrayList<Entry>();

            //寻找每段填充数据
            for (; indexStart < hrValues.size(); indexStart++) {
                if (hrValues.get(indexStart).getX() > exercisesBean.offset_from) {
                    Values.add(new Entry(hrValues.get(indexStart).getX(), hrValues.get(indexStart).getY()));
                }
                if (hrValues.get(indexStart).getX() > exercisesBean.offset_to) {
                    break;
                }
            }
//            LogU.v(TAG, "indexStart:" + indexStart + ",Values.size:" + Values.size());
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
                Drawable hrDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.chart_fade_accent);
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
