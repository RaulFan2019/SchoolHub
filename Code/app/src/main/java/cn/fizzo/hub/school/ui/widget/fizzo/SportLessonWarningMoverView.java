package cn.fizzo.hub.school.ui.widget.fizzo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

import cn.fizzo.hub.school.R;
import cn.fizzo.hub.school.entity.model.MintTimeFormatter;
import cn.fizzo.hub.school.entity.net.GetSportLessonWarningListRE;
import cn.fizzo.hub.school.ui.widget.common.CircularImage;
import cn.fizzo.hub.school.ui.widget.common.NumTextView;
import cn.fizzo.hub.school.utils.DeviceU;
import cn.fizzo.hub.school.utils.ImageU;

/**
 * Created by FW on 2018/3/19.
 */

public class SportLessonWarningMoverView extends LinearLayout{


    CircularImage ivAvatar;
    TextView tvNo;
    TextView tvName;
    NumTextView tvHr;
    LineChart chartHr;

    public SportLessonWarningMoverView(Context context) {
        super(context);
    }

    public SportLessonWarningMoverView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_sport_lesson_warning_mover, this, true);

        ivAvatar = (CircularImage) findViewById(R.id.iv_avatar);
        tvNo = (TextView) findViewById(R.id.tv_no);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvHr = (NumTextView) findViewById(R.id.tv_curr_hr);
        chartHr = (LineChart) findViewById(R.id.chart_hr);

        initChart();
    }

    public void updateViewByData(final Context context,final GetSportLessonWarningListRE.AlertMoversBean moversBean){

        ImageU.loadStoreImage(moversBean.avatar,ivAvatar);
        if (moversBean.studentnumber < 10){
            tvNo.setText("0" + moversBean.studentnumber+"");
        }else {
            tvNo.setText(moversBean.studentnumber+"");
        }

        tvName.setText(moversBean.nickname);
        tvHr.setText(moversBean.cur_bpm + "");

        setHrChartData(context,moversBean);
    }

    /**
     * 初始化图形
     */
    private void initChart(){
        float mChartMarginTop = DeviceU.dpToPixel(20);
        float mChartMarginBottom = DeviceU.dpToPixel(45);
        float mChartMarginLeft = DeviceU.dpToPixel(45);
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
        xAxis.setTextSize(DeviceU.dpToPixel(20));
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setValueFormatter(new MintTimeFormatter());
        xAxis.setLabelCount(5);
        xAxis.setYOffset(10);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis leftAxis = chartHr.getAxisLeft();
        leftAxis.setTextSize(DeviceU.dpToPixel(20));
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setAxisMaximum(220f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setXOffset(10);
        leftAxis.setLabelCount(6);
        leftAxis.setDrawGridLines(false);
        leftAxis.setGranularityEnabled(false);
        leftAxis.setDrawZeroLine(true);

        YAxis rightAxis = chartHr.getAxisRight();
        rightAxis.setEnabled(false);

        chartHr.getAxisRight().setDrawZeroLine(false);
        chartHr.getAxisRight().setDrawGridLines(false);
        chartHr.getAxisRight().setDrawAxisLine(false);
    }


    /**
     * 设置数据
     */
    private void setHrChartData(final Context context,final GetSportLessonWarningListRE.AlertMoversBean moversBean) {
        ArrayList<Entry> hrValues = new ArrayList<Entry>();
        ArrayList<Entry> stepValues = new ArrayList<Entry>();

        XAxis xAxis = chartHr.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMaximum((float) (moversBean.elasped));

        int interval = moversBean.bpms.size() / 300 + 1;
        int index = 0;
        if (moversBean.bpms != null && moversBean.bpms.size() > 0) {
            for (GetSportLessonWarningListRE.AlertMoversBean.BpmsBean hr : moversBean.bpms) {
                if (index % interval == 0) {
                    hrValues.add(new Entry(hr.timeoffset, hr.bpm));
                }
                index++;
            }
        }

        LineDataSet hrSet = new LineDataSet(hrValues, "DataSet hr");
        hrSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        hrSet.setColor(Color.parseColor("#FF4612"));
        hrSet.setLineWidth(1.5f);
        hrSet.setDrawCircles(false);
        hrSet.setDrawValues(false);
        hrSet.setHighLightColor(Color.TRANSPARENT);
        hrSet.setDrawCircleHole(false);
        hrSet.setDrawFilled(true);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable hrDrawable = ContextCompat.getDrawable(context, R.drawable.chart_fade_accent);
            hrSet.setFillDrawable(hrDrawable);
        } else {
            hrSet.setFillColor(Color.parseColor("#FF4612"));
        }

        //危险心率线
        LimitLine llWarning = new LimitLine(moversBean.alert_hr, "");
        llWarning.setLineWidth(1f);
        llWarning.enableDashedLine(10f, 3f, 0f);
        llWarning.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        llWarning.setTextSize(10f);

        YAxis leftAxis = chartHr.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(llWarning);

        // create a data object with the datasets
//        LineData data = new LineData(hrSet, stepSet);
        LineData data = new LineData(hrSet);

        if (chartHr.getData() != null
                && chartHr.getData().getDataSetCount() > 0) {
            chartHr.setData(data);
            chartHr.notifyDataSetChanged();
            chartHr.invalidate();
        } else {
            chartHr.setData(data);
//            chartHr.getData().setHighlightEnabled(true);

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
