package cn.fizzo.hub.school.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import cn.fizzo.hub.school.config.SPEnums;

/**
 * Created by FW on 2018/3/21.
 */

public class SPDataApp {


    /**
     * 获取Preference设置
     */
    public static SharedPreferences getSharedPreferences(final Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }


    /**
     * 设置崩溃前的页面
     * @param context
     * @param page
     */
    public static void setLastPageBeforeCrash(final Context context, final int page){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(SPEnums.SP_LAST_PAGE_BEFORE_CRASH, page);
        editor.commit();
    }

    /**
     * 获取崩溃前的页面
     * @param context
     * @return
     */
    public static int getLastPageBeforeCrash(final Context context){
        return getSharedPreferences(context).getInt(SPEnums.SP_LAST_PAGE_BEFORE_CRASH, SPEnums.PAGE_MAIN);
    }

    /**
     * 设置崩溃前的页面
     * @param context
     * @param serviceIp
     */
    public static void setServiceIp(final Context context, final String serviceIp){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(SPEnums.SP_SERVICE_IP, serviceIp);
        editor.commit();
    }

    /**
     * 获取崩溃前的页面
     * @param context
     * @return
     */
    public static String getServiceIp(final Context context){
        return getSharedPreferences(context).getString(SPEnums.SP_SERVICE_IP, SPEnums.DEFAULT_SERVICE_IP);
    }

}
