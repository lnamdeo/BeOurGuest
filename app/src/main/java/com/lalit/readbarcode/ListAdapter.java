package com.lalit.readbarcode;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

    Activity activity;
    String[] titles;
    public ListAdapter(Activity activity, String[] titles) {
        this.activity = activity;
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
        View view = l.inflate(R.layout.list_type, null);
        TextView title = (TextView) view.findViewById(R.id.title);
        Typeface t = Typeface.createFromAsset(activity.getAssets(),"LobsterTwo-Regular.ttf");
        title.setTypeface(t);
        TextView type = (TextView) view.findViewById(R.id.type);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView tot = (TextView)view.findViewById(R.id.tot);
        String x[]=titles[position].split("_acb@#@xzy_");
        title.setText(x[0]);
        type.setText(x[1]);
        if(x[1].equals("Veg"))
        {
            type.setTextColor(Color.GREEN);
        }
        else
            if(x[1].equals("Non-Veg"))
        {
            type.setTextColor(Color.RED);
        }
        else
            if(x[1].equals("Contain Egg"))
            {
            type.setTextColor(Color.rgb(255,215,0));
            }
        if(x[1].equals("Cold"))
        {
            type.setTextColor(Color.BLUE);
        }
        else
        if(x[1].equals("Hot")) {
            type.setTextColor(Color.RED);
        }

            price.setText(x[2]);
        tot.setText(x[2]);
        //tv.setText(titles[position]);*/
        view.setTag(x[0]);
        return view;
    }
}
