package com.example.arfoodview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.SharedPreferences;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends AppCompatActivity {

    Switch switch1;
    FirebaseFirestore db;
    Button edit;
    ImageButton button;
    ListView listView;
    ArrayList<String> userAllergies;
    ArrayAdapter<String > adapter;
    public static boolean switchState1;


    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String restaurant = getIntent().getStringExtra("Rest_name");

        preferences = getSharedPreferences("PREFS", 0);
        switchState1 = preferences.getBoolean("switch1", false);

        //switch1 = findViewById(R.id.AllegiesSwitch);
        edit = findViewById(R.id.b_edit);
        button = findViewById( R.id.Button );
        listView = (ListView) findViewById(R.id.listView);
        String TAG = "firebase_menu_window";


        db = FirebaseFirestore.getInstance();

        String restPath = "restaurants/" + restaurant + "/food";

        // this is done to get the allergens
        DocumentReference docAllergyref = db.collection("users").document("temporary");
        docAllergyref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    userAllergies =(ArrayList<String>) document.get( "allergies" );
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        userAllergies = (ArrayList<String>) document.get("allergies");
                        Log.d(TAG, "DocumentSnapshot data: " + document.get("allergies"));
                        adapter = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_list_item_1,userAllergies);
                       listView.setAdapter(adapter);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }

            }

        });
    /*    String restPath = "restaurants/" + restaurant + "/food";

        db.collection(restPath).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent( QuerySnapshot documentSnapshots,  FirebaseFirestoreException e) {

                if(e != null) {
                    Log.d(TAG, "error: " + e.getMessage());
                }

                for(DocumentSnapshot doc: documentSnapshots) {
                    String itemName = doc.getId();
                    System.out.println( "itemName: "+itemName );
                    userAllergies.add(itemName);
                }
                adapter = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_list_item_1,userAllergies);
                listView.setAdapter(adapter);

            }
        });
        */




//        switch1.setChecked(switchState1);
//
//        switch1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                switchState1 = !switchState1;
//                switch1.setChecked(switchState1);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putBoolean("switch1", switchState1);
//                editor.apply();
//
//
//            }
//        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            // When user settings start button do this
            public void onClick(View view) {
                Log.d("VisiFood", "Clicked Settings Button");
                Intent intent = new Intent(SettingsActivity.this, helpActivity.class);
                startActivity(intent);
                //Intent intent2 = new Intent(MainActivity.this, AllergyActivity.class);

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AllergyActivity.class);
                startActivity(intent);


            }
        });

    }
    public static void Count(){
        // this is just a test for Git commit
        // this is ron's test... change if need be.
    }
}
