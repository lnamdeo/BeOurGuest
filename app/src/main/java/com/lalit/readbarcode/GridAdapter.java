package com.lalit.readbarcode;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by LALIT on 26-06-2017.
 */

public class GridAdapter extends BaseAdapter {

    Activity activity;
    int[] imgs;
    String[] titles;

    public GridAdapter(Activity activity, int[] imgs, String[] titles) {
        this.activity = activity;
        this.imgs = imgs;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater l = activity.getLayoutInflater();
        View view = l.inflate(R.layout.grid_item, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        TextView tv = (TextView) view.findViewById(R.id.tv);
        Typeface t = Typeface.createFromAsset(activity.getAssets(),"LobsterTwo-Regular.ttf");
        tv.setTypeface(t);

        iv.setImageResource(imgs[0]);
        tv.setText(titles[position]);
        view.setTag(titles[position]);
        return view;
    }
}
