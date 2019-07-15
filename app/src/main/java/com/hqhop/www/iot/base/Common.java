package com.hqhop.www.iot.base;

import com.hqhop.www.iot.BuildConfig;

/**
 * Created by allen on 2017/7/5.
 */

public class Common {
    //    public static String BASE_URL = "http://app.4kb.cn/";
//    public static String BASE_URL = "http://223.85.202.110:29999/";
//    public static String BASE_URL = "http://hqhops.4kb.cn/";
//    public static String BASE_URL = "http://120.55.240.77:48301/";
//    public static String BASE_URL = "http://192.168.106.61:9999/";
//    public static String BASE_URL = BuildConfig.DEBUG ? "http://223.85.202.110:29999/" : "http://120.55.240.77:48301/";

    public static String BASE_URL = BuildConfig.DEBUG ? "http://iot.hqhop.com:30101/" : "http://iot.hqhop.com:30101/";
//    public static String BASE_URL = BuildConfig.DEBUG ? "http://iot.hopnet.cn:48301/" : "http://223.85.202.110:29876/";

    public static String BASE_DEBUG_URL = "http://iot.hqhop.com:30101/";
//    public static String BASE_DEBUG_URL = "http://iot.hopnet.cn:48301/";

        public static String BASE_RELEASE_URL = "http://iot.hqhop.com:30101/";
//    public static String BASE_RELEASE_URL = "http://iot.hopnet.cn:48301/";

    public static final String GRANT_TYPE = "password";

    public static final int REQUEST_CODE_PERMISSION = 127;

    public static final int REQUEST_CODE_QRCODE = 7723;

    /**
     * 天气查询KEY
     */
    public static final String WEATHER_KEY = "8fbd3361aa6c4b8bafa451b27a3adfa7";

    /**
     * 天气图片url
     */
    public static final String WEATHER_ICON_URL = "https://cdn.heweather.com/cond_icon/";

    /**
     * 讯飞开放平台APPID
     */
    public static final String XF_APPID = "59b11065";

    public static final String HQHP_SITE_URL = "http://www.hqhop.com";

    /**
     * 折叠展开动画的播放时长
     */
    public static final int DURATION_FOLD_ANIMATION = 250;

    /**
     * 获取验证码的间隔时间
     */
    public static final int TIME_INTERVAL_VERIFICATION_CODE = 60 * 1000;

    /**
     * 刷新倒计时的间隔时间
     */
    public static final int TIME_INTERVAL_REFRESH_COUNTDOWN = 1000;

    /**
     * 站点类型(用于Intent传值)
     */
    public static final String TYPE_STATION = "TYPE";

    /**
     * 报警信息传递的name
     */
    public static final String BUNDLE_ALERT = "ALERT_DATA";

    /**
     * 液位信息传递的name
     */
    public static final String BUNDLE_LIQUID = "LIQUID_DATA";

    /**
     * 维保信息传递的name
     */
    public static final String BUNDLE_MAINTAIN = "MAINTAIN_DATA";

    /**
     * 具体站点信息传递的name
     */
    public static final String BUNDLE_STATION = "STATION_DATA";

    /**
     * 关注页面具体站点信息传递的name
     */
    public static final String BUNDLE_FOLLOW_STATION = "STATION_FOLLOW_DATA";

    /**
     * 巡检时间的name
     */
    public static final String POLLING_TIME = "POLLING_TIME";

    /**
     * 巡检地点的name
     */
    public static final String POLLING_ADDRESS = "POLLING_ADDRESS";

    /**
     * 保存显示模块的SharedPreferences的名字
     */
    public static final String SHARED_PREFERENCES_MODULE_SETTING = "WHAT_TO_SHOW";

    /**
     * 设置显示模块的request code
     */
    public static final int REQUEST_CODE_MODULE_SETTING = 1000;

    /**
     * 设置显示模块成功的result code
     */
    public static final int RESULT_CODE_MODULE_SETTING = 1001;

    public static final String IS_SHOW_LIQUID = "IS_SHOW_LIQUID";

    public static final String IS_SHOW_BUSINESS = "IS_SHOW_BUSINESS";

    public static final String IS_SHOW_PRESSURE = "IS_SHOW_PRESSURE";

    /**
     * 站点详细模块数据库前缀名
     */
    public static final String DB_DETAIL_PREFIX_NAME = "station_detail";

    /**
     * 站点快捷模块数据库前缀名
     */
    public static final String DB_QUICK_PREFIX_NAME = "station_quick";

    public static final String SP_USER_INFO = "USER_INFO";
}
