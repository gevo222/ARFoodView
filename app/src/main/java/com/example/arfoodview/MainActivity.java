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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1;

    //public static String sel_rest = "";

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

    // Arraylist
    ArrayList<String> restaurant = new ArrayList<>();
    SpinnerDialog spinnerDialog;
    Button btnShow;

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
        final String TAG = "firebaselog";
        final ImageButton settingsButton = findViewById(R.id.settingsButton);
        final ImageButton cameraButton = findViewById(R.id.cameraButton);
        final ImageButton helpButton = findViewById(R.id.helpButton);

        // initiate the database
       // myDB = new DataBaseHelper(this);
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
        });


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



        // Restaurant Array
        initRestaurant();
        spinnerDialog = new SpinnerDialog(MainActivity.this,restaurant,"Select Restaurant");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String rest, int position) {

                Toast.makeText(MainActivity.this, "Selected: "+rest, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Menu_Window.class);
                intent.putExtra("Rest_name", rest);
                startActivity(intent);
                //sel_rest = rest;
                /*if(rest.equals("SunnyWay")) {
                    Intent intent = new Intent(MainActivity.this, restActivity.class);
                    startActivity(intent);
                }*/
            }
        });




        btnShow = (Button)findViewById(R.id.btnShow);
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
             public void onClick(View v) {
                spinnerDialog.showSpinerDialog();
                //Intent intent = new Intent(MainActivity.this, restActivity.class);
                //startActivity(intent);
            }
        });

    }//end of onCreate

    private void initRestaurant() {
        restaurant.add("Sushi-Ron");
        restaurant.add("Gevo's Gravy!");
        restaurant.add("Sunny Side Up");
        restaurant.add("CinnaRon!");
        restaurant.add("Munchin Mo'Nuts");
        restaurant.add("SunnyWay");
        restaurant.add("Give Me Mo!");
        restaurant.add("Gevordo's Pizzeria");
        restaurant.add("Sunny's Philadelphia Cheesecakes");
        restaurant.add("Mo' than Waffles");
        //for(int i = 0; i < 100; i++) {
        //   restaurant.add("Restaurant "+(i+1));
        //}
    }

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
