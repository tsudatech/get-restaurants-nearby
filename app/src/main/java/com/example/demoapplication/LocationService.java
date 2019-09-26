package com.example.demoapplication;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Handler;

import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class LocationService {

    private Timer locationTimer;
    private long time;
    private LocationManager mLocationManager;
    private MyLocationListener listener;
    MainActivity activity;

    public LocationService(MainActivity activity){
        this.activity = activity;
    }

    // 位置情報を取得する
    public void callRequestLocationUpdates()
    {
        // GPS
        mLocationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener(activity,this);

        // 位置情報取得用のタイマー
        locationTimer = new Timer(true);
        time = 0L;
        final Handler handler = new Handler();

        // タイマー起動
        locationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (time >= (1 * 1000L)) {
                            activity.setText("位置情報の取得に時間がかかっています。\n"
                                    + "もう一度検索を押下してください。");
                            stopLocationService();
                            activity.dismissProgressDialog();
                        }
                        time = time + 1000L;
                    }
                });
            }
        }, 0L, 1000L);

        //複数プロパイダで処理を回すことで高速化を目指す
        //removeUpdates()が起動されると全ての並列処理は終了する
        List<String> providers = mLocationManager.getProviders(true);
        checkPermission();
        for (String provider : providers) {
            mLocationManager.requestLocationUpdates(provider, 500, 1, listener); //リスナの定義は割愛
        }
    }

    // パーミッションをチェックする
    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // パーミッションの許可を取得する
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }
    }

    // 位置情報の取得を終了する
    public void stopLocationService() {
        if (locationTimer != null) {
            locationTimer.cancel();
            locationTimer.purge();
            locationTimer = null;
        }
        if (mLocationManager != null) {
            if (listener != null) {
                mLocationManager.removeUpdates(listener);
                listener.setActiveFlg(0);
                listener = null;
            }
            mLocationManager = null;
        }
    }

}
