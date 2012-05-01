package com.srz.androidtools.util;

import android.content.Context;

/**
 * Created with IntelliJ IDEA.
 * User: jleo
 * Date: 12-4-28
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
public class PreferenceUtil {
    public static final String SHARED_PREFERENCE_KEY = "Global";
    public static final String FIRST_TIMER = "FIRST_TIMER";

    public static boolean isFirstTimeBoot(Context context) {
        return context.getSharedPreferences(SHARED_PREFERENCE_KEY + "-" + context.getPackageName(), 0).getBoolean(FIRST_TIMER, true);
    }

    public static void setFirstTimeBoot(Context context, boolean isFirstTime) {
        context.getSharedPreferences(SHARED_PREFERENCE_KEY + "-" + context.getPackageName(),0).edit().putBoolean(FIRST_TIMER, isFirstTime).commit();
    }
}
