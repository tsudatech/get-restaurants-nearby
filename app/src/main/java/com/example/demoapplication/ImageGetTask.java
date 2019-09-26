package com.example.demoapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageGetTask extends AsyncTask<String,Void, Bitmap> {
    private int index;
    private ImageGetTaskListener imageGetTaskListener;
    private ImagesHolder imagesHolder;

    public ImageGetTask(int index, ImageGetTaskListener imageGetTaskListener, ImagesHolder imagesHolder) {
        this.index = index;
        this.imageGetTaskListener = imageGetTaskListener;
        this.imagesHolder = imagesHolder;
    }

    // ホットペッパーAPIの画像URLを使って画像を取得する
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap image;
        try {
            URL imageUrl = new URL(params[0]);
            BufferedInputStream imageIs;
            imageIs = new BufferedInputStream(imageUrl.openStream());
            image = BitmapFactory.decodeStream(imageIs);
            return image;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    // コールバックを呼び出す
    @Override
    protected void onPostExecute(Bitmap result) {
        imagesHolder.addImage(this.index, result);
        if((imagesHolder.getImageSize() == imagesHolder.getCurrentCount()) && (imageGetTaskListener instanceof ImageGetTaskListener))
            imageGetTaskListener.onImageGetTaskCallBack();
    }

    // コールバック用のリスナーインターフェース
    interface ImageGetTaskListener {
        void onImageGetTaskCallBack();
    }
}
