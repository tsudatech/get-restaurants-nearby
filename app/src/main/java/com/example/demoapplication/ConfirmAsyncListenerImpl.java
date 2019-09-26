package com.example.demoapplication;

import android.graphics.Bitmap;
import android.provider.ContactsContract;

import java.util.List;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class ConfirmAsyncListenerImpl implements RestaurantAsync.ConfirmAsyncListener {

    private MainActivity activity;
    private LocationService locationService;

    public ConfirmAsyncListenerImpl(MainActivity activity, LocationService locationService){
        this.activity = activity;
        this.locationService = locationService;
    }

    // ホットペッパー取得後のコールバック
    @Override
    public void onRestaurantAsyncCallBack(List<HotPepperGourmet> hotPepperGourmetArray) {
        // ホットペッパー取得結果をホルダーにセット
        HotPepperGourmetListHolder hotPepperGourmetListHolder = new HotPepperGourmetListHolder(hotPepperGourmetArray);

        // 画像格納用ホルダーの初期化
        ImagesHolder imagesHolder = new ImagesHolder();
        imagesHolder.setImages(new Bitmap[Constants.IMAGE_LIST_SIZE]);
        imagesHolder.setCurrentCount(0);

        // 画像取得処理
        ImageGetTaskListenerImpl imageGetTaskListener = new ImageGetTaskListenerImpl(activity, hotPepperGourmetListHolder, imagesHolder);
        if(hotPepperGourmetListHolder.getHotPepperGourmetListSize() > 0){
            for(int i = 0; i < hotPepperGourmetListHolder.getHotPepperGourmetListSize(); i++){
                int imageIndex = i;
                ImageGetTask task = new ImageGetTask(imageIndex, imageGetTaskListener, imagesHolder);
                task.executeOnExecutor(THREAD_POOL_EXECUTOR,hotPepperGourmetListHolder.getHotPepperGourmetByIndex(i).getGzUrl());
            }
        }else{
            activity.setText("検索結果が見つかりませんでした。");
        }
        locationService.stopLocationService();
    }
}
