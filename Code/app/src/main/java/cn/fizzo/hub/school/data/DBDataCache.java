package cn.fizzo.hub.school.data;

import org.xutils.ex.DbException;

import java.util.List;

import cn.fizzo.hub.school.LocalApp;
import cn.fizzo.hub.school.entity.db.CacheDE;
import cn.fizzo.hub.school.entity.event.NewUploadDataEE;

/**
 * Created by Raul.fan on 2017/7/10 0010.
 */

public class DBDataCache {


    /**
     * 找到第一个要上传的数据
     * @return
     */
    public static CacheDE getFirst(){
        try {
            return LocalApp.getInstance().getDb().findFirst(CacheDE.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 保存
     *
     * @param cache
     */
    public static void save(CacheDE cache) {
        try {
            LocalApp.getInstance().getDb().save(cache);
            LocalApp.getInstance().getEventBus().post(new NewUploadDataEE());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存
     *
     * @param cacheList
     */
    public static void save(List<CacheDE> cacheList) {
        try {
            LocalApp.getInstance().getDb().save(cacheList);
            LocalApp.getInstance().getEventBus().post(new NewUploadDataEE());
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一条数据
     *
     * @param cache
     */
    public static void delete(CacheDE cache) {
        try {
            LocalApp.getInstance().getDb().delete(cache);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
