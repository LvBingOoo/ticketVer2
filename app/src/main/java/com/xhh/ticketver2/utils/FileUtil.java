package com.xhh.ticketver2.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Author:    hup
 * Date:      2016/8/10.
 * Description:
 */
public class FileUtil {


    private static final String PRE_FILE_NAME_KIT = "ticket";
    public static final String TOKEN = "token";
    public static final String USERNAME = "username";
    public static final String PRE_FILE_KFQQ = "qq";
    public static final String PRE_FILE_SAFETYPASSRORD = "safetypassword";
    public static final String PRE_FILE_RONG_TOKEN = "rong_token";
    public static final String PRE_FILE_RONG_GOURPID = "rong_gourpid";
    public static final String PRE_FILE_RONG_GOURPID_SUCCTESS = "rong_gourpid_succcss";
    public static final String PRE_FILE_DOMAIN = "base_domain";
    private static SharedPreferences mKITPreferences;


    /**
     * 获取KITsharepreference
     * @param context context
     * @return return
     */
    public static SharedPreferences getKITSharedPreferences(Context context) {
        if (mKITPreferences == null && context != null) {
            mKITPreferences = context.getSharedPreferences(PRE_FILE_NAME_KIT, Context.MODE_MULTI_PROCESS
                    + Context.MODE_PRIVATE);
        }
        return mKITPreferences;
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences pre = getKITSharedPreferences(context);
        pre.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences pre = getKITSharedPreferences(context);
        return pre.getString(key, "");
    }
    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences pre = getKITSharedPreferences(context);
        pre.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences pre = getKITSharedPreferences(context);
        return pre.getBoolean(key, false);
    }
    public static void saveLong(Context context, String key, long value) {
        SharedPreferences pre = getKITSharedPreferences(context);
        pre.edit().putLong(key, value).commit();
    }

    public static long getLong(Context context, String key) {
        SharedPreferences pre = getKITSharedPreferences(context);
        return pre.getLong(key, 0);
    }

    public static void saveInt(Context context, String key, int value) {
        SharedPreferences pre = getKITSharedPreferences(context);
        pre.edit().putInt(key, value).commit();
    }

    public static int getInt(Context context, String key) {
        SharedPreferences pre = getKITSharedPreferences(context);
        return pre.getInt(key, 0);
    }
}
