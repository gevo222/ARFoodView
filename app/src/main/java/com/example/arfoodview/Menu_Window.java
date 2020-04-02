package com.example.arfoodview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Menu_Window extends AppCompatActivity {
   static String model = "";
    FirebaseFirestore db;
    SearchView searchView;
    ListView listView;
    ArrayList<String> dish;
    ArrayAdapter<String > adapter;
    ArrayAdapter<String > arrayAdapter;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;


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
        String rName = restaurant + " Menu";
        tView.setText(rName);

        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.dishlist);

        dish = new ArrayList<>();

        String TAG = "firebase_menu_window";


         db = FirebaseFirestore.getInstance();
        String restPath = "restaurants/" + restaurant + "/food";

        db.collection(restPath).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent( QuerySnapshot documentSnapshots,  FirebaseFirestoreException e) {

                if(e != null) {
                    Log.d(TAG, "error: " + e.getMessage());
                }

                for(DocumentSnapshot doc: documentSnapshots) {
                    String itemName = doc.getId();
                    System.out.println( "itemName: "+itemName );
                    dish.add(itemName);
                }
                adapter = new ArrayAdapter<String>(Menu_Window.this, android.R.layout.simple_list_item_1,dish);
                listView.setAdapter(adapter);

            }
        });

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = ((TextView)view).getText().toString();
                Intent intent = new Intent(Menu_Window.this, Infor_window.class);
                intent.putExtra("restChosen", restaurant);
                intent.putExtra("itemChosen", item);
                startActivity(intent);
                //model = item;
                //System.out.println( "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" );
                //System.out.println( "Model chosen is: "+model );

            }
        });
    }
        //public String getModel(){
        //return model;
        //}
}
