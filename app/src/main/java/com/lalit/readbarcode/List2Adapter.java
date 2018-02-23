package com.lalit.readbarcode;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class List2Adapter extends BaseAdapter {

    Activity activity;
    String[] titles;
    String[] quantity;
    String[] price;
    public List2Adapter(Activity activity, String[] titles, String[] quantity, String[] price) {
        this.activity = activity;
        this.titles = titles;
        this.quantity = quantity;
        this.price = price;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }
    public int getPrice(int position){
        return Integer.parseInt(price[position]);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater l = activity.getLayoutInflater();
        View view = l.inflate(R.layout.final_list, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView qno = (TextView) view.findViewById(R.id.qno);
        TextView no = (TextView) view.findViewById(R.id.no);
        TextView tot_price = (TextView) view.findViewById(R.id.tot_price);
        Typeface t = Typeface.createFromAsset(activity.getAssets(),"LobsterTwo-Regular.ttf");
        title.setTypeface(t);
        title.setText(titles[position]);
        qno.setText(quantity[position]);
        no.setText(quantity[position]);
        tot_price.setText(String.valueOf((Integer.parseInt(price[position])) * (Integer.parseInt(quantity[position]))));
        //tv.setText(titles[position]);*/
        view.setTag(titles[position]);
        return view;
    }
}
