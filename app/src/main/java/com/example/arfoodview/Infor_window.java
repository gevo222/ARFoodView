package com.example.arfoodview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Infor_window extends AppCompatActivity{
   private  FirebaseFirestore db;
   final String TAG = "firebaselog";
    TextView fName;
    TextView allDisplay;
    TextView sugarData;
    TextView proteinData;
    TextView sodiumData;
    TextView transFatData;
    TextView fatData;
    TextView carbsData;
    TextView calories;
    TextView ings;
    ListView ingredients;
    List<String> userAllergies; // user given by users
    List<String> allergentCross; // user given by users

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String imageChosen = "salmon";
        String restChosen = getIntent().getStringExtra("restName");
        String itemChosen = getIntent().getStringExtra("itemChosen");
        userAllergies = new ArrayList<>(  );
        // populating the textViews
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_infor_window);
        fName = (TextView)findViewById(R.id.displayFoodName);
        calories = (TextView)findViewById(R.id.calDisplay);
        sugarData = (TextView)findViewById(R.id.sugarDisplay);
        proteinData = (TextView)findViewById(R.id.proteinDisplay);
        sodiumData = (TextView)findViewById(R.id.NaDisplay);
        transFatData = (TextView)findViewById(R.id.taDisplay);
        fatData = (TextView)findViewById(R.id.fatDisplay);
        carbsData = (TextView)findViewById(R.id.carbsDisplay);
        ings = (TextView)findViewById(R.id.textArea);
        allDisplay = (TextView)findViewById( R.id.allergendisplay );
        // end of populating the textViews

        String restPath = "restaurants/" + restChosen + "/food";

        // this is done to get the allergens
        DocumentReference docAllergyref = db.collection("users").document("temporary");
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
                     for(int i = 0; i < userAllergies.size(); i++)
                         for(int j = 0; j < allergenData.size(); j++){
                             if(userAllergies.get( i ).equals(allergenData.get( j ))){
                                 allergentCross.add(userAllergies.get( i ));
                             }
                         }
                     Log.d( TAG,"Cross data is: "+ allergentCross );
                        if(!allergentCross.isEmpty()){
                            allDisplay.setBackgroundColor( Color.RED);
                            allDisplay.setTypeface( Typeface.DEFAULT_BOLD);
                            for(String str : allergentCross){
                                allDisplay.append( "\n"+str );
                            }
                        }else{
                            allDisplay.setText("No Allergens. ");
                        }

                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG, "DocumentSnapshot data: " + document.get("calories"));
                        Log.d(TAG, "DocumentSnapshot data: " + document.get("allergens"));
                        fName.setText(itemChosen);
                        calories.setText(document.getString("calories"));
                        sugarData.setText(document.getString("sugar"));
                        proteinData.setText(document.getString("protein"));
                        sodiumData.setText(document.getString("sodium"));
                        transFatData.setText(document.getString("transFat"));
                        fatData.setText(document.getString("fat"));
                        carbsData.setText(document.getString("carbs"));

                        // choosing the right 2D images
                        ImageView img = (ImageView)findViewById( R.id.imageview_2 );
                        if (imageChosen.equals("food")){
                            img.setImageResource( R.drawable.food );
                        } else if (imageChosen.equals("salmon")) {
                            img.setImageResource(R.drawable.salmon);
                        }else{
                            System.out.println( "image not found!" );
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

        // Ar camera button
        final ImageButton arButton = findViewById(R.id.ArButton);
        arButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // When user settings start button do this
            public void onClick(View view) {
                Log.d("RoadRageKiller", "Clicked Settings Button");
                Intent intent = new Intent(Infor_window.this, Ar.class);
                startActivity(intent);
                //Intent intent2 = new Intent(MainActivity.this, AllergyActivity.class);

            }
        });// end of Ar camera button

        // a\help button
        final Button helpButton = findViewById(R.id.HelpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            // When user settings start button do this
            public void onClick(View view) {
                Log.d("RoadRageKiller", "Clicked Settings Button");
                Intent intent = new Intent(Infor_window.this, helpActivity.class);
                startActivity(intent);
                //Intent intent2 = new Intent(MainActivity.this, AllergyActivity.class);

            }
        });
    }
}

// int calories, sugar, protein, sodium, transfar, fat, carbs;
// String ingri;

/*
        db.collection("Restaurants").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if(!queryDocumentSnapshots.isEmpty()){
                    List<DocumentSnapshot> restaurants = queryDocumentSnapshots.getDocuments();
                    for(DocumentSnapshot d : restaurants){
                    }
                }
            }
        });
*/

// attemp #34
/*
*  //components
        final String[] name = {null};
        //DbComponents
        final TextView[] f_name = new TextView[1];
        final String TAG = "firebaselog";
       ArrayList<DocumentSnapshot> ingridiens = new ArrayList<DocumentSnapshot>();
        DocumentReference docRef = db.collection("restaurants/SunnyWay/food").document("applePie");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // this is for the logcat... internal useage
                       Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG, "DocumentSnapshot data: " + document.get("ingresients"));
                        for(DocumentSnapshot d: ingridiens){
                            //ingridiens.add(d.getData().get("ingredients"));
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/