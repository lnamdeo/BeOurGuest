package com.lalit.readbarcode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class sub_menu extends AppCompatActivity {
TextView tv;
    Button btn;
    ListView lv;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mref;
    ArrayList<String> mnotes = new ArrayList<>();
private View v;
    String f=" ";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String item_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sub_menu);
        tv= (TextView)findViewById(R.id.res_name);
        lv = (ListView)findViewById(R.id.lv_items);
        btn = (Button)findViewById(R.id.btn_cart);
        Intent i = getIntent();
        item_name=i.getStringExtra("category");
        sp=getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        if(!netcheck.isInternetAvailable(sub_menu.this)) //returns true if internet available
        {

            finish();
            Intent j = new Intent(sub_menu.this,nonet.class);
            startActivity(j);
        }
//        Firebase.setAndroidContext(this);
        Typeface t = Typeface.createFromAsset(getAssets(),"Pacifico-Regular.ttf");
        tv.setTypeface(t);
        String str = sp.getString("res_name"," ").trim();
        tv.setText(item_name);
        final ProgressDialog progressDialog = new ProgressDialog(sub_menu.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
//        mref= database.getReference("https://be-our-guest-host.firebaseio.com/\"+str+\"/menu/\"+item_name+\"/\"");
        mref= database.getReference("/"+str+"/menu/"+item_name);

        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                mnotes.add(value);
                String data[] = mnotes.toArray(new String[mnotes.size()]);
                com.lalit.readbarcode.ListAdapter ad = new com.lalit.readbarcode.ListAdapter(sub_menu.this, data);
                lv.setAdapter(ad);
//Toast.makeText(sub_menu.this,value,Toast.LENGTH_LONG).show();
                progressDialog.dismiss();

                lv.setOnItemClickListener(
                        new AdapterView.OnItemClickListener(){
                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                                v=view;
                                final String title = view.getTag().toString();
                                view.findViewById(R.id.tv_hide).setVisibility(View.GONE);
                                view.findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TextView tvQuantity = (TextView) view.findViewById(R.id.no);
                                        int quantity = Integer.parseInt(tvQuantity.getText().toString());
                                        if(quantity < 10)
                                            tvQuantity.setText(String.valueOf(++quantity));
                                        TextView tot=(TextView)view.findViewById(R.id.tot);
                                        TextView t=(TextView)view.findViewById(R.id.price);
                                        int tt= Integer.parseInt(t.getText().toString());
                                        int ttt=tt* Integer.parseInt(tvQuantity.getText().toString());
                                        tot.setText(String.valueOf(ttt));
                                    }
                                });
                                view.findViewById(R.id.minus).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TextView tvQuantity = (TextView) view.findViewById(R.id.no);
                                        int quantity = Integer.parseInt(tvQuantity.getText().toString());
                                        if(quantity > 1)
                                            tvQuantity.setText(String.valueOf(--quantity));
                                        TextView tot=(TextView)view.findViewById(R.id.tot);
                                        TextView t=(TextView)view.findViewById(R.id.price);
                                        int tt= Integer.parseInt(t.getText().toString());
                                        int ttt=tt* Integer.parseInt(tvQuantity.getText().toString());
                                        tot.setText(String.valueOf(ttt));
                                    }
                                });

                                view.findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        TextView q = (TextView)view.findViewById(R.id.no);
                                        TextView ty = (TextView)view.findViewById(R.id.price);
                                        String st= q.getText().toString();
                                        String hj =ty.getText().toString();
                                        Toast.makeText(sub_menu.this, "You just added\n" + title + " Quantity :" + st, Toast.LENGTH_SHORT).show();
                                        if (f.equals(" ")) {
                                            f = title + "_acb@#@xzy_" + st + "_acb@#@xzy_" + hj;
                                        } else {
                                            f = f + "_acb@#@xzy_" + title + "_acb@#@xzy_" + st + "_acb@#@xzy_" + hj;
                                        }

                                        String stt = sp.getString("order"," ");
                                        editor = sp.edit();
                                        if(stt.equals(" ")) {
                                            editor.putString("order", f);
                                        }
                                        else
                                        {
                                            String stri=stt+"_acb@#@xzy_"+f;
                                            editor.putString("order",stri);
                                        }
                                        editor.commit();
                                        f=" ";
                                        return;
                                    }
                                });
                            }
                        });
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

btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
        Intent i = new Intent(sub_menu.this,cart.class);
        i.putExtra("category",item_name);
        startActivity(i);
        ;
    }
});
    }

    @Override
    public void onBackPressed()
    {
        finish();
        Intent i = new Intent(sub_menu.this,main_menu.class);
        startActivity(i);

    }

}
