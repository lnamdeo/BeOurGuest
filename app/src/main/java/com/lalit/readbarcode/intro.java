package com.lalit.readbarcode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class intro extends AppCompatActivity {
    private ImageView iv;
    private SharedPreferences sp;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);
        sp = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        status= sp.getInt("status",0);
        iv = (ImageView)findViewById(R.id.iv);
        Animation a = AnimationUtils.loadAnimation(this,R.anim.my_scale);
        iv.startAnimation(a);
        Handler mh = new Handler();
        mh.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(status==1) {
                    startActivity(new Intent(intro.this, Main2Activity.class));
                }
                else
                {
                    startActivity(new Intent(intro.this, signup.class));
                }
                //setContentView(R.layout.activity_first2);
                finish();

            }
        },2000);

    }
}
