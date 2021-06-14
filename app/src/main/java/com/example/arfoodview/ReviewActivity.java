package com.example.arfoodview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ReviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review);

        // Navigation Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent;
            switch (item.getItemId()) {
                case R.id.ingredient_item:
                    Toast.makeText(ReviewActivity.this, "Reviews", Toast.LENGTH_SHORT).show();
                    Log.d("NavigationLog", "Clicked Reviews");
                    intent = new Intent(ReviewActivity.this, SeeIngredients.class);
                    startActivity(intent);
                    break;
                case R.id.AR_item:
                    Toast.makeText(ReviewActivity.this, "View AR", Toast.LENGTH_SHORT).show();
                    intent = new Intent(ReviewActivity.this, Ar.class);
                    startActivity(intent);
                    break;
                case R.id.nutrition_item:
                    Toast.makeText(ReviewActivity.this, "Nutrition Info", Toast.LENGTH_SHORT).show();
                    intent = new Intent(ReviewActivity.this, Infor_window.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            return true;
        });

    }


}
