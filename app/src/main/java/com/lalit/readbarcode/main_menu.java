package com.lalit.readbarcode;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class main_menu extends AppCompatActivity {
private TextView name;
SharedPreferences sp;
FirebaseDatabase database = FirebaseDatabase.getInstance();
DatabaseReference mref;
    SharedPreferences.Editor editor;
    GridView gvCategory;
    int[] imgs = {R.drawable.food};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu2);
        sp=getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
editor = sp.edit();
        if(!netcheck.isInternetAvailable(main_menu.this)) //returns true if internet available
        {

            finish();
            Intent i = new Intent(main_menu.this,nonet.class);
            startActivity(i);
        }
//Firebase.setAndroidContext(this);
        name = (TextView)findViewById(R.id.namet);
        Typeface t = Typeface.createFromAsset(getAssets(),"Pacifico-Regular.ttf");
        name.setTypeface(t);
        String str = sp.getString("res_name"," ");
        if(str.equals(" "))
        {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
        name.setText(str);
        mref = database.getReference(str+"/menu/major_categories");
        gvCategory = (GridView) findViewById(R.id.gv_category);

        final ProgressDialog progressDialog = new ProgressDialog(main_menu.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();

        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                String str[] = value.split("_acb@#@xzy_");
                GridAdapter ad = new GridAdapter(main_menu.this, imgs, str);
                gvCategory.setAdapter(ad);
                progressDialog.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = view.getTag().toString();
                Intent i = new Intent(getApplicationContext(), sub_menu.class);
                i.putExtra("category", title);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder ab = new AlertDialog.Builder(main_menu.this);
        ab.setMessage("Are you sure you want to exit menu page ?\nAll of your cart items will be deleted").setCancelable(false);
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                editor.putString("order"," ");
                editor.putString("res_name"," ");
                editor.putString("table_no"," ");
                editor.commit();
                Intent i = new Intent(main_menu.this,Main2Activity.class);
                startActivity(i);
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

    }

}
