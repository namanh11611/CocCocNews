package com.namanh.coccocnews.helper;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.namanh.coccocnews.application.AppExecutors;

public class DarkModePreference {

    private static final String PREF_NAME = "dark_mode_pref";
    private static final String KEY = "dark_mode";

    private static DarkModePreference DARK_MODE_PREF = null;
    private static SharedPreferences preferences;

    private DarkModePreference(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, context.MODE_PRIVATE);
    }

    public static DarkModePreference getInstance(Context context) {
        if (DARK_MODE_PREF == null) {
            synchronized (AppExecutors.class) {
                if (DARK_MODE_PREF == null) {
                    DARK_MODE_PREF = new DarkModePreference(context);
                }
            }
        }
        return DARK_MODE_PREF;
    }

    public static void putDarkMode(int mode) {
        preferences.edit().putInt(KEY, mode).commit();
    }

    public static int getDarkMode() {
        return preferences.getInt(KEY, AppCompatDelegate.MODE_NIGHT_NO);
    }
}
