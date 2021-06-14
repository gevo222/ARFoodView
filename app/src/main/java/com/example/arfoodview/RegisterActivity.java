package com.example.arfoodview;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    //EditText mUsernameText
    EditText mEmailText, mPasswordText;
    Button mRegisterButton, mSignInButton;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //mUsernameText = findViewById(R.id.usernameText);
        mEmailText = findViewById(R.id.emailText);
        mPasswordText = findViewById(R.id.passwordText);
        mRegisterButton = findViewById(R.id.registerButton);
        mSignInButton = findViewById(R.id.sign_in_Btn);

        fAuth = FirebaseAuth.getInstance();

        mSignInButton.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);

        });

        mRegisterButton.setOnClickListener(view -> {
            String email = mEmailText.getText().toString().trim();
            String password = mPasswordText.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                mEmailText.setError("Email is Required");
                return;
            }

            if (TextUtils.isEmpty(password)) {
                mPasswordText.setError("Password is Required");
                return;
            }

            if (password.length() < 6) {
                mPasswordText.setError("Password must be 6 or more Characters");
                return;
            }

            //register user in firebase

            fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "Error!" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });
    }
}
