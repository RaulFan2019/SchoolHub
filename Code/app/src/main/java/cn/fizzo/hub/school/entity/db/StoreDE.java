package cn.fizzo.hub.school.entity.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Raul.fan on 2018/1/3 0003.
 */
@Table(name = "store")
public class StoreDE {

    @Column(name = "storeId", isId = true, autoGen = false)
    public int storeId;//门店D
    @Column(name = "registerTime")
    public String registerTime; // 门店注册时间
    @Column(name = "name")
    public String name;//门店名称
    @Column(name = "logo")
    public String logo;//门店LOGO地址


    public StoreDE() {
    }

    public StoreDE(int storeId, String registerTime, String name, String logo) {
        this.storeId = storeId;
        this.registerTime = registerTime;
        this.name = name;
        this.logo = logo;
    }
}
