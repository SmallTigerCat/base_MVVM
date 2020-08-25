package com.sports2020.demo;

/**
 * APP 常量配置
 *
 * @author tsx
 */
public class AppConstant {


    public static final String DB_NAME = "jb_course.db";//数据库名

    /**
     * http请求读取超时时间，单位 秒
     */
    public static final int READ_TIMEOUT = 20;
    /**
     * http请求写入超时时间，单位 秒
     */
    public static final int WRITE_TIMEOUT = 20;
    /**
     * http请求连接超时时间，单位 秒
     */
    public static final int CONN_TIMEOUT = 10;
    /**
     * 请求头 appId
     */
    public static final String HTTP_HEADER_APP_ID = "jbkj_app_Ecommerce";
    /**
     * 接口调用平台 plat
     */
    public static final String HTTP_HEADER_PLAT = "android";
    /**
     * 数据请求格式 MediaType 为 json 字符串
     */
    public static final String MEDIA_TYPE_JSON_STRING = "application/json; charset=utf-8";

    public static final class lineUpType {
        public static final int IN_CLASS = 1;        // 上课
        public static final int FINISH_CLASS = 2;    // 下课
    }

}
