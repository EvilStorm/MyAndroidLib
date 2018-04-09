package com.moremon.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefData {

	private static final String SHARED_FILE_TITLE = "lib_pref";


    public static void clearAll(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_FILE_TITLE, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultData) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        return pref.getBoolean(key, defaultData);
    }
    public static void putBoolean(Context context, String key, boolean flag) {
        SharedPreferences p = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit();
        e.putBoolean(key, flag);
        e.commit();
    }
    public static int getInt(Context context, String key, int defaultData) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        return pref.getInt(key, defaultData);
    }
    public static void putInt(Context context, String _key, int _data) {
        SharedPreferences p = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit();
        e.putInt(_key, _data);
        e.commit();
    }
    public static long getLong(Context context, String key, long defaultData) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        return pref.getLong(key, defaultData);
    }
    public static void putLong(Context context, String _key, long _data) {
        SharedPreferences p = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit();
        e.putLong(_key, _data);
        e.commit();
    }
    public static double getDouble(Context context, final String key, final double defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        return Double.longBitsToDouble(pref.getLong(key, Double.doubleToLongBits(defaultValue)));
    }
    public static void putDouble(Context context, final String key, final double value) {
        SharedPreferences p = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit();
        e.putLong(key, Double.doubleToRawLongBits(value));
        e.commit();
    }

    public static String getString(Context context, String key, String defaultData) {
        SharedPreferences pref = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        return pref.getString(key, defaultData);
    }
    public static void putString(Context context, String key, String data) {
        SharedPreferences p = context.getSharedPreferences(SHARED_FILE_TITLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = p.edit();
        e.putString(key, data);
        e.commit();
    }
}
