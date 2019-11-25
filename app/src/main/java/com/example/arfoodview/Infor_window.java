package com.example.arfoodview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Infor_window extends AppCompatActivity{
   private  FirebaseFirestore db;
   final String TAG = "firebaselog";
    private Button showData;
    String itemChosen = "applePie";// menu.getItem();
    String foodname = itemChosen;
   // String cal, sugar, protein, sodium,transfat,fat,carbs;
    //textViewa
    TextView fName;
    TextView sugarData;
    TextView proteinData;
    TextView sodiumData;
    TextView transFatData;
    TextView fatData;
    TextView carbsData;
    TextView calories;
    TextView ings;
    ListView ingredients;
    ArrayList<String>  ing = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

       // ingredients = (ListView)findViewById(R.id.AllDisplay);
//        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//        ingredients.setAdapter(arrayAdapter);
        //texting
        String[] test;
        test = new String[]{"apple", "cinnamont"};


        DocumentReference docRef = db.collection("restaurants/SunnyWay/food").document(itemChosen);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    List<String> ing = (List<String>) document.get("ingredients");
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Log.d(TAG, "DocumentSnapshot data: " + document.get("calories"));
                        fName.setText(itemChosen);
                        calories.setText(document.getString("calories"));
                        sugarData.setText(document.getString("sugar"));
                        proteinData.setText(document.getString("protein"));
                        sodiumData.setText(document.getString("sodium"));
                        transFatData.setText(document.getString("transFat"));
                        fatData.setText(document.getString("fat"));
                        carbsData.setText(document.getString("carbs"));
                       // ingredients.setAdapter(arrayAdapter);
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