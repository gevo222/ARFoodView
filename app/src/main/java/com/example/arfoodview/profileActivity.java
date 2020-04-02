package com.example.arfoodview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class profileActivity extends AppCompatActivity {


    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        fAuth = FirebaseAuth.getInstance();
/*
        if(fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
*/

        // Navigation Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.profile_icon:
                        Toast.makeText(profileActivity.this, "Profile", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.search_icon:
                        Toast.makeText(profileActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        intent = new Intent(profileActivity.this, MainActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.settings_icon:
                        Toast.makeText(profileActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        intent = new Intent(profileActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });


    }
}
