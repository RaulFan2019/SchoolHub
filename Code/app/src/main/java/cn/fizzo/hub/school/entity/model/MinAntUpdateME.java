package cn.fizzo.hub.school.entity.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raul.fan on 2018/1/10 0010.
 */

public class MinAntUpdateME {

    public String serialNo;
    public int startCount;
    public int endCount;
    public List<AntInfo> antInfoList;

    public static class AntInfo {
        public int offset;
        public int hr;
        public int cadence;

        public AntInfo() {

        }

        public AntInfo(int offset, int hr, int cadence) {
            this.offset = offset;
            this.hr = hr;
            this.cadence = cadence;
        }
    }

    public MinAntUpdateME() {

    }

    public MinAntUpdateME(String serialNo, int startCount, int endCount, int offset, int hr, int cadence) {
        this.serialNo = serialNo;
        this.startCount = startCount;
        this.endCount = endCount;
        antInfoList = new ArrayList<>();
        antInfoList.add(new AntInfo(offset, hr, cadence));
    }
}
