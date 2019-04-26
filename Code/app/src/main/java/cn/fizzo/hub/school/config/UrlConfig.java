package cn.fizzo.hub.school.config;

/**
 * Created by Raul.fan on 2018/1/1 0001.
 */

public class UrlConfig {

    /**
     * 根据编译版本获取Host Ip 地址
     *
     * @return
     */
//    public static String getHostIp() {
//        return "http://www.123yd.cn/";
//    }

    //获取设备信息
    public static final String URL_GET_CONSOLE_INFO = "/fitness/V2/School/getConsoleInfo";

    //上传日志信息
    public static final String URL_SYSTEM_SAVE_DEBUG = "/fitness/V3/Text/saveConsoleDebugInfo";

    //获取最新的硬件版本
    public static final String URL_CHECK_HW_VERSION = "/fitness/V2/Androidrelease/getHrtLatestFirmware";

    //获取最新的版本
    public static final String URL_CHECK_VISION = "/fitness/V2/Androidrelease/getHubLatestRelease";

    //上传当前心率相关信息
    public static final String URL_UPLOAD_RECENT_HR = "/fitness/V2/Heartrate/uploadRecentHeartrates";
    //上传每分钟数据
    public static final String URL_UPLOAD_AMT_SPLIT = "/fitness/V2/School/uploadMinuteHeartrates";


    //获取HUB组信息
    public static final String URL_GET_HUB_GROUP_LIST = "/fitness/V2/Console/getConsoleGroupList";
    //创建HUB组
    public static final String URL_CREATE_HUB_GROUP = "/fitness/V2/Console/createConsoleGroup";
    //加入HUB组
    public static final String URL_JOIN_HUB_GROUP = "/fitness/V2/Console/joinConsoleGroup";
    //删除HUB组
    public static final String URL_DELETE_HUB_GROUP = "/fitness/V2/Console/deleteConsoleGroup";
    //修改HUB组名称
    public static final String URL_EDIT_HUB_GROUP_NAME = "/fitness/V2/Console/updateConsoleGroupName";
    //获取HUB组的心率信息
    public static final String URL_GET_GROUP_HR = "/fitness/V2/Heartrate/getRecentHeartratesByConsoleGroup";

    //上传从桂花网获取的数据
    public static final String URL_UPLOAD_RECENT_HR_BY_MAC = "/fitness/V2/Heartrate/uploadRecentHeartratesByMacaddr";

    public static final String URL_POST_CASSIA_STATE = "/fitness/V2/School/setCassiaOnline";

    //获取上课状态列表信息
    public static final String URL_GET_PE_STATUS_INFO = "/fitness/V2/Pelesson/getLessonInfo";
    //获取上课数据
    public static final String URL_GET_PE_DATA = "/fitness/V2/Pelesson/getLessonHeartrates";



}
