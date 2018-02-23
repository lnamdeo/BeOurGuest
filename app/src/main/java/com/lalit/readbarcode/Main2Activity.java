package com.lalit.readbarcode;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class Main2Activity extends AppCompatActivity {
    private SurfaceView cameraView;
    private TextView hellotv,user_btn,barcodeInfo;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    public String s;
    int l=0;
    SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private Button proceed;
    String arr[];
    private String email,tempp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);
        if(!netcheck.isInternetAvailable(Main2Activity.this)) //returns true if internet available
        {

            finish();
            Intent i = new Intent(Main2Activity.this,nonet.class);
            startActivity(i);
        }
        proceed=(Button)findViewById(R.id.proceed);
        hellotv=(TextView)findViewById(R.id.hellotv);
        sp=getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = sp.edit();
        String name=sp.getString("name"," ");
            hellotv.setText("Hello, "+name+".");
        cameraView = (SurfaceView) findViewById(R.id.camera_view);
        barcodeInfo = (TextView) findViewById(R.id.code_info);
        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(480, 640)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(Main2Activity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        AlertDialog.Builder ab = new AlertDialog.Builder(Main2Activity.this);
                        ab.setMessage("In order to scan QR code camera is needed \nPlease allow this app to access camera").setCancelable(false);
                        ab.setTitle("Camera permission not granted");
                        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                startActivity(new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                        Uri.fromParts("package", getPackageName(), null)));
                                }
                        });
                        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);

                            }

                        });

                        AlertDialog a = ab.create();
                        a.show();
                        return;
                    }
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException ie) {
                    Log.e("CAMERA SOURCE", ie.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                if (barcodes.size() != 0) {
                   barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                        public void run() {
                          /*  barcodeInfo.setText(    // Update the TextView
                                    barcodes.valueAt(0).displayValue
                            */

                            s =barcodes.valueAt(0).displayValue.toString();
                            arr=s.split("_acb@#@xzy_");
                            proceed.setText("Welcome to "+arr[0]);
                            proceed.setVisibility(View.VISIBLE);
                            proceed.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    editor.putString("res_name",arr[0]);
                                    editor.putString("table_no",arr[1]);
                                    editor.putString("order", " ");
                                    editor.commit();
                                    finish();
                                    Intent i  = new Intent(Main2Activity.this,main_menu.class);
                                    startActivity(i);}

                            });

                        }
                    });


            }
        }});
    }
    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder ab = new AlertDialog.Builder(Main2Activity.this);
        ab.setMessage("Are you sure you want to exit ???").setCancelable(false);
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                editor.putString("order"," ");
                editor.putString("res_name"," ");
                editor.putString("table_no"," ");
                editor.commit();
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
