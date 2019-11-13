package com.example.arfoodview;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.SQLData;
import java.util.ArrayList;

/*this class creates the database*/
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Restaurants.db";
    public static final String TABLE_NAME = "Restaurant_Info";
    public static final String RESTAURANT__NAME = "Restaurant";//column 1
    public static final String FOOD_NAME = "Name";//column 2
    public static final String FOOD_DESCRIPTION = "Description";//column 3
    public static ArrayList INGRIDIENTS;//column 4
    public static ArrayList ALLERGENTS;//column 5

    public DataBaseHelper( Context context ) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" Create Table "+TABLE_NAME+"(Restaurant TEXT PRIMARY KEY AUTOINCREMENT,Food TEXT,Description TEXT,Ingridients ArrayList, Allergents ArrayList)");// takes string and passes it as a object
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
    onCreate(db);
    }
    public boolean insertData(String R_Name, String f_Name, String f_Des, ArrayList ingridients, ArrayList allergents){
        SQLiteDatabase db = this.getWritableDatabase(); // creates the data base and table this is just for checking.
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESTAURANT__NAME,R_Name);
        contentValues.put(FOOD_NAME,f_Name);
        contentValues.put(FOOD_DESCRIPTION,f_Des);
        //contentValues.put(INGRIDIENTS, ingridients);
        //contentValues.put(ALLERGENTS, allergents); does not woel becacsae of the data type

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1){
            return false;
        }else{
            return true;

        }
    }
}
