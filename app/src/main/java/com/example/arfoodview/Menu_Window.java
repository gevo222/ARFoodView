package com.example.arfoodview;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Menu_Window extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    ArrayList<String> dish;
    ArrayAdapter<String > adapter;

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
