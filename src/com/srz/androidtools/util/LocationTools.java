package com.srz.androidtools.util;
 
 

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
 
import com.srz.androidtools.R;

/**
 * @author shang
 * sample1： 
 *     locationTools = new LocationTools(MapActivityBase.this, new Handler() {
                        public void handleMessage(Message msg) {  
                            Location location = locationTools.getNowLocation() ;
                            int lat = (int) (location.getLatitude() * 1E6);
                            int lng = (int) (location.getLongitude() * 1E6); 
                            locationTools.stopListen();
                            ...
                        }
                    }); 
      locationTools.startListen() ;
      
 *     sample2:
 *     Location location = LocationTools.getLastKnownLocation((LocationManager)getSystemService(Context.LOCATION_SERVICE););
 */


public class LocationTools {
    private static final int GPS_WAIT_TIME = 20000;
    private Activity activity;
    private Handler handler;
    private LocationManager locationManager = null;
    private LocationProvider locationProvider;
    private boolean islocationListenerSetup = false;

    public LocationTools(Activity activity, Handler handler) {
        setActivity(activity);
        this.handler = handler;
        this.locationManager = (LocationManager) this.activity
                .getSystemService(Context.LOCATION_SERVICE);

        // if(locationProvider != null)
        // locationManager.requestLocationUpdates(locationProvider.getName(),
        // 2000, 0, locationListener);
    }

    private Activity getActivity() {
        return activity;
    }

    public boolean isAnyProviderEnabled() {
        if (locationProvider == null)
            return false;
        else
            return true;
    }

    private void setActivity(Activity activity) {
        this.activity = activity;

    }

    private LocationProvider checkProviders() {
//        if(isUsesetCriteriaChoseProvider &&  this.locationProvider != null)
//             return this.locationProvider;
        LocationProvider _locationProvider = null;
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            _locationProvider = locationManager
                    .getProvider(LocationManager.NETWORK_PROVIDER);

        } else if (locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            _locationProvider = locationManager
                    .getProvider(LocationManager.GPS_PROVIDER);
        }
        return _locationProvider;

    }
 

    public Location getNowLocation() {
        if (!isAnyProviderEnabled())
            return null;

        Location location = locationManager
                .getLastKnownLocation(locationProvider.getName());
        // loctionManager.requestLocationUpdates(provider, 2000, 10,
        // locationListener);
        if (location == null)
            return null;

        return location;
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

    private void updateWithNewLocation(Location location) {
        if (this.handler == null)
            return;
        if (location == null)
            Toast.makeText(this.activity, R.string.nonetworklocation, 1500).show();
        else {
            this.handler.sendEmptyMessage(1);
        }

    }
    
    public void stopListen() {
        if (islocationListenerSetup) {
            locationManager.removeUpdates(locationListener);
            islocationListenerSetup = false;
        }
    }
    
    public void startListen() {
        locationProvider = checkProviders();
        if (locationProvider == null)
            Toast.makeText(this.activity, R.string.nonetworklocation, 1500).show();

        if (locationProvider != null && islocationListenerSetup == false) {
            locationManager.requestLocationUpdates(locationProvider.getName(),
                    2000, 0, locationListener);
            islocationListenerSetup = true;
        }

    }
    
    //简单得到最后定位
    public static Location getLastKnownLocation(final LocationManager pLocationManager) {
        if (pLocationManager == null)  
            return null;
        final Location gpsLocation =
            pLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        final Location networkLocation =
            pLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (gpsLocation == null) 
            return networkLocation;
        else if (networkLocation == null) 
            return gpsLocation;
        else {
            if (networkLocation.getTime() > gpsLocation.getTime() + GPS_WAIT_TIME) 
                return networkLocation;
            else 
                return gpsLocation;
        }
    }
    
    
    //暂时不用
    private boolean isUsesetCriteriaChoseProvider = false;
    public void  setCriteriaChoseProvider(int accuracy, boolean altitudeRequired, boolean bearingRequired,
             boolean costAllowed, int powerlevel ) {
        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精度
//        criteria.setAltitudeRequired(false);// 不要求海拔
//        criteria.setBearingRequired(false);// 不要求方位
//        criteria.setCostAllowed(true); 
//        criteria.setPowerRequirement(Criteria.POWER_LOW);// 低功耗
        criteria.setAccuracy(accuracy); 
        criteria.setAltitudeRequired(altitudeRequired); 
        criteria.setBearingRequired(bearingRequired);
        criteria.setCostAllowed(costAllowed); 
        criteria.setPowerRequirement(powerlevel);
       
        String bestproviderString = locationManager.getBestProvider(criteria, true);
        if (bestproviderString == null) {
            isUsesetCriteriaChoseProvider = false;
            return;
        }
        this.locationProvider = locationManager.getProvider(bestproviderString); 
        isUsesetCriteriaChoseProvider = true;
    }
}
