package com.example.arfoodview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button RegisterBtn = findViewById(R.id.registerBtn);
        Button LoginBtn = findViewById(R.id.loginButton);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            // When user settings start button do this
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });


        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            // When user settings start button do this
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });
    }

}
