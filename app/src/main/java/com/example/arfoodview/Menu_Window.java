package com.example.arfoodview;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Menu_Window extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayList<String> dish;
    ArrayAdapter<String > adapter;
//    // public onstructor
//    public Menu_Window(){
//
//        // this will bdo nothing.
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__window);

        String restaurant = getIntent().getStringExtra("Rest_name");

        TextView tView = (TextView) findViewById(R.id.RestName);
        tView.setText(restaurant);

        searchView = (SearchView) findViewById(R.id.searchView2);
        listView = (ListView) findViewById(R.id.dishes);

        dish = new ArrayList<>();

        String TAG = "firebase_menu_window";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String restPath = "restaurant/" + restaurant + "/food";
        //DocumentReference docs = db.collection(restPath).document(food);

        db.collection(restPath).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });


        /*
        db.collection(restPath).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e != null) {
                    Log.d(TAG, "error: " + e.getMessage());
                }

                for(DocumentSnapshot doc: documentSnapshots) {
                    String restName = doc.getId();
                    dish.add(restName);
                }
            }
        });*/

/*
        db.collection("restaurant").document(restaurant).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null) {
                    Log.d(TAG, "error: " + e.getMessage());
                }

                for(DocumentSnapshot doc: documentSnapshot) {
                    String restName = doc.getId();
                    dish.add(restName);
                }
            }
        })*/

        //String restPath = "restaurants/" + restaurant;
        //DocumentReference docRef =
        /*
        db.collection("restaurants").document(restaurant).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e != null) {
                    Log.d(TAG, "error: " + e.getMessage());
                }

                for(DocumentSnapshot doc: documentSnapshots) {
                    String restName = doc.getId();
                    dish.add(restName);
                }
            }
        });/*
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    List<String> ing = (List<String>) document.get("ingredients");
                    if (document.exists()) {


                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/



/*
        dish.add("Milk");
        dish.add("Peanuts");
        dish.add("Shellfish");
        dish.add("Egg");
        dish.add("Fish");
        dish.add("Nut");
        dish.add("Soybean");
        dish.add("Wheat");
        dish.add("School");  //lol
        dish.add("i dunno");
        dish.add("Soybean");
        dish.add("Wheat");
        dish.add("School");  //lol
        dish.add("i dunno"); */

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dish);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(dish.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(Menu_Window.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });







    }
}
