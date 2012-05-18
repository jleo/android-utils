package com.srz.androidtools.util;

import android.content.Context;

public class ResTools {
    public static String getString(String name, Context context) {
        int stringID = context.getResources().getIdentifier(name, "string", context.getPackageName());
        if (stringID != 0)
            return context.getResources().getString(stringID);
        return null;
    }

    public static int getDrawable(String name, Context context) {
        int drawable = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        if (drawable == 0) {//not found
            return context.getResources().getIdentifier("unknown", "drawable", context.getPackageName());

        }
        return drawable;
    }

}
