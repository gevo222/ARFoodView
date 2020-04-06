package com.example.arfoodview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.SharedPreferences;

import android.util.Log;
import android.view.MenuItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class SettingsActivity extends AppCompatActivity {

    Switch switch1;
    //FirebaseFirestore db;
    Button edit;
    ImageButton button;
    ListView listView;
    ArrayList<String> userAllergies;
    ArrayAdapter<String > adapter;
    FirebaseAuth fAuth;
    public static boolean switchState1;


    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        fAuth = FirebaseAuth.getInstance();

        preferences = getSharedPreferences("PREFS", 0);
        switchState1 = preferences.getBoolean("switch1", false);



        //switch1 = findViewById(R.id.AllegiesSwitch);
        edit = findViewById(R.id.b_edit);
        button = findViewById( R.id.Button );
        listView = (ListView) findViewById(R.id.listView);
        String TAG = "firebase_menu_window";
        userAllergies = new ArrayList<>();

        //db = FirebaseFirestore.getInstance();

        String user_id = fAuth.getCurrentUser().getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Userss").child(user_id).child("allergies");
        adapter = new ArrayAdapter<String>(SettingsActivity.this, android.R.layout.simple_list_item_1,userAllergies);
        listView.setAdapter(adapter);


        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                userAllergies.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                userAllergies.remove(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       /* String restPath = "restaurants/" + restaurant + "/food";

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

        });*/

        // Navigation Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.settings_icon:
                        Toast.makeText(SettingsActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.profile_icon:
                        Toast.makeText(SettingsActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        intent = new Intent(SettingsActivity.this, profileActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case R.id.search_icon:
                        Toast.makeText(SettingsActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        intent = new Intent(SettingsActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
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

        // to hide android's nav bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    } // end of onCreate

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

    public static void Count(){
        // this is just a test for Git commit
        // this is ron's test... change if need be.
    }
}
