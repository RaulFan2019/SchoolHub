package cn.fizzo.hub.school.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;

import cn.fizzo.hub.school.LocalApp;

/**
 * Created by Raul.fan on 2017/7/10 0010.
 */

public class DeviceU {


    /**
     * 获取屏幕高度
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getHeight();
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        return activity.getWindowManager().getDefaultDisplay().getWidth();
    }

    /**
     * 像素转化dp     *
     *
     * @param pixel
     * @return
     */
    public static float pixelToDp(float pixel) {
        final float scale = getDisplayMetrics().density;
        return (int) (pixel / scale + 0.5f);
    }


    /**
     * dp转化为像素
     *
     * @param dp
     * @return
     */
    public static float dpToPixel(float dp) {
        return dp * (getDisplayMetrics().densityDpi / 160F);
    }


    /**
     * 获取屏幕分辨率
     *
     * @return
     */
    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((WindowManager) LocalApp.getInstance().applicationContext.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay()
                .getMetrics(displaymetrics);
        return displaymetrics;
    }

    /**
     * 获取版本Code
     *
     * @param packageName
     * @return
     */
    public static int getVersionCode(String packageName) {
        int versionCode = 0;
        try {
            versionCode = LocalApp.applicationContext.getPackageManager().getPackageInfo(packageName,
                    0).versionCode;
        } catch (PackageManager.NameNotFoundException ex) {
            versionCode = 0;
        }
        return versionCode;
    }

    /**
     * 安装APK
     *
     * @param context
     * @param file
     */
    public static void installAPK(Context context, File file) {
//        Log.v(TAG,"file == null:" + (file == null));
        if (file == null || !file.exists()) {
            return;
        }
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 静默安装App
     * <p>非root需添加权限 {@code <uses-permission android:name="android.permission.INSTALL_PACKAGES" />}</p>
     *
     * @param filePath 文件路径
     * @return {@code true}: 安装成功<br>{@code false}: 安装失败
     */
    public static boolean installAppSilent(final String filePath) {
        File file = FileU.getFileByPath(filePath);
        if (!FileU.isFileExists(file)) return false;
        String command = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install " + filePath;
        ShellU.CommandResult commandResult = ShellU.execCmd(command, !isSystemApp(), true);
        return commandResult.successMsg != null && commandResult.successMsg.toLowerCase().contains("success");
    }

    /**
     * 判断App是否是系统应用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp() {
        return isSystemApp(LocalApp.getInstance().applicationContext.getPackageName());
    }

    /**
     * 判断App是否是系统应用
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp(final String packageName) {
        if (FileU.isSpace(packageName)) return false;
        try {
            PackageManager pm = LocalApp.getInstance().applicationContext.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(packageName, 0);
            return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }


}
