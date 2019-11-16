package com.example.arfoodview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AllergyActivity extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayList<String> list;
    ArrayList<String> allergies;
    ArrayAdapter<String > adapter;
    Intent intent = new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy);

        searchView = (SearchView) findViewById(R.id.searchView);
        listView = (ListView) findViewById(R.id.lv1);

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = ((TextView)view).getText().toString();

                Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                if (allergies.contains(item)) {
                    allergies.remove(item);

                    ((TextView) view).setTextColor(Color.BLACK);
                    ((TextView) view).setAllCaps(false);
                }
                else{
                    allergies.add(item);
                    ((TextView) view).setTextColor(Color.rgb(0,128,0));
                    ((TextView) view).setAllCaps(true);
                }

                Log.d("hi",""+allergies);
                // TODO: 11/6/2019  
                /*if (SettingsActivity.switchState1) {
                    intent.putExtra("mylist", allergies);
                }*/
            }
        });

    }
}
