package cn.fizzo.hub.school.ui.fragment.pe;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Message;
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

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import butterknife.BindView;
import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.config.UrlConfig;
import cn.fizzo.hub.school.data.SPDataApp;
import cn.fizzo.hub.school.entity.model.HrFormatter;
import cn.fizzo.hub.school.entity.model.MintTimeFormatter;
import cn.fizzo.hub.school.entity.net.BaseRE;
import cn.fizzo.hub.school.entity.net.GetPeInfoRE;
import cn.fizzo.hub.school.network.BaseResponseParser;
import cn.fizzo.hub.school.network.RequestParamsBuilder;
import cn.fizzo.hub.school.utils.DeviceU;
import cn.fizzo.hub.school.utils.LogU;

/**
 * @author Raul.Fan
 * @email 35686324@qq.com
 * @date 2019/3/21 17:14
 */
public class PeReportFourFragment extends BasePeFragment {


    private static final String TAG = "PeReportTwoFragment";

    private static final int MSG_UPDATE_VIEWS = 0x01;
    private static final int MSG_GET_REPORT_AGAIN = 0x02;


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
    @BindView(R.id.chart_hr)
    LineChart chartHr;


    /* local data */
    private int mPeId;
    private String mClazzName;
    private int mPlannedDuration;

    private GetPeInfoRE mPeData;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pe_report_four;
    }

    @Override
    protected void myHandleMsg(Message msg) {
        switch (msg.what) {
            case MSG_GET_REPORT_AGAIN:
                postGetReportData();
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

        tvClazzName.setText(mClazzName);

        initHrChart();
        postGetReportData();
    }

    @Override
    protected void causeGC() {

    }

    @Override
    protected void onVisible() {

    }

    @Override
    protected void onInVisible() {

    }

    /**
     * 获取班级数据
     */
    private void postGetReportData() {
        x.task().post(new Runnable() {
            @Override
            public void run() {
                String url = SPDataApp.getServiceIp(getActivity()) + UrlConfig.URL_GET_PE_DATA;
                RequestParams params = RequestParamsBuilder.buildGetPeFourData(getActivity(), url, mPeId, 0);
                mCancelable = x.http().post(params, new Callback.CommonCallback<BaseRE>() {
                    @Override
                    public void onSuccess(BaseRE result) {
                        LogU.v(TAG, "onSuccess:" + result.result);
                        if (result.errorcode == BaseResponseParser.ERROR_CODE_NONE) {
                            mPeData = JSON.parseObject(result.result, GetPeInfoRE.class);
                            mHandler.sendEmptyMessage(MSG_UPDATE_VIEWS);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        mHandler.sendEmptyMessageDelayed(MSG_GET_REPORT_AGAIN, 2000);
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
        float mChartMarginTop = DeviceU.dpToPixel(20);
        float mChartMarginBottom = DeviceU.dpToPixel(40);
        float mChartMarginLeft = DeviceU.dpToPixel(50);
        float mChartMarginRight = DeviceU.dpToPixel(30);

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
        xAxis.setTextSize(DeviceU.dpToPixel(11));
        xAxis.setTextColor(Color.parseColor("#88262528"));
        xAxis.setAxisMaximum(mPlannedDuration);
        xAxis.setAxisMinimum(0);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new MintTimeFormatter());
        xAxis.setLabelCount(mPlannedDuration / 60 / 10);
        xAxis.setYOffset(5);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = chartHr.getAxisLeft();
        leftAxis.setTextSize(DeviceU.dpToPixel(11));
        leftAxis.setTextColor(getActivity().getResources().getColor(R.color.chart_ruler));
        leftAxis.setValueFormatter(new HrFormatter());
        leftAxis.setAxisMaximum(160f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridDashedLine(new DashPathEffect(new float[]{4.0f, 3.0f}, 0));
        leftAxis.setGridColor(Color.parseColor("#88262528"));
        leftAxis.setXOffset(10);
        leftAxis.setLabelCount(5);
        leftAxis.setGranularityEnabled(false);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisLineWidth(2f);
        leftAxis.setZeroLineColor(Color.parseColor("#88262528"));
        leftAxis.setZeroLineWidth(2f);

        LimitLine alertLl = new LimitLine(140f, "");
        alertLl.setLineWidth(2f);
        alertLl.enableDashedLine(10f, 10f, 0f);
        alertLl.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        alertLl.setTextSize(DeviceU.dpToPixel(12));
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
        tvAlertTimes.setText(mPeData.lesson_basic_data.alert_times + "");
        tvBpm.setText(mPeData.lesson_basic_data.bpm_avg + "");
        tvDensity.setText(mPeData.lesson_basic_data.density + "%");
        tvPower.setText(mPeData.lesson_basic_data.resthr_intensity + "");

        setChartData();
    }

    /**
     * 设置数据
     */
    private void setChartData() {
        ArrayList<Entry> hrValues = new ArrayList<Entry>();

        if (mPeData.class_bpms.bpms != null && mPeData.class_bpms.bpms.size() > 0) {
            int index = 0;
            int interval = mPeData.class_bpms.bpms.size() / 300 + 1;
            for (GetPeInfoRE.BpmsBean hr : mPeData.class_bpms.bpms) {
                if (index % interval == 0) {
                    hrValues.add(new Entry(hr.offset, hr.bpm - 60));
                }
                index++;
            }
        }
        LineDataSet hrSet = new LineDataSet(hrValues, "DataSet hr");
        hrSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        hrSet.setColor(Color.parseColor("#FF4612"));
        hrSet.setLineWidth(2f);
        hrSet.setDrawCircles(false);
        hrSet.setDrawValues(false);
        hrSet.setHighLightColor(Color.TRANSPARENT);
        hrSet.setDrawCircleHole(false);
        hrSet.setDrawFilled(false);

        LineData data = new LineData(hrSet);

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