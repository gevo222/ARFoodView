package com.example.arfoodview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Switch switch1;
    boolean switchState1;
    public static boolean allergies = true;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences("PREFS", 0);
        switchState1 = preferences.getBoolean("switch1", false);

        switch1 = (Switch) findViewById(R.id.switch1);

        switch1.setChecked(switchState1);

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchState1 = !switchState1;
                switch1.setChecked(switchState1);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("switch1", switchState1);
                editor.apply();
                allergies = !allergies;
            }
        });
    }
}
