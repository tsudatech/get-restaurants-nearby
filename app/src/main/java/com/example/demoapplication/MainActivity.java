package com.example.demoapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class MainActivity extends AppCompatActivity implements RestaurantAsync.ConfirmAsyncListener, ImageGetTask.ImageGetTaskListener, AdapterView.OnItemClickListener{

    private static int arraySize = 10;
    private int currentCount = 0;
    private TextView textView;
    private GridView gridView;
    private Bitmap[] images;
    private ArrayList<HotPepperGourmet> hotPepperGourmetArray;
    private LocationManager mLocationManager;
    private MyLocationListener listener;
    private Button button;
    public ProgressDialog m_ProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // GPS
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        listener = new MyLocationListener(this);
//        boolean gpsFlg = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        Log.d("GPS Enabled", gpsFlg ? "OK" : "NG");

        textView = (TextView) findViewById(R.id.textView);
        gridView = (GridView) findViewById(R.id.gridview);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setActiveFlg(0);
                images = new Bitmap[arraySize];
                setCurrentCount(0);
                createProgressBar();
                callRequestLocationUpdates();
            }
        });
    }

    private void createProgressBar(){
        this.m_ProgressDialog = new ProgressDialog(this);
        this.m_ProgressDialog.setMessage("実行中...");
        this.m_ProgressDialog.setCanceledOnTouchOutside(false); // プログレスダイアログ外をタッチできないようにする
        this.m_ProgressDialog.show();
    }

    private void callRequestLocationUpdates()
    {
        //複数プロパイダで処理を回すことで高速化を目指す
        //removeUpdates()が起動されると全ての並列処理は終了する
        List<String> providers = mLocationManager.getProviders(true);
        checkPermission();
        for (String provider : providers) {
            mLocationManager.requestLocationUpdates(provider, 500, 1, listener); //リスナの定義は割愛
        }
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // パーミッションの許可を取得する
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1000);
        }
    }

    @Override
    public void onRestaurantAsyncCallBack() {
        for(int i = 0; i < hotPepperGourmetArray.size(); i++){
            int imageIndex = i;
            ImageGetTask task = new ImageGetTask(imageIndex, this);
            task.executeOnExecutor(THREAD_POOL_EXECUTOR,hotPepperGourmetArray.get(i).getGzUrl());
        }
        mLocationManager.removeUpdates(listener);
        listener.setActiveFlg(0);
    }

    @Override
    public void onImageGetTaskCallBack(){
        BitmapAdapter adapter = new BitmapAdapter(
                this.getApplicationContext(), R.layout.grid_items,
                this.images);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        if(hotPepperGourmetArray.size() > 0)
            this.textView.setText("");
        m_ProgressDialog.dismiss();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Uri uri = Uri.parse(hotPepperGourmetArray.get(position).getUrl());
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(i);
    }

    public void addImage(int index, Bitmap image){
        setCurrentCount(getCurrentCount() + 1);
        this.images[index] = image;
    }

    public int getImageSize(){
        return this.images.length;
    }

    public void setCurrentCount(int currentCount){
        this.currentCount = currentCount;
    }

    public int getCurrentCount(){
        return this.currentCount;
    }

    public void setHotPepperGourmetArray(ArrayList<HotPepperGourmet> hotPepperGourmetArray){
        this.hotPepperGourmetArray = hotPepperGourmetArray;
    }

    public ArrayList<HotPepperGourmet> getHotPepperGourmetArray(){
        return this.hotPepperGourmetArray;
    }
}
