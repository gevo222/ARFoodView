package com.example.arfoodview;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SeeIngredients extends AppCompatActivity{
    private  FirebaseFirestore db;
    final String TAG = "firebaselog";
    TextView fName;
    TextView allDisplay;
    TextView ings;
    ListView ingredients;
    List<String> userAllergies; // user given by users
    List<String> allergentCross; // user given by users
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //String imageChosen = "salmon";
        String restChosen = Menu_Window.chosenRest; //getIntent().getStringExtra("restName");
        String itemChosen = Menu_Window.chosenItem; //getIntent().getStringExtra("itemChosen");
        userAllergies = new ArrayList<>();
        // populating the textViews
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.ingredients_list_item);
        fName = (TextView)findViewById(R.id.displayFoodName);
        ings = (TextView)findViewById(R.id.textArea);
        allDisplay = (TextView)findViewById( R.id.allergendisplay );
        // end of populating the textViews

        String restPath = "restaurants/" + restChosen + "/food";

        // new DB
        fAuth = FirebaseAuth.getInstance();
        String user_id = fAuth.getCurrentUser().getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Userss").child(user_id).child("allergies");

        // this is done to get the allergens
        /*DocumentReference docAllergyref = db.collection("users").document("temporary");
        docAllergyref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    userAllergies = (List<String>) document.get( "allergies" );
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        userAllergies = (ArrayList<String>) document.get("allergies");
                        Log.d(TAG, "DocumentSnapshot data: " + document.get("allergies"));
                        Log.d(TAG, ""+userAllergies.contains("Peanuts"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/


        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                userAllergies.add(value);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                userAllergies.remove(value);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DocumentReference docRef = db.collection(restPath).document(itemChosen);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    List<String> ing = (List<String>) document.get("ingredients");
                    //for allergens
                    List<String> allergenData = (List<String>) document.get( "allergens" );
                    allergentCross = new ArrayList(  );

                    /* THIS IS CRASHING PAGE/APP
                    for(int i = 0; i < userAllergies.size(); i++)
                        for(int j = 0; j < allergenData.size(); j++){
                            if(userAllergies.get( i ).equals(allergenData.get( j ))){
                                allergentCross.add(userAllergies.get( i ));
                            }
                        } */

                    Log.d( TAG,"Cross data is: "+ allergentCross );
                    if(!allergentCross.isEmpty()){
                        allDisplay.setTextColor(Color.parseColor("FF6400"));
                        allDisplay.append( "Allergens:" );
                        allDisplay.setTypeface( Typeface.DEFAULT_BOLD);
                        for(String str : allergentCross){
                            allDisplay.append( "\n"+str );
                        }
                    }else{
                        allDisplay.setText("No Allergens.");
                    }

                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG, "DocumentSnapshot data: " + document.get("calories"));
                        Log.d(TAG, "DocumentSnapshot dataAllergens: " + document.get("allergens"));
                        fName.setText(itemChosen);

                        // choosing the right 2D images
                        ImageView img = (ImageView)findViewById( R.id.imageview_2 );
                        switch (itemChosen) {
                            case "Ramen":
                                img.setImageResource(R.drawable.ramens);
                                break;
                            case "salmon":
                                img.setImageResource(R.drawable.salmon);
                                break;
                            case "applePie":
                                img.setImageResource(R.drawable.applepie);
                                break;
                            case "pancakes":
                                img.setImageResource(R.drawable.pancakes);
                                break;
                            case "Candy Bowl":
                                img.setImageResource(R.drawable.candybowl);
                                break;
                            case "Pizza":
                                img.setImageResource(R.drawable.pizza);
                                break;
                            case "Apple Strudel":
                                img.setImageResource(R.drawable.applestrudel);
                                break;
                            default:
                                System.out.println("image not found!");
                                break;
                        }
                        // end of choosing 2D images

                        for(String str : ing){
                            if(true) { // need to work on this
                                ings.append("\n" + str);
                            }else{
                                ings.append("\n" + str);
                            }
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        // help button
        final ImageButton helpButton = findViewById(R.id.HelpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // When user settings start button do this
            public void onClick(View view) {
                Log.d("VisiFood", "Clicked Settings Button");
                Intent intent = new Intent(SeeIngredients.this, helpActivity.class);
                startActivity(intent);
                //Intent intent2 = new Intent(MainActivity.this, AllergyActivity.class);

            }
        });

        // Navigation Bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.AR_item:
                        Toast.makeText(SeeIngredients.this, "View AR", Toast.LENGTH_SHORT).show();
                        intent = new Intent(SeeIngredients.this, Ar.class);
                        startActivity(intent);
                        break;
                    case R.id.nutrition_item:
                        Toast.makeText(SeeIngredients.this, "Nutrition Info", Toast.LENGTH_SHORT).show();
                        intent = new Intent(SeeIngredients.this, Infor_window.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        // to hide android's nav bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }// end of onCreate

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
