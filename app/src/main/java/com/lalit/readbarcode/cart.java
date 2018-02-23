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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class cart extends AppCompatActivity {
    private ListView lv;
    private Button btn ;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    List2Adapter ad;
    String f;
    String fff=" ";
    int hg=0;
    String item_name;
    String raw[];
    private int falg_f=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cart);
        Intent i = getIntent();
        item_name=i.getStringExtra("category");
        lv= (ListView)findViewById(R.id.cart_list);
        btn = (Button)findViewById(R.id.cart_btn);
        sp=getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        editor = sp.edit();
        f= " ";
        TextView ti = (TextView)findViewById(R.id.ti);
//        Typeface t = Typeface.createFromAsset(getAssets(),"Pacifico-Regular.ttf");
//        ti.setTypeface(t);
        if(!netcheck.isInternetAvailable(cart.this)) //returns true if internet available
        {

            finish();
            Intent j = new Intent(cart.this,nonet.class);
            startActivity(j);
        }
        final ProgressDialog progressDialog = new ProgressDialog(cart.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please Wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        String str = sp.getString("order", " ");
        raw = str.split("_acb@#@xzy_");
        int n = ((raw.length)/3);
        ArrayList<String> titles = new ArrayList();
        ArrayList<String> no = new ArrayList();
        ArrayList<String> price = new ArrayList();
        for(int h = 0; h < n; h++) {
            if (!(raw[(h * 3) + 1]).trim().equals("0")) {
                /*titles[h] = raw[(h * 3)];
                no[h] = raw[(h * 3) + 1];
                price[h] = raw[(h * 3) + 2];*/
                titles.add(raw[(h * 3)]);
                no.add(raw[(h * 3) + 1]);
                price.add(raw[(h * 3) + 2]);
            }
        }
         for(int y=0;y<(titles.size()-1);y++)
         {
             for(int r=(y+1);r<titles.size();r++)
             {
                 if(titles.get(y).toString().equals(titles.get(r).toString()))
                 {
                     int a = Integer.parseInt(no.get(y).toString());
                     int b = Integer.parseInt(no.get(r).toString());
                     int c=a+b;
                     titles.remove(r);
                     no.remove(r);
                     price.remove(r);
                     no.set(y, String.valueOf(c));
                 }
             }
         }

String raw2[]=new String[(titles.size()*3)];
        for(int g=0;g<titles.size();g++)
        {
            raw2[(g*3)]=titles.get(g).toString();
            raw2[(g*3)+1]=no.get(g).toString();
            raw2[(g*3)+2]=price.get(g).toString();
        }
        for(int r=0;r<raw2.length;r++)
        {
            if(f.equals(" "))
                f=raw2[r];
            else
                f=f+"_acb@#@xzy_"+raw2[r];
        }
        editor.putString("order",f);
        f= " ";
        editor.commit();

        ad = new List2Adapter(cart.this,  titles.toArray(new String[0]),no.toArray(new String[0]), price.toArray(new String[0]));
        lv.setAdapter(ad);
//Toast.makeText(sub_menu.this,value,Toast.LENGTH_LONG).show();
        progressDialog.dismiss();

        if(lv.getCount()==0) {
TextView tv = (TextView) findViewById(R.id.empty_tv);
            tv.setVisibility(View.VISIBLE);
            lv.setVisibility(View.GONE);
            Typeface typ = Typeface.createFromAsset(getAssets(),"LobsterTwo-Regular.ttf");
            tv.setTypeface(typ);
            btn.setVisibility(View.GONE);
        }
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                        final String title = view.getTag().toString();
                        view.findViewById(R.id.tv_hide).setVisibility(View.GONE);
                        view.findViewById(R.id.plus).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView tvQuantity = (TextView) view.findViewById(R.id.no);
                                TextView qtvQuantity = (TextView) view.findViewById(R.id.qno);
                                TextView totalpr = (TextView)view.findViewById(R.id.tot_price);
                                int quantity = Integer.parseInt(tvQuantity.getText().toString());
                                if(quantity < 10) {
                                    tvQuantity.setText(String.valueOf(++quantity));
                                    qtvQuantity.setText(String.valueOf(quantity));
                                    hg=quantity;
                                }
                                int tt= Integer.parseInt(qtvQuantity.getText().toString());
                                int ttt=tt * ad.getPrice(position);
                                totalpr.setText(String.valueOf(ttt));
                            }
                        });
                        view.findViewById(R.id.minus).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView tvQuantity = (TextView) view.findViewById(R.id.no);
                                TextView qtvQuantity = (TextView) view.findViewById(R.id.qno);
                                TextView totalpr = (TextView)view.findViewById(R.id.tot_price);
                                int quantity = Integer.parseInt(tvQuantity.getText().toString());
                                if(quantity >0) {
                                    tvQuantity.setText(String.valueOf(--quantity));
                                    qtvQuantity.setText(String.valueOf(quantity));
                                    hg=quantity;
                                }
                                int tt= Integer.parseInt(qtvQuantity.getText().toString());
                                int ttt=tt * ad.getPrice(position);
                                totalpr.setText(String.valueOf(ttt));
                            }
                        });

                        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView title_tv = (TextView)view.findViewById(R.id.title);
                                TextView q = (TextView)view.findViewById(R.id.qno);
                                String st= q.getText().toString();
                                String rr = title_tv.getText().toString();
                                if(!st.equals("0")) {
                                    Toast.makeText(cart.this, "You just edited your order\n" + rr + " Quantity :" + st, Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(cart.this, "You removed "+rr+" from your cart", Toast.LENGTH_SHORT).show();
                                }
                                for(int i=0;i<(raw.length-1);i=i+3)
                                {
                                    if(raw[i].equals(rr))
                                    {
                                        raw[i+1]=st;
                                        break;
                                    }
                                }

                                for(int i=0;i<raw.length;i++)
                                {
                                    if(f.equals(" "))
                                    f=raw[i];
                                    else
                                        f=f+"_acb@#@xzy_"+raw[i];
                                }
                                editor.putString("order",f);
                                f= " ";
                                editor.commit();
                                }
                        });


                    }
});
findViewById(R.id.add_more).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
        Intent i  = new Intent(cart.this,main_menu.class);
        startActivity(i);}

}
);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        for(int d=1;d<raw.length;d=d+3)
        {
            if(!raw[d].equals("0"))
            {
                falg_f=1;
            }
        }
        if(falg_f==0)
        {
            Toast.makeText(cart.this,"Please add quantity in any item to proceed", Toast.LENGTH_SHORT).show();
        }
        else
        {
            finish();
            Intent o = new Intent(cart.this,samapt.class);
            startActivity(o);
        }

            }
        });
    }

    @Override
    public void onBackPressed()
    {
        finish();
        Intent i = new Intent(cart.this,main_menu.class);
        startActivity(i);

    }

}