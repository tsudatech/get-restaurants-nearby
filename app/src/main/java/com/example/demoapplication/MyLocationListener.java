package com.example.demoapplication;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import java.io.IOException;
import java.net.URL;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class MyLocationListener implements LocationListener {

    private double lat = 0;
    private double lng = 0;
    private LocationService locationService;
    private MainActivity activity;
    private int activeFlg = 0;
    public MyLocationListener(MainActivity activity, LocationService locationService){
        this.activity = activity;
        this.locationService = locationService;
    }

    // 位置情報を取得したら呼び出される(並列で起動される可能性があるため、synchronized)
    @Override
    public synchronized void onLocationChanged(Location location) {
        String msg = "Lat=" + location.getLatitude()
                + "\nLng=" + location.getLongitude();
        lat = location.getLatitude();
        lng = location.getLongitude();
        if(getActiveFlg() == 0){
            setActiveFlg(1);
            requestResutaurantsInfo(locationService, lat, lng);
        }
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

    // ホットペッパーAPIを呼び出す
    public void requestResutaurantsInfo(LocationService locationService, Double lat, Double lng){
        try {
            URL url = new URL("http://webservice.recruit.co.jp/hotpepper/gourmet/v1/?key=3821e6b6d4ce8074&format=json&range=3000m&count=" + Constants.IMAGE_LIST_SIZE + "&lat=" + String.valueOf(lat) + "&lng=" + String.valueOf(lng));
            System.out.println(url);
            RestaurantAsync.ConfirmAsyncListener confirmAsyncListener = new ConfirmAsyncListenerImpl(activity, locationService);
            new RestaurantAsync(confirmAsyncListener).executeOnExecutor(THREAD_POOL_EXECUTOR,url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {

        }
    }

    public void setActiveFlg(int activeFlg){
        this.activeFlg = activeFlg;
    }

    public int getActiveFlg(){
        return this.activeFlg;
    }
}
