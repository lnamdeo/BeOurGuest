package com.lalit.readbarcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mref;
    private EditText inputEmail, inputPassword,name,pno;
    private Button btnSignUp;
    TextView tv;
    private String email,password,name_s,pno_s;
    private int t=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        tv = (TextView)findViewById(R.id.textView2);
        Typeface t = Typeface.createFromAsset(getAssets(),"Pacifico-Regular.ttf");
        tv.setTypeface(t);
        if(!netcheck.isInternetAvailable(signup.this)) //returns true if internet available
        {

            finish();
            Intent i = new Intent(signup.this,nonet.class);
            startActivity(i);
        }
//        Firebase.setAndroidContext(this);


        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        //      username = (EditText) findViewById(R.id.username);
        name = (EditText)findViewById(R.id.name);
        pno = (EditText)findViewById(R.id.contact);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_s= name.getText().toString().trim();
                pno_s=pno.getText().toString().trim();
                email = inputEmail.getText().toString().trim();
//                userx = username.getText().toString().trim();
                if (TextUtils.isEmpty(name_s)) {
                    name.setError("Enter name");
                    name.requestFocus();
                    /*
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();*/
                    return;
                }

                if (TextUtils.isEmpty(pno_s)) {
                    pno.setError("Enter Contact number");
                    pno.requestFocus();
                    /*
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();*/
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    inputEmail.setError("Enter email address");
                    inputEmail.requestFocus();
                    /*
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();*/
                    return;
                }


                if (pno_s.length() != 10 && (!pno_s.matches("[0-9]+"))) {
                    pno.setText("");
                    pno.setError("Invalid Contact number");
                    pno.requestFocus();
                    return;
                }
                pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    editor = pref.edit();
                                    editor.putInt("status",1);
                                    editor.putString("user",email);
                                    editor.putString("name",name_s);
                                    editor.putString("pno",pno_s);
                                    editor.commit();
                                  Toast.makeText(signup.this,"User information stored successfully", Toast.LENGTH_SHORT).show();
                mref = database.getReference("/user");
                mref.push().setValue(name_s+"_acb@#@xzy_"+pno_s+"_acb@#@xzy_"+email);
                finish();
                startActivity(new Intent(signup.this,Main2Activity.class));
                                }

                        });

            }


    public void onBackPressed()
    {
        AlertDialog.Builder ab = new AlertDialog.Builder(signup.this);
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

