package cn.fizzo.hub.school.entity.net;

import java.util.List;

/**
 * Created by Raul.fan on 2018/1/3 0003.
 */

public class GetConsoleInfoRE {


    /**
     * updatetime : 1970-01-01 08:00:00
     * store : {"id":0,"registertime":"1970-01-01 08:00:00","name":"","logo":""}
     * console : {"id":0,"registertime":"1970-01-01 08:00:00","serialno":"","name":"","consolegroup_id":0}
     */

    public String updatetime;
    public StoreBean store;
    public ConsoleBean console;
    public List<CassiaHubsBean> cassia_hubs;


    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public StoreBean getStore() {
        return store;
    }

    public void setStore(StoreBean store) {
        this.store = store;
    }

    public ConsoleBean getConsole() {
        return console;
    }

    public void setConsole(ConsoleBean console) {
        this.console = console;
    }

    public List<CassiaHubsBean> getCassia_hubs() {
        return cassia_hubs;
    }

    public void setCassia_hubs(List<CassiaHubsBean> cassia_hubs) {
        this.cassia_hubs = cassia_hubs;
    }

    public static class StoreBean {
        /**
         * id : 0
         * registertime : 1970-01-01 08:00:00
         * name :
         * logo :
         */

        public int id;//门店ID
        public String registertime;//注册时间
        public String name;//门店名称
        public String logo;//LOG图标

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRegistertime() {
            return registertime;
        }

        public void setRegistertime(String registertime) {
            this.registertime = registertime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }

    public static class ConsoleBean {
        /**
         * id : 0
         * registertime : 1970-01-01 08:00:00
         * serialno :
         * name :
         * consolegroup_id : 0
         */

        public int id;//Console id
        public String registertime;//注册时间
        public String serialno;//设备序列号
        public String name;//设备名称
        public int consolegroup_id;//HUB组ID
        public int slide_interval;//自动切换时间 单位：秒

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRegistertime() {
            return registertime;
        }

        public void setRegistertime(String registertime) {
            this.registertime = registertime;
        }

        public String getSerialno() {
            return serialno;
        }

        public void setSerialno(String serialno) {
            this.serialno = serialno;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getConsolegroup_id() {
            return consolegroup_id;
        }

        public void setConsolegroup_id(int consolegroup_id) {
            this.consolegroup_id = consolegroup_id;
        }

        public int getSlide_interval() {
            return slide_interval;
        }

        public void setSlide_interval(int slide_interval) {
            this.slide_interval = slide_interval;
        }
    }

    public static class CassiaHubsBean {
        /**
         * id : 1
         * serialno : C1P021538002539
         * mac_addr : cc:1b:e0:e0:27:a8
         * name : cassia-E027A8
         */

        public int id;
        public String serialno;
        public String mac_addr;
        public String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSerialno() {
            return serialno;
        }

        public void setSerialno(String serialno) {
            this.serialno = serialno;
        }

        public String getMac_addr() {
            return mac_addr;
        }

        public void setMac_addr(String mac_addr) {
            this.mac_addr = mac_addr;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
