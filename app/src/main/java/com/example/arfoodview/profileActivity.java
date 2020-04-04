package com.example.arfoodview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class profileActivity extends AppCompatActivity {

    Button mLogoutButton;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);


        mLogoutButton = findViewById(R.id.logoutButton);
        fAuth = FirebaseAuth.getInstance();

        // if user isn't signed in, go to login
        if(fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }


        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                fAuth.signOut();
                Intent intent = new Intent(profileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


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
                        finish();
                        break;
                    case R.id.settings_icon:
                        Toast.makeText(profileActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        intent = new Intent(profileActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
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
}
