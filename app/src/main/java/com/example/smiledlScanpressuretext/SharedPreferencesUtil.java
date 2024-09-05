package com.example.smiledlScanpressuretext;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Description: SharedPreferences工具类
 */
public class SharedPreferencesUtil {

    public static final String ALL_TEST_TIME = "all_test_time";
    public static final String ALL_TEST_COUNT = "all_test_count";

    private static SharedPreferencesUtil mUtil = new SharedPreferencesUtil();

    public static SharedPreferencesUtil getInstance() {
        return mUtil;
    }

    private static SharedPreferences mSharedPreferences;

    Context mContext;

    public void Init(Context mContext) {
        this.mContext = mContext;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("protest_gaode", 0);
        }
        return mSharedPreferences;

    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(Context context, String key, String value) {
        return getSharedPreferences(context).getString(key, value);
    }

    public String getString(String key, String value) {
        return getSharedPreferences(mContext).getString(key, value);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean value) {
        return getSharedPreferences(context).getBoolean(key, value);
    }

    public static void putLong(Context context, String key, Long value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static Long getLong(Context context, String key, Long value) {
        return getSharedPreferences(context).getLong(key, value);
    }

    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getInt(Context context, String key, int value) {
        return getSharedPreferences(context).getInt(key, value);
    }

    public static void remove(Context context, String key) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(key);
        editor.commit();
    }
}
