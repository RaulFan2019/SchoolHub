package cn.fizzo.hub.school.data;

import org.xutils.ex.DbException;

import cn.fizzo.hub.school.LocalApp;
import cn.fizzo.hub.school.entity.db.ConsoleDE;
import cn.fizzo.hub.school.entity.net.GetConsoleInfoRE;

/**
 * Created by Raul.fan on 2018/1/3 0003.
 */

public class DBDataConsole {

    /**
     * 保存设备信息
     *
     * @param consoleBean
     */
    public static void saveStoreInfo(final GetConsoleInfoRE.ConsoleBean consoleBean) {
        ConsoleDE dbEntity = new ConsoleDE(LocalApp.getInstance().getCpuSerial(),
                consoleBean.id, consoleBean.registertime, consoleBean.name,
                consoleBean.consolegroup_id,consoleBean.slide_interval);
        try {
            LocalApp.getInstance().getDb().saveOrUpdate(dbEntity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取设备信息
     */
    public static ConsoleDE getConsoleInfo() {
        try {
            return LocalApp.getInstance().getDb()
                    .selector(ConsoleDE.class).where("SerialNo", "=", LocalApp.getInstance().getCpuSerial())
                    .findFirst();
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }
}
