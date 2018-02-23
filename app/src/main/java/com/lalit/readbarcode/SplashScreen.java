package com.lalit.readbarcode;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    private ImageView iv;
    private SharedPreferences sp;
    int status;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        status= sp.getInt("status",0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(status==1) {
                    startActivity(new Intent(SplashScreen.this, Main2Activity.class));
                }
                else
                {
                    startActivity(new Intent(SplashScreen.this, signup.class));
                }
                finish();
            }
        }, 1000);

    }

}
