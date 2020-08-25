package com.sports2020.demo;

import android.util.Log;

/**
 * 日志打印 {@link AppConfig#IS_DEBUG} 控制日志开关
 *
 * @author tsx
 */
public class Logger {

    public static void d(String tag, String msg) {
        if (AppConfig.IS_DEBUG)
            Log.d(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (AppConfig.IS_DEBUG)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (AppConfig.IS_DEBUG)
            Log.v(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (AppConfig.IS_DEBUG)
            Log.e(tag, msg);
    }

    public static void wtf(String tag, String msg) {
        if (AppConfig.IS_DEBUG)
            Log.wtf(tag, msg);
    }
}
