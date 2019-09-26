package com.example.demoapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

public class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {

    private List<HotPepperGourmet> hotPepperGourmetList;
    private Activity activity;

    public OnItemClickListenerImpl(List<HotPepperGourmet> hotPepperGourmetList, Activity activity){
        this.hotPepperGourmetList = hotPepperGourmetList;
        this.activity = activity;
    }

    // gridViewの画像をクリックリスナー
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Uri uri = Uri.parse(this.hotPepperGourmetList.get(position).getUrl());
        Intent i = new Intent(Intent.ACTION_VIEW,uri);
        activity.startActivity(i);
    }
}
