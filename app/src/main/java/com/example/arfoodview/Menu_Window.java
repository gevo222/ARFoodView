package com.example.arfoodview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Menu_Window extends AppCompatActivity {
    static String model = "";
    static String chosenRest = "";
    static String chosenItem = "";
    FirebaseFirestore db;
    SearchView searchView;
    ListView listView;
    ArrayList<String> dish;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__window);

        String restaurant = MainActivity.restChosen; //getIntent().getStringExtra("Rest_name");

        TextView tView = (TextView) findViewById(R.id.RestName);
        String rName = restaurant + " Menu";
        tView.setText(rName);

        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.dishlist);

        dish = new ArrayList<>();

        String TAG = "firebase_menu_window";


        db = FirebaseFirestore.getInstance();
        String restPath = "restaurants/" + restaurant + "/food";

        db.collection(restPath).addSnapshotListener((documentSnapshots, e) -> {

            if (e != null) {
                Log.d(TAG, "error: " + e.getMessage());
            }

            if (documentSnapshots != null) {
                for (DocumentSnapshot doc : documentSnapshots) {
                    String itemName = doc.getId();
                    System.out.println("itemName: " + itemName);
                    dish.add(itemName);
                }
            }
            adapter = new ArrayAdapter<>(Menu_Window.this, android.R.layout.simple_list_item_1, dish);
            listView.setAdapter(adapter);

        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (dish.contains(query)) {
                    adapter.getFilter().filter(query);
                } else {
                    Toast.makeText(Menu_Window.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {

            String item = ((TextView) view).getText().toString();
            Intent intent = new Intent(Menu_Window.this, Infor_window.class);
            chosenRest = restaurant;
            chosenItem = item;
            //intent.putExtra("restChosen", restaurant);
            intent.putExtra("itemChosen", item);
            startActivity(intent);
            model = item;
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            System.out.println("Model chosen is: " + model);

        });
    }
    //public String getModel(){
    //return model;
    //}
}
