package com.example.demoapplication;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class MyLocationListener implements LocationListener {


    private double lat = 0;
    private double lng = 0;
    MainActivity activity;
    private int activeFlg = 0;

    public MyLocationListener(MainActivity activity){
        this.activity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {
        String msg = "Lat=" + location.getLatitude()
                + "\nLng=" + location.getLongitude();
        lat = location.getLatitude();
        lng = location.getLongitude();
        if(getActiveFlg() == 0){
            setActiveFlg(1);
            requestResutaurantsInfo(activity, lat, lng);
        }
//        Log.d("GPS", msg);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    private void requestResutaurantsInfo(MainActivity activity, Double lat, Double lng){
        try {
            URL url = new URL("http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=3821e6b6d4ce8074&format=json&range=3000m&count=" + activity.getImageSize() + "&lat=" + String.valueOf(lat) + "&lng=" + String.valueOf(lng));
            System.out.println(url);
            new RestaurantAsync(activity).executeOnExecutor(THREAD_POOL_EXECUTOR,url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setActiveFlg(int activeFlg){
        this.activeFlg = activeFlg;
    }

    public int getActiveFlg(){
        return this.activeFlg;
    }
}
