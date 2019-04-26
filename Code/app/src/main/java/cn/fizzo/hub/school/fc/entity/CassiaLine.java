package cn.fizzo.hub.school.fc.entity;

import java.util.List;

public class CassiaLine {


    /**
     * name : (unknown)
     * evt_type : 0
     * rssi : -83
     * adData : 02011A0AFF4C0010050B10F27112
     * bdaddrs : [{"bdaddr":"69:1C:9B:ED:AD:F6","bdaddrType":"random"}]
     */

    public String name;
    public int evt_type;
    public int rssi;
    public String adData;
    public List<BdaddrsBean> bdaddrs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEvt_type() {
        return evt_type;
    }

    public void setEvt_type(int evt_type) {
        this.evt_type = evt_type;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getAdData() {
        return adData;
    }

    public void setAdData(String adData) {
        this.adData = adData;
    }

    public List<BdaddrsBean> getBdaddrs() {
        return bdaddrs;
    }

    public void setBdaddrs(List<BdaddrsBean> bdaddrs) {
        this.bdaddrs = bdaddrs;
    }

    public static class BdaddrsBean {
        /**
         * bdaddr : 69:1C:9B:ED:AD:F6
         * bdaddrType : random
         */

        public String bdaddr;
        public String bdaddrType;

        public String getBdaddr() {
            return bdaddr;
        }

        public void setBdaddr(String bdaddr) {
            this.bdaddr = bdaddr;
        }

        public String getBdaddrType() {
            return bdaddrType;
        }

        public void setBdaddrType(String bdaddrType) {
            this.bdaddrType = bdaddrType;
        }
    }
}
