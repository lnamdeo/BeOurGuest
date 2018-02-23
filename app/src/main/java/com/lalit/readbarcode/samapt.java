package com.lalit.readbarcode;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class samapt extends AppCompatActivity {
SharedPreferences sp;
String[] raw;
    TextView ti,name_tv,email_tv,pno_tv,tno_tv,toto;
    Button conti,done_btn,edit;
    ListView akhrilist;
    List3Adapter ad;
    String date, time;
    private String id;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_samapt);
        if(!netcheck.isInternetAvailable(samapt.this)) //returns true if internet available
        {

            finish();
            Intent i = new Intent(samapt.this,nonet.class);
            startActivity(i);
        }
//        Firebase.setAndroidContext(this);
        sp = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        ti=(TextView)findViewById(R.id.ti);
        name_tv=(TextView)findViewById(R.id.name_tv);
        pno_tv=(TextView)findViewById(R.id.pno_tv);
        email_tv=(TextView)findViewById(R.id.email_tv);
        tno_tv=(TextView)findViewById(R.id.tno_tv);
        toto=(TextView)findViewById(R.id.toto);
        conti =(Button)findViewById(R.id.conti);
        done_btn =(Button)findViewById(R.id.done_btn);
        edit =(Button)findViewById(R.id.edit);
        akhrilist=(ListView)findViewById(R.id.akhrilist);
        Typeface t = Typeface.createFromAsset(getAssets(),"Pacifico-Regular.ttf");
        ti.setTypeface(t);
        toto.setTypeface(t);
        name_tv.setText(sp.getString("name"," "));
        pno_tv.setText(sp.getString("pno"," "));
        email_tv.setText(sp.getString("user"," "));
        tno_tv.setText(sp.getString("table_no"," "));
        String str = sp.getString("order", " ");
        raw = str.split("_acb@#@xzy_");
        int n = ((raw.length)/3);
        ArrayList<String> titles = new ArrayList();
        ArrayList<String> no = new ArrayList();
        ArrayList<String> price = new ArrayList();
        for(int h = 0; h < n; h++)
        {
            if(!(raw[(h * 3) + 1]).trim().equals("0"))
            {
                titles.add(raw[(h * 3)]);
                no.add(raw[(h * 3) + 1]);
                price.add(raw[(h * 3)+2]);
            }

        }
        ad = new List3Adapter(samapt.this,  titles.toArray(new String[0]),no.toArray(new String[0]), price.toArray(new String[0]));
        akhrilist.setAdapter(ad);
//Toast.makeText(sub_menu.this,value,Toast.LENGTH_LONG).show();
        String arr2[]=no.toArray(new String[0]);
        String arr[] = price.toArray(new String[0]);
        int g=0;
        for(int p=0;p<arr.length;p++)
        {
            g=g+( Integer.parseInt(arr[p]) * Integer.parseInt(arr2[p]) );
        }
        toto.setText("Total Payable : Rs."+ String.valueOf(g));

        edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        finish();
        startActivity(new Intent(samapt.this,cart.class));
            }
        });
        conti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(samapt.this,main_menu.class));
            }
        });

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
           //

            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child(sp.getString("res_name"," ")).child("orders").child("available_id").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        id = dataSnapshot.child("available_id").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                time = new SimpleDateFormat("hh:mm:ss").format(Calendar.getInstance().getTime());
                String finally_str = date+"_xzy@#@acb_"+time+"_xzy@#@acb_"+sp.getString("name"," ")+"_xzy@#@acb_"+sp.getString("pno"," ")
                        +"_xzy@#@acb_"+sp.getString("user"," ")+"_xzy@#@acb_"
                        +sp.getString("table_no"," ")+"_xzy@#@acb_"+sp.getString("order"," ");
                mref = database.getReference(sp.getString("res_name"," ")+"/orders/");
                mref.push().setValue(finally_str);
                finish();
                Intent i = new Intent(samapt.this,Order_id.class);
                startActivity(i);
            }
        });

    }

    public void onBackPressed()
    {
        finish();
        Intent i = new Intent(samapt.this,cart.class);
        startActivity(i);

    }
}
