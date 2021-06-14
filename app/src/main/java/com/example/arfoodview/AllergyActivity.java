package com.example.arfoodview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class AllergyActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayList<String> allergies;
    ArrayAdapter<String> adapter;
    FirebaseAuth fAuth;

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


        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        String user_id = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
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

        //searching
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (list.contains(query)) {
                    adapter.getFilter().filter(query);
                } else {
                    Toast.makeText(AllergyActivity.this, "No Match found", Toast.LENGTH_LONG).show();
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
        listView.setOnItemClickListener((parent, view, position, id) -> {

            String item = ((TextView) view).getText().toString();

            if (allergies.contains(item)) {
                allergies.remove(item);

                ((TextView) view).setTextColor(Color.RED);
                ((TextView) view).setAllCaps(false);
                Toast.makeText(getBaseContext(), item + " removed", Toast.LENGTH_SHORT).show();
            } else {
                allergies.add(item);
                ((TextView) view).setTextColor(Color.rgb(0, 128, 0));
                ((TextView) view).setAllCaps(true);
                Toast.makeText(getBaseContext(), item + " added", Toast.LENGTH_SHORT).show();
            }

            Log.d("hi", "" + allergies);
        });

    }

    @Override
    public void onBackPressed() { // want it to update the allergen list with the current allergies array
        super.onBackPressed();

        String user_id = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Userss").child(user_id).child("allergies");
        current_user_db.setValue(allergies);
        Intent intent = new Intent(AllergyActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

}
