package com.srz.androidtools.util;

import android.content.Context;

public class ResTools {
    public static String getString(String name, Context context) {
        return context.getResources().getString(context.getResources().getIdentifier(name, "string", context.getPackageName()));
    }

    public static int getDrawable(String name, Context context) {
        return context.getResources().getIdentifier(name, "drawable", context.getPackageName());
    }

}