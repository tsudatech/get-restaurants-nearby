package com.example.demoapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;

import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class GetImagesLogic {

    private MainActivity activity;

    public GetImagesLogic(MainActivity activity){
        this.activity = activity;
    }

    public void doMainLogic(){

        // GridViewのクリア
        activity.setAdaptor(null);

        // 位置情報の取得を呼び出す
        LocationService locationService = new LocationService(activity);
        locationService.callRequestLocationUpdates();

    }
}
