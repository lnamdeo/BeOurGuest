package com.lalit.readbarcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class nonet extends AppCompatActivity {
private Button btn;
    int status;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_nonet);
btn = (Button)findViewById(R.id.btn);
        sp=getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor=sp.edit();
        status = sp.getInt("status",0);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       if(netcheck.isInternetAvailable(nonet.this)) //returns true if internet available
                                       {
                                        if(status==0)
                                        {
                                            finish();
                                            startActivity(new Intent(nonet.this,signup.class));
                                        }
                                        else
                                            if(status==1)
                                            {
                                                editor.putString("order"," ");
                                                editor.commit();
                                                finish();
                                                startActivity(new Intent(nonet.this,Main2Activity.class));
                                            }
                                       }
                                       else
                                       {
                                           Toast.makeText(nonet.this,"Internet connection not available", Toast.LENGTH_LONG).show();
                                       }
                                   }
                               }
        );
    }

    public void onBackPressed()
    {
        AlertDialog.Builder ab = new AlertDialog.Builder(nonet.this);
        ab.setMessage("Are you sure you want to exit ???").setCancelable(false);
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        AlertDialog a = ab.create();
        a.show();
        //finish();
        //  Intent i = new Intent(loginlogup.this,Main2Activity.class);
        // startActivity(i);

    }

}
