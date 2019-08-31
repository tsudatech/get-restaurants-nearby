package com.example.demoapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageGetTask extends AsyncTask<String,Void, Bitmap> {
    private int index;
    private MainActivity activity;

    public ImageGetTask(int index, MainActivity activity) {
        this.index = index;
        this.activity = activity;
    }
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
    @Override
    protected void onPostExecute(Bitmap result) {
        activity.addImage(this.index, result);
        if((activity.getImageSize() == activity.getCurrentCount()) && (activity instanceof ImageGetTaskListener))
            activity.onImageGetTaskCallBack();
    }

    interface ImageGetTaskListener {
        void onImageGetTaskCallBack();
    }
}
