package cn.fizzo.hub.school.data;

import org.xutils.ex.DbException;

import cn.fizzo.hub.school.LocalApp;
import cn.fizzo.hub.school.entity.db.ConsoleDE;
import cn.fizzo.hub.school.entity.db.StoreDE;

/**
 * Created by Raul.fan on 2017/10/26 0026.
 */

public class DBData {


    /**
     * 清空数据库
     */
    public static void cleanAllDBData(){
        try {
            LocalApp.getInstance().getDb().delete(StoreDE.class);
            LocalApp.getInstance().getDb().delete(ConsoleDE.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
