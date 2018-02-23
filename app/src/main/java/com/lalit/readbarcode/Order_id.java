package com.lalit.readbarcode;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

public class Order_id extends AppCompatActivity {
TextView tv1,tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_id);
        tv1 = (TextView)findViewById(R.id.id_tv);
        Typeface t = Typeface.createFromAsset(getAssets(),"Pacifico-Regular.ttf");
        tv1.setTypeface(t);

    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(Order_id.this, Main2Activity.class));

    }
}
