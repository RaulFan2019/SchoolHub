package cn.fizzo.hub.school.entity.model;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by Administrator on 2016/8/9.
 */
public class HrFormatter implements IAxisValueFormatter {

    public HrFormatter(){
        super();
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if (value < 0){
            return "";
        }else {
            return (int)(value + 60)+ "";
        }
    }
}
