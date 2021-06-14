package com.example.arfoodview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;


public class SettingsActivity extends AppCompatActivity {

    public static boolean switchState1;
    //FirebaseFirestore db;
    Button edit;
    ImageButton button;
    ListView listView;
    ArrayList<String> userAllergies;
    ArrayAdapter<String> adapter;
    FirebaseAuth fAuth;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        fAuth = FirebaseAuth.getInstance();

        preferences = getSharedPreferences("PREFS", 0);
        switchState1 = preferences.getBoolean("switch1", false);

        edit = findViewById(R.id.b_edit);
        button = findViewById(R.id.Button);
        listView = (ListView) findViewById(R.id.listView);
        userAllergies = new ArrayList<>();

        String user_id = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Userss").child(user_id).child("allergies");
        adapter = new ArrayAdapter<>(SettingsActivity.this, android.R.layout.simple_list_item_1, userAllergies);
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

        // Navigation Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
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
        });

        // When user settings start button do this
        button.setOnClickListener(view -> {
            Log.d("VisiFood", "Clicked Settings Button");
            Intent intent = new Intent(SettingsActivity.this, helpActivity.class);
            startActivity(intent);
            //Intent intent2 = new Intent(MainActivity.this, AllergyActivity.class);

        });

        edit.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsActivity.this, AllergyActivity.class);
            startActivity(intent);
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
}
