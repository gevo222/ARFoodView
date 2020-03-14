package com.example.arfoodview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.FirebaseApp;

public class RegisterActivity extends AppCompatActivity {

    EditText mUsernameText, mEmailText, mPasswordText;
    Button mRegisterButton;
    FirebaseAuth fAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsernameText = findViewById(R.id.usernameText);
        mEmailText = findViewById(R.id.emailText);
        mPasswordText = findViewById(R.id.passwordText);
        mRegisterButton = findViewById(R.id.registerButton);

        fAuth = FirebaseAuth.getInstance();
        //progressBar = findViewById(R.id.progressBar);


        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

                //progressBar.setVisibility((View.VISIBLE));

                //register user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"Error!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
