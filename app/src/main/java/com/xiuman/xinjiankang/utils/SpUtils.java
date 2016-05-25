package com.xiuman.xinjiankang.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.xiuman.xinjiankang.app.GlobalContext;


/**
 * 描述：SharedPreferences工具类
 * 名称：SpUtil
 *
 * @author csx
 *         date：2015/1/3 0003.
 */
public class SpUtils {
    private static final String SP_NAME = "xingduoduo_pref";

    /**
     * 描述：将布尔值写入SharedPreferences文件中
     *
     * @param key   键
     * @param value 值
     */
    public static void setBoolean(String key, boolean value) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value).commit();
    }

    /**
     * 描述：获取存储的boolean数据
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 描述：存储String数据到SharedPreferences中
     *
     * @param key
     * @param value
     */
    public static void setString(String key, String value) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value).commit();
    }

    /**
     * 描述：获取存储的String数据
     *
     * @param key 键
     * @return
     */
    public static String getString(String key) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }
    public static long getLong(String key) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getLong(key, 0);
    }
    public static void setLong(String key,long value) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value).commit();
    }

    /**
     * 描述：保存int
     *
     * @param key
     * @param value
     */
    public static void setInt(String key, int value) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value).commit();
    }

    /**
     * 描述：取出int值
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(String key, int defaultValue) {
        SharedPreferences sp = GlobalContext.getInstance().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }
}
