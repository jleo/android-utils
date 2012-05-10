package com.srz.androidtools.util;

import android.content.Context;

public class ResTools {
    public static String getString(String name, Context context) {
        try {
            return context.getResources().getString(context.getResources().getIdentifier(name, "string", context.getPackageName()));
        }catch(Exception e) {
            e.printStackTrace();
            return null;
            
        }
        
    }

    public static int getDrawable(String name, Context context) {
        int drawable = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        if(drawable == 0){//not found
            return context.getResources().getIdentifier("unknown", "drawable", context.getPackageName());

        }
        return drawable;
    }

}
