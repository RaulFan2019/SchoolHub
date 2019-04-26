package cn.fizzo.hub.school.network;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.fizzo.hub.school.LocalApp;
import cn.fizzo.hub.school.entity.model.CrashLog;

/**
 * Created by Raul.fan on 2018/1/1 0001.
 */

public class RequestParamsBuilder {


    /**
     * 上传崩溃信息
     * @param context
     * @param url
     * @param log
     * @param time
     * @return
     */
    public static RequestParams buildReportCrashRP(final Context context, final String url,
                                                   final CrashLog log, final String time) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
        String timeStr = "";
        try {
            timeStr = df.format(new Date(Long.parseLong(time)));
        } catch (NumberFormatException ex) {
            timeStr = time + "";
        }
        requestParams.addBodyParameter("serialno", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("debugtime", timeStr);
        requestParams.addBodyParameter("loglevel", log.logLevel + "");
        requestParams.addBodyParameter("logtitle", log.type);
        requestParams.addBodyParameter("debuginfo", log.content);
        requestParams.addBodyParameter("deviceos", log.os);
        requestParams.addBodyParameter("devicemodel", log.model);
        requestParams.addBodyParameter("appversion", log.app);
        return requestParams;
    }

    /**
     * 获取最新软件版本
     *
     * @param context
     * @param url
     * @return
     */
    public static RequestParams buildGetLastSoftVersionInfoRP(final Context context, final String url) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("serialno", LocalApp.getInstance().getCpuSerial());
        return requestParams;
    }


    /**
     * 获取HUB组列表
     *
     * @param context
     * @param url
     * @return
     */
    public static RequestParams buildSettingHubGroupGetListRP(final Context context, final String url) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("console_sn", LocalApp.getInstance().getCpuSerial());
        return requestParams;
    }

    /**
     * 创建HUB组请求
     *
     * @param context
     * @param url
     * @param groupName
     * @return
     */
    public static RequestParams buildSettingHubGroupCreateRP(final Context context, final String url,
                                                             final String groupName) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("console_sn", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("name", groupName + "");
        return requestParams;
    }


    /**
     * 加入HUB组
     *
     * @param context
     * @param url
     * @param groupId
     * @return
     */
    public static RequestParams buildSettingHubGroupJoinRP(final Context context, final String url,
                                                           final int groupId) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("console_sn", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("groupid", groupId + "");
        return requestParams;
    }


    /**
     * 修改HUB组名称
     *
     * @param context
     * @param url
     * @param groupId
     * @param name
     * @return
     */
    public static RequestParams buildSettingHubGroupEditNameRP(final Context context, final String url,
                                                               final int groupId, final String name) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("console_sn", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("groupid", groupId + "");
        requestParams.addBodyParameter("name", name + "");
        return requestParams;
    }


    /**
     * 删除HUB组
     *
     * @param context
     * @param url
     * @param groupId
     * @return
     */
    public static RequestParams buildSettingHubGroupDeleteRP(final Context context, final String url,
                                                             final int groupId) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("console_sn", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("groupid", groupId + "");
        return requestParams;
    }


    /**
     * 获取设备绑定信息
     *
     * @param context
     * @param url
     * @return
     */
    public static RequestParams buildGetConsoleRP(final Context context, final String url
            , final String content) throws PackageManager.NameNotFoundException {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("serialno", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("content", content);
        requestParams.addBodyParameter("versioncode", pi.versionCode + "");

        String hwVer = LocalApp.getInstance().getHwVer();
        if (!hwVer.equals("unKnow")) {
            requestParams.addBodyParameter("hrt_versionname", LocalApp.getInstance().getHwVer() + "");
        }
        return requestParams;
    }

    /**
     * 上传当时的ant数据
     *
     * @param context
     * @param url
     * @param ants
     * @param bpms
     * @param step
     * @return
     */
    public static RequestParams buildRealTimeUploadAntRP(final Context context, final String url,
                                                         final String ants, final String bpms,
                                                         final String step, final String stridefrequencies,
                                                         final String rssiString) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("console_sn", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("antsns", ants);
        requestParams.addBodyParameter("bpms", bpms);
        requestParams.addBodyParameter("stepcounts", step);
        requestParams.addBodyParameter("stridefrequencies", stridefrequencies);
        requestParams.addBodyParameter("rssis", rssiString);
        return requestParams;
    }


    /**
     * 上传当时的ant数据
     *
     * @param context
     * @param url
     * @param bpms
     * @param step
     * @return
     */
    public static RequestParams buildRealTimeUploadAntRPByMac(final Context context, final String url,
                                                              final int cassiaid,
                                                              final String macaddrs, final String bpms,
                                                              final String step, final String stridefrequencies,
                                                              final String rssiString) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("console_sn", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("cassiaid", cassiaid + "");
        requestParams.addBodyParameter("macaddrs", macaddrs);
        requestParams.addBodyParameter("bpms", bpms);
        requestParams.addBodyParameter("stepcounts", step);
        requestParams.addBodyParameter("stridefrequencies", stridefrequencies);
        requestParams.addBodyParameter("rssis", rssiString);
        return requestParams;
    }


    /**
     * 上传每分钟的数据
     *
     * @param context
     * @param url
     * @param data
     * @return
     */
    public static RequestParams buildUploadAntSplitInfoRP(final Context context, final String url,
                                                          final String data) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("data", data);
        return requestParams;
    }

    /**
     * 获取HUB组心率信息
     *
     * @param context
     * @param url
     * @return
     */
    public static RequestParams buildGetHubGroupHr(final Context context, final String url) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("console_sn", LocalApp.getInstance().getCpuSerial());
        return requestParams;
    }


    /**
     * 获取上课信息
     *
     * @param context
     * @param url
     * @param content
     * @return
     */
    public static RequestParams buildGetLessonInfoRP(final Context context, final String url,
                                                     final String content) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("console_sn", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("content", content);
        return requestParams;
    }


    /**
     * 发送桂花网的状态
     *
     * @param context
     * @param url
     * @param cassiaId
     * @param onLine
     * @return
     */
    public static RequestParams buildPostCassiaStateRP(final Context context, final String url,
                                                       final int cassiaId, final int onLine) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("console_sn", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("cassiaid", cassiaId + "");
        requestParams.addBodyParameter("online", onLine + "");
        return requestParams;
    }


    /**
     * 获取体育课上课状态信息
     *
     * @param context
     * @param url
     * @param infoType 1. update_time, 2. all
     * @return
     */
    public static RequestParams buildGetPeStatusRP(final Context context, final String url,
                                                   final int infoType) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("serial_no", LocalApp.getInstance().getCpuSerial());
        requestParams.addBodyParameter("info_type", infoType + "");
        return requestParams;
    }

    /**
     * 获取心率墙页面的数据
     *
     * @param context
     * @param url
     * @param peId
     * @return
     */
    public static RequestParams buildGetPeWallData(final Context context, final String url, final int peId) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("lesson_id", peId + "");
        requestParams.addBodyParameter("is_get_all_students", "1");
        requestParams.addBodyParameter("is_get_alert_students", "1");
        requestParams.addBodyParameter("is_get_basic_data", "1");
        return requestParams;
    }

    /**
     * 获取课程代表数据
     * @param context
     * @param url
     * @param peId
     * @param offsetFrom
     * @return
     */
    public static RequestParams buildGetPeDelegatesData(final Context context, final String url, final int peId
            , final int offsetFrom) {
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("lesson_id", peId + "");
        requestParams.addBodyParameter("is_get_basic_data", "1");
        requestParams.addBodyParameter("is_get_delegates_data", "1");
        requestParams.addBodyParameter("is_get_delegates_bpms", "1");
        requestParams.addBodyParameter("delegates_offset_from", offsetFrom + "");
        requestParams.addBodyParameter("is_get_alert_students", "1");
        return requestParams;
    }


    /**
     * 获取学生个人运动数据
     * @param context
     * @param url
     * @param peId
     * @param studentNumber
     * @param offsetFrom
     * @return
     */
    public static RequestParams buildGetPeStudentData(final Context context, final String url, final int peId,
                                                      final int studentNumber ,final int offsetFrom){

        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("lesson_id", peId + "");
        requestParams.addBodyParameter("is_get_selected_student_data", "1");
        requestParams.addBodyParameter("is_get_selected_student_bpms", "1");
        requestParams.addBodyParameter("selected_student_number", studentNumber + "");
        requestParams.addBodyParameter("selected_student_offset_from", offsetFrom + "");
        return requestParams;
    }

    /**
     * 获取体育课报告一屏数据
     * @param context
     * @param url
     * @param peId
     * @return
     */
    public static RequestParams buildGetPeReportOneData(final Context context, final String url, final int peId){
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("lesson_id", peId + "");
        requestParams.addBodyParameter("is_get_class_bpms", "1");
        requestParams.addBodyParameter("is_get_basic_data", "1");
        requestParams.addBodyParameter("class_bpms_offset_from","0");
        return requestParams;
    }


    /**
     * 获取4屏的班级数据
     * @param context
     * @param url
     * @param peId
     * @param offsetFrom
     * @return
     */
    public static RequestParams buildGetPeFourData(final Context context,final String url, final int peId,
                                                   final int offsetFrom){
        MyRequestParams requestParams = new MyRequestParams(context, url);
        requestParams.addBodyParameter("lesson_id", peId + "");
        requestParams.addBodyParameter("is_get_class_bpms", "1");
        requestParams.addBodyParameter("is_get_basic_data", "1");
        requestParams.addBodyParameter("class_bpms_offset_from",offsetFrom + "");
        requestParams.addBodyParameter("is_get_alert_students", "1");
        return requestParams;
    }
}
