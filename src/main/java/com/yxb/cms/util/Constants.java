package com.yxb.cms.util;

/**
 * Created by dongao on 2018/5/5.
 */
public class Constants {

    /** cookie.domain*/
    public static final String COOKIEDOMAIN= "pipichongwu.com";
    /** cookie.path*/
    public static final String COOKIEPATH= "/";

    /** 搜索字符串前缀 */
    public static final String SEARCH_PREFIX = "search_";
    /** 排序字符串前缀 */
    public static final String ORDER_PREFIX = "order_";
    /** 按排序列的序号 */
    public static final String SORT_COL_NUM = "iSortCol_0";
    /** 排序类key的前缀 */
    public static final String SORT_COLUMN = "mDataProp_";
    /** 排序：升序  降序 */
    public static final String SORT_DIR = "sSortDir_0";
    /**UTF-8编码*/
    public static final String UTF8 = "UTF-8";
    public static final String OpenKey="Te@cherMe";
    public static final Long SLOW_SECOND= 5000l;//default 5秒
    public static final String FILE_PATH_FTL = "";

    public static final Integer PAGESIZE = 10;

    /** 服务器文件地址*/
    public static final String FILE = "/web/pet";
    /** 服务器文件地址*/
    public static final String FILEIMAGES = FILE+"images\\";
    /** 服务器文件地址*/
    public static final String FILEVIDEO = FILE+"video";


    /**小程序appid*/
    public static final String APPID = "wx21b9057159fbbe2c";
    /**小程序appid*/
    public static final String SECRET = "4374cd1b695123d66a98e1281ca1223d";
    /**小程序appid*/
    public static final String GRANT_TYPE = "authorization_code";
    /**小程序appid*/
    public static final String JSCODE2SESSION = "https://api.weixin.qq.com/sns/jscode2session";

    /** 一小时多少分钟 */
    public static final long MINUTES_IN_HOUR = 60L;
    /** 一分钟多少秒 */
    public static final long SECONDS_IN_MINUTE = 60L;
    /** 一小时多少秒 */
    public static final long SECONDS_IN_HOUR = SECONDS_IN_MINUTE * MINUTES_IN_HOUR;
    /** 一秒有多少毫秒 */
    public static final long MILLISECONDS_IN_SECOND = 1000;

}
