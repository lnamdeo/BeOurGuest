package com.lalit.readbarcode;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class List3Adapter extends BaseAdapter {

    Activity activity;
    String[] titles;
    String[] quantity;
    String[] price;
    public List3Adapter(Activity activity, String[] titles, String[] quantity, String[] price) {
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
        View view = l.inflate(R.layout.last_list, null);
        TextView title = (TextView) view.findViewById(R.id.item_name);
        TextView qno = (TextView) view.findViewById(R.id.quantity);
        TextView tot_price = (TextView) view.findViewById(R.id.tota);
        title.setText(titles[position]);
        qno.setText(quantity[position]);
        tot_price.setText(String.valueOf((Integer.parseInt(price[position])) * (Integer.parseInt(quantity[position]))));
        //tv.setText(titles[position]);*/
        view.setTag(titles[position]);
        return view;
    }
}
