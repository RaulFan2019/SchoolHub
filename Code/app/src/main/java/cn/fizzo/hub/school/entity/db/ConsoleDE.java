package cn.fizzo.hub.school.entity.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Raul.fan on 2018/1/3 0003.
 */
@Table(name = "console")
public class ConsoleDE {

    @Column(name = "SerialNo", isId = true, autoGen = false)
    public String SerialNo;//Serial No
    @Column(name = "consoleId")
    public int consoleId;//console id
    @Column(name = "registerTime")
    public String registerTime; // 门店注册时间
    @Column(name = "name")
    public String name;//Hub名称
    @Column(name = "groupId")
    public int groupId;//组ID
    @Column(name = "intervalSlice")
    public int intervalSlice;


    public ConsoleDE() {
    }

    public ConsoleDE(String SerialNo, int consoleId, String registerTime, String name, int groupId,int intervalSlice) {
        this.SerialNo = SerialNo;
        this.consoleId = consoleId;
        this.registerTime = registerTime;
        this.name = name;
        this.groupId = groupId;
        this.intervalSlice = intervalSlice;
    }
}