package com.sports2020.demo;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * SharePreference管理类
 *
 * @author tsx
 */
public class AppPreference {
    public static final String TAG = AppPreference.class.getSimpleName();

    public static final String SHARED_PREFERENCE_FILE_NAME = AppApplication.getInstance().getPackageName() + ".sharedpreference";

    private static SharedPreferences getPreferences() {
        return AppApplication.getInstance().getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Activity.MODE_PRIVATE);
    }

    /**
     * 清除所有share配置
     */
    public static void clearAll() {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.clear();
        editor.apply();
    }

    public static void removeKey(String key) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * 设置整型值
     *
     * @param key   键
     * @param value 值
     */
    public static void setIntValue(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 获取整型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 整型值
     */
    public static int getIntValue(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    /**
     * 设置字符型值
     *
     * @param key   键
     * @param value 值
     */
    public static void setStringValue(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取字符型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 字符型值
     */
    public static String getStringValue(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    /**
     * 设置布尔型值
     *
     * @param key   键
     * @param value 值
     */
    public static void setBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 获取布尔型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 布尔型值
     */
    public static boolean getBooleanValue(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    /**
     * 设置布浮点值
     *
     * @param key   键
     * @param value 值
     */
    public static void setFloatValue(String key, float value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 获取浮点型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 浮点型值
     */
    public static float getFloatValue(String key, float defaultValue) {
        return getPreferences().getFloat(key, defaultValue);
    }

    /**
     * 设置长整型值
     *
     * @param key   键
     * @param value 值
     */
    public static void setLongValue(String key, long value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 获取长整型值
     *
     * @param key          键
     * @param defaultValue 默认值
     * @return 长整型值
     */
    public static long getLongValue(String key, long defaultValue) {
        return getPreferences().getLong(key, defaultValue);
    }
}
