package com.example.demoapplication;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BitmapAdapter extends BaseAdapter {

    private int resourceId;
    private Bitmap[] images;
    private LayoutInflater inflater;
    private int layoutId;


    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    BitmapAdapter(Context context,
                int layoutId,
                Bitmap[] images) {

        super();
        this.inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
        this.images = images;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.image_view);
            holder.textView = convertView.findViewById(R.id.text_view);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageBitmap(this.images[position]);

        return convertView;
    }

    @Override
    public int getCount() {
        // ここでちゃんと配列の要素数を返しておかないと、gridviewに画像がセットされない
        return this.images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
