package com.example.arfoodview;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.SpinnerDialog;


public class MainActivity extends AppCompatActivity {


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
    static String restChosen = "";
    //public static String sel_rest = "";
    int runtime_request = 1; //used in .requestPermissions()   for error message
    // Arraylist
    ArrayList<String> restaurant = new ArrayList<>();
    SpinnerDialog spinnerDialog;
    Button btnShow;

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

    // Navigation Bar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Navigation Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.search_icon:
                    Toast.makeText(MainActivity.this, "Search", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.profile_icon:
                    Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, profileActivity.class);
                    startActivity(intent);
                    break;
                case R.id.settings_icon:
                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                    intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });


        if (hasPermissions(this, RUNTIME_PERMISSIONS)) {
            Log.d("food", "Runtime permissions already granted");

        } else {
            Log.d("food", "Runtime permissions not granted");
            ActivityCompat.requestPermissions(this, RUNTIME_PERMISSIONS, runtime_request);
        }
        //final ImageButton cameraButton = findViewById(R.id.cameraButton);
        final ImageButton helpButton = findViewById(R.id.helpButton);

        // initiate the database
        // myDB = new DataBaseHelper(this);
        /*
        FirebaseFirestore db = FirebaseFirestore.getInstance();

                DocumentReference docRef = db.collection("restaurants/SunnyWay/food").document("pancakes");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG, "DocumentSnapshot data: " + document.get("calories"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/

        //Help button
        // When user clicks start button do this
        helpButton.setOnClickListener(view -> {
            Log.d("VisiFood", "Clicked Help Button");
            Intent intent = new Intent(MainActivity.this, helpActivity.class);
            startActivity(intent);
        });

        // Restaurant Array
        initRestaurant();
        spinnerDialog = new SpinnerDialog(MainActivity.this, restaurant, "Select Restaurant");
        spinnerDialog.bindOnSpinerListener((rest, position) -> {

            Toast.makeText(MainActivity.this, "Selected: " + rest, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, Menu_Window.class);
            //intent.putExtra("Rest_name", rest);
            restChosen = rest;
            startActivity(intent);
            //sel_rest = rest;
            /*if(rest.equals("SunnyWay")) {
                Intent intent = new Intent(MainActivity.this, restActivity.class);
                startActivity(intent);
            }*/
        });


        btnShow = (Button) findViewById(R.id.btnShow);
        btnShow.setOnClickListener(v -> {
            spinnerDialog.showSpinerDialog();
            //Intent intent = new Intent(MainActivity.this, restActivity.class);
            //startActivity(intent);
        });

        // to hide android's nav bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }//end of onCreate

    // to hide android's nav bar
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private void initRestaurant() {

        String TAG = "sunny";

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("restaurants").addSnapshotListener((documentSnapshots, e) -> {

            if (e != null) {
                Log.d(TAG, "error: " + e.getMessage());
            }

            if (documentSnapshots != null) {
                for (DocumentSnapshot doc : documentSnapshots) {
                    String restName = doc.getId();
                    restaurant.add(restName);
                }
            }
        });

    }
}
