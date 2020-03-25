package com.example.arfoodview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText mEmailText, mPasswordText;
    Button mRLoginButton, mCreateButton;
    FirebaseAuth fAuth;
    //Todo: Add progress Bar again
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button RegisterBtn = findViewById(R.id.registerBtn);
        Button LoginBtn = findViewById(R.id.loginButton);

        //gevo
        mEmailText = findViewById(R.id.emailText);
        mPasswordText = findViewById(R.id.passwordText);
        mRLoginButton = findViewById(R.id.loginButton);
        mCreateButton = findViewById(R.id.registerBtn);
        fAuth = FirebaseAuth.getInstance();
        //Todo progressBar = something

        //end



        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            // When user settings start button do this
            public void onClick(View view) {

                // SKIPPING LOGIN FOR NOW
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                String email = mEmailText.getText().toString().trim();
                String password = mPasswordText.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmailText.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPasswordText.setError("Password is Required");
                    return;
                }

                if(password.length()<6) {
                    mPasswordText.setError("Password must be 6 or more Characters");
                    return;
                }
                //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //startActivity(intent);

                //authenticate the user
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(LoginActivity.this,"Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            // set progress bar to gone 2/4 28:00
                        }

                    }
                });
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
