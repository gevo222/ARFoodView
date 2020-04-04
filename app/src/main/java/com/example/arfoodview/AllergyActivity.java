package com.example.arfoodview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllergyActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayList<String> allergies;
    ArrayAdapter<String > adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth;
    String TAG = "firebaseallergies";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy);


        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.lv1);
        fAuth = FirebaseAuth.getInstance();

        list = new ArrayList<>();
        allergies = new ArrayList<>();
        list.add("Milk");
        list.add("Peanuts");
        list.add("Shellfish");
        list.add("Egg");
        list.add("Fish");
        list.add("Nut");
        list.add("Soybean");
        list.add("Wheat");
        list.add("School");  //lol
        list.add("i dunno");


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        String user_id = fAuth.getCurrentUser().getUid();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Userss").child(user_id).child("allergies");

        allergies.clear();
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String value = dataSnapshot.getValue(String.class);
                allergies.add(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       // allergies = current_user_db.;



/*        // update allergies from database
//        DocumentReference docRef = db.collection("users").document("temporary");
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                       // allergies = (ArrayList<String>) document.get("allergies");
//                        Log.d(TAG, "DocumentSnapshot data: " + document.get("allergies"));
//                        Log.d(TAG, ""+allergies.contains("Peanuts"));
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });*/
        


        //searching
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(AllergyActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                  adapter.getFilter().filter(newText);
                return false;
            }
        });


        //color change and adding/removing clicked from allergies list
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = ((TextView)view).getText().toString();

                if (allergies.contains(item)) {
                    allergies.remove(item);

                    ((TextView) view).setTextColor(Color.RED);
                    ((TextView) view).setAllCaps(false);
                    Toast.makeText(getBaseContext(), item+ " removed", Toast.LENGTH_SHORT).show();
                }
                else{
                    allergies.add(item);
                    ((TextView) view).setTextColor(Color.rgb(0,128,0));
                    ((TextView) view).setAllCaps(true);
                    Toast.makeText(getBaseContext(), item+ " added", Toast.LENGTH_SHORT).show();
                }

                Log.d("hi",""+allergies);
                // TODO: 11/6/2019  
                /*if (SettingsActivity.switchState1) {
                    intent.putExtra("mylist", allergies);
                }*/
            }
        });

    }

    @Override
    public void onBackPressed() { // want it to update the allergen list with the current allergies array
        super.onBackPressed();


        //DocumentReference docRef = db.collection("users").document("temporary").;
        /*DocumentReference docRef = db.document("users/temporary/allergies");
        docRef.set(allergies);*/

        String user_id = fAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Userss").child(user_id).child("allergies");
        current_user_db.setValue(allergies);
        Intent intent = new Intent(AllergyActivity.this, SettingsActivity.class);
        startActivity(intent);
       // DocumentReference docRef = db.collection("users").document("temporary");
        //docRef.update("allergies" , allergies);



    }

}
