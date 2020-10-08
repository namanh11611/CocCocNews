package com.namanh.coccocnews.utils;

import android.util.Log;

import com.namanh.coccocnews.BuildConfig;

public class LogUtil {

    private static final String CUSTOM_TAG = "#CCN";

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    private static String getTag(String tag) {
        return tag == null ? (CUSTOM_TAG + "") : (CUSTOM_TAG + "_" + tag);
    }

    public static void i(String tag, String message) {
        Log.i(getTag(tag), message);
    }

    public static void d(String tag, String message) {
        if (isDebug()) Log.d(getTag(tag), message);
    }

    public static void d(String tag, String message, Throwable throwable) {
        if (isDebug()) Log.d(getTag(tag), message + Log.getStackTraceString(throwable));
    }

    public static void w(String tag, String message) {
        if (isDebug()) Log.w(getTag(tag), message);
    }

    public static void e(String tag, String message) {
        if (isDebug()) Log.e(getTag(tag), message);
    }

    public static void throwEx(String tag, String message) {
        throw new RuntimeException(getTag(tag) + ": " + message);
    }

}
