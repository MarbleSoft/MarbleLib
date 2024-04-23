package com.mar.lib.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import static android.content.Context.LOCATION_SERVICE;

public class LocationHelper implements LocationListener {
    private Activity main;
    private LocationListener locationListener;
    private OnGetLocationListener onGetLocationListener;
//    private Lo
    public LocationHelper(Activity main,LocationListener locationListener) {
        this.main = main;
        this.locationListener = locationListener;
    }

    public LocationHelper(Activity main) {
        this.main = main;
    }

    private TextView textView;
    @SuppressLint("MissingPermission")
    public void startLocation(TextView textView){
        this.textView = textView;
        try {
            LocationManager locationManager = (LocationManager) main
                    .getSystemService(LOCATION_SERVICE);
            // get GPS status
    //        boolean checkGPS = locationManager
    //                .isProviderEnabled(LocationManager.GPS_PROVIDER);
            // get network provider status
            //System.err: java.lang.SecurityException: "network" location provider requires ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION permission.
            boolean networkLocate = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(networkLocate){
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,2000,1,this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        textView.append(location.getLatitude()+","+location.getLongitude());
    }

    @Deprecated
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        textView.append("\n"+provider+",enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        textView.append("\n"+provider+",disabled");
    }

    public interface OnGetLocationListener{
        void onGetLocation();
    }
}
