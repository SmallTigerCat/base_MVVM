package com.sports2020.demo;

/**
 * APP 配置类
 *
 * @author tsx
 */
public class AppConfig {

    public static ServerName serverName = ServerName.SERVER_TEST;
    public static boolean IS_DEBUG = true;

    private static String apiUrl;//基本网络请求地址

    public static void setUrl() {
        switch (serverName) {
            case SERVER_RELEASE://生产环境
                apiUrl = "http://www.jiabuedu.cn:8848/api/";
                break;

            case SERVER_TEST://测试环境
                apiUrl = "http://www.jiabuedu.cn:8848/api/";
                break;
        }
    }

    public static String getApiUrl() {
        return apiUrl;
    }

    /**
     * 服务器类型
     */
    public enum ServerName {
        SERVER_RELEASE,
        SERVER_TEST,
    }
}
