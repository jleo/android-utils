package com.srz.androidtools.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 5/26/11
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class DeviceInfo {
    private int displayWidth;
    private int displayHeight;
    private int width;
    private int height;
    private static DeviceInfo deviceInfo;
    private float density;
    private int statusBarHeight;

    private DeviceInfo() {

    }

    public static synchronized DeviceInfo getInstance(Activity activity) {
        if (deviceInfo == null) {
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            int statusBarHeight = 0;
            switch (dm.densityDpi) {
                case DisplayMetrics.DENSITY_HIGH:
                    statusBarHeight = 48;
                    break;
                case DisplayMetrics.DENSITY_MEDIUM:
                    statusBarHeight = 32;
                    break;
                case DisplayMetrics.DENSITY_LOW:
                    statusBarHeight = 24;
                    break;
                default:

            }
            deviceInfo = new DeviceInfo();
            deviceInfo.setDisplayHeight((int) ((activity.getWindowManager().getDefaultDisplay().getHeight()) - statusBarHeight*dm.density));
            deviceInfo.setDisplayWidth((activity.getWindowManager().getDefaultDisplay().getWidth()));
            deviceInfo.setStatusBarHeight(statusBarHeight);
            deviceInfo.setWidth(activity.getWindowManager().getDefaultDisplay().getWidth());
            deviceInfo.setHeight(activity.getWindowManager().getDefaultDisplay().getHeight());
            deviceInfo.setDensityScale(dm.density);
        }
        return deviceInfo;

    }



    private void setStatusBarHeight(int contentHeight) {
        this.statusBarHeight = contentHeight;
    }

    private void setDensityScale(float density) {
        this.density = density;
    }

    public float getDensity() {
        return density;
    }

    public int getStatusBarHeight() {
        return statusBarHeight;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    public boolean isLargeScreen() {
        return height >= 800;
    }

    public boolean isSmallScreen() {
        return height == 320;
    }


    @Override
    public String toString() {
        return "display:" + displayWidth + "," + displayHeight + ", actual:" + width + "," + height + ",scale:" + density;
    }
}
