package com.example.demoapplication;

import android.graphics.Bitmap;

public class ImagesHolder {
    private int currentCount = 0;
    private Bitmap[] images;

    public void addImage(int index, Bitmap image){
        setCurrentCount(getCurrentCount() + 1);
        images[index] = image;
    }

    public void setImages(Bitmap[] imagesInput){
        images = imagesInput;
    }

    public Bitmap[] getImages(){
        return images;
    }

    public int getImageSize(){
        return images.length;
    }

    public void setCurrentCount(int currentCountInput){
        currentCount = currentCountInput;
    }

    public int getCurrentCount(){
        return currentCount;
    }
}
