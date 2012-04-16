package com.srz.androidtools.util;

import com.srz.androidtools.R;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
 

public class NetworkUtil {
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ? true : false;
    }

    public static boolean isGPRS(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ? true : false;
    }

    public static boolean toLoadImage(Context context) {
        boolean updateNonWIFI = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.key_load_image_preference), false);
        boolean shallUpdate = (NetworkUtil.isNetworkAvailable(context) && NetworkUtil.isWifi(context)) || (NetworkUtil.isNetworkAvailable(context) && !NetworkUtil.isWifi(context) && NetworkUtil.isGPRS(context) && updateNonWIFI);
        return shallUpdate;
    }

    public static boolean toUpdateSource(Context context) {
        boolean updateNonWIFI = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getString(R.string.key_auto_check_update_nonwifi_preference), false);
        boolean shallUpdate = (NetworkUtil.isNetworkAvailable(context) && NetworkUtil.isWifi(context)) || (NetworkUtil.isNetworkAvailable(context) && !NetworkUtil.isWifi(context) && NetworkUtil.isGPRS(context) && updateNonWIFI);
        return shallUpdate;
    }

    public static boolean isNetworkAvailable(Context context) {

        if (context != null) {

            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                return false;
            } else {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } else {
            Log.e("NetworkUtil", "No context.");
        }
        return false;
    }
}
