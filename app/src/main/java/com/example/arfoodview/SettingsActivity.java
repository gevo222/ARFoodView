package com.example.arfoodview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.content.SharedPreferences;

import android.view.View;
import android.widget.Button;
import android.widget.Switch;


public class SettingsActivity extends AppCompatActivity {

    Switch switch1;
    Button edit;
    public static boolean switchState1;


    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences("PREFS", 0);
        switchState1 = preferences.getBoolean("switch1", false);

        switch1 = findViewById(R.id.AllegiesSwitch);
        edit = findViewById(R.id.b_edit);
        switch1.setChecked(switchState1);

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchState1 = !switchState1;
                switch1.setChecked(switchState1);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("switch1", switchState1);
                editor.apply();


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
