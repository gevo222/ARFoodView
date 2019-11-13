package com.example.arfoodview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    //This is for requesting permissions
    private static final String[] RUNTIME_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.CAMERA
    };

    int runtime_request = 1; //used in .requestPermissions()   for error message
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataBaseHelper myDB = new DataBaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (hasPermissions(this, RUNTIME_PERMISSIONS)) {
            Log.d("food", "Runtime permissions already granted");

        } else {
            Log.d("food", "Runtime permissions not granted");
            ActivityCompat.requestPermissions(this, RUNTIME_PERMISSIONS, runtime_request);
        }

        final ImageButton settingsButton = findViewById(R.id.settingsButton);
        final ImageButton cameraButton = findViewById(R.id.cameraButton);
        final ImageButton helpButton = findViewById(R.id.helpButton);

        // initiate the database
       // myDB = new DataBaseHelper(this);

        //Settings button   MainActivity -> Settings
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // When user settings start button do this
            public void onClick(View view) {
                Log.d("RoadRageKiller", "Clicked Settings Button");
                Intent intent = new Intent(MainActivity.this, Infor_window.class);
                startActivity(intent);
                //Intent intent2 = new Intent(MainActivity.this, AllergyActivity.class);

            }
        });

        //camera button
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // When user clicks start button do this
            public void onClick(View view) {
                Log.d("RoadRageKiller", "Clicked Camera Button");
                Intent intent = new Intent(MainActivity.this, Ar.class);
                startActivity(intent);
            }
        });

        //Help button
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // When user clicks start button do this
            public void onClick(View view) {
                Log.d("RoadRageKiller", "Clicked Help Button");
                Intent intent = new Intent(MainActivity.this, helpActivity.class);
                startActivity(intent);
            }
        });

    }//end of onCreate

    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        } else {
            Log.d("food", "OS Build Version Not Met");
        }
        return true;
    }
public static void Count(){
        // this is just a test for Git commit
    // this is ron's test... change if need be.
}
}
