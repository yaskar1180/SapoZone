package com.example.sapozone;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.example.sapozone.data.users.Account;


public class Database extends SQLiteOpenHelper {

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_xNAME = "sapoZoneDB";



    //Table name
    public static final String ACCOUNT_TABLE = "ACCOUNT";


    public Database(Context context) {

        super(context, "DATABASE_NAME", null, DATABASE_VERSION);
        System.out.println("REUSSI");
    }

    // Create the tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_ACCOUNT_TABLE = "CREATE TABLE ACCOUNT(username TEXT PRIMARY KEY,id TEXT, password TEXT, email TEXT,firstname TEXT,lastname Text,streetnumber TEXT, streetname TEXT, postalcode TEXT, city TEXT, phonenumber TEXT, bio TEXT)";

        db.execSQL(CREATE_ACCOUNT_TABLE);

    }

    // Upgrade the database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS TICKET " );

        // Create tables again
        //onCreate(db);
    }

    // Add a new row
    public void addRow(String tableName, HashMap elements) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        Map<Object, Object> map = elements;

        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            if (entry.getValue() instanceof String) {
                values.put((String) entry.getKey(), (String) entry.getValue());

            } else if (entry.getValue() instanceof Float) {
                values.put((String) entry.getKey(), (Float) entry.getValue());

            } else if (entry.getValue() instanceof Integer) {
                values.put((String) entry.getKey(), (Integer) entry.getValue());

            }

        }


        db.insert(tableName, null, values);
        db.close();

    }




    // Get all rows
    public List getAllRows(String table) {
        List l = new ArrayList<>();

        // Select all query
        String selectQuery = "SELECT * FROM " + table;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all the rows and addi the to the list
        if (cursor.moveToFirst()) {
            if(table.equalsIgnoreCase(ACCOUNT_TABLE)){
                do {


                    //(username TEXT PRIMARY KEY,id TEXT, password TEXT, email TEXT,firstname TEXT,lastname Text,streetnumber TEXT, streetname TEXT, postalcode TEXT, city TEXT, phonenumber TEXT, bio TEXT)
                    //    public Account(String username,String id, String password, String email, String phonenumber,  String firstname, String lastname, String streetname,String streetnumber, String postal_code, String city, String bio ) {
                    Account account = new Account(cursor.getString(0),cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(10),cursor.getString(4),cursor.getString(5),cursor.getString(7),cursor.getString(6),cursor.getString(8),cursor.getString(9),cursor.getString(11));

                    // Add row to list
                    l.add(account);
                } while (cursor.moveToNext());
            }
        }

        cursor.close();
        db.close();

        // Return the list
        return l;
    }

    // Get all rows
    public String getLastId(String table) {

        // Select all query
        String selectQuery = "SELECT * FROM " + table;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String lastId = "0";
        // Loop through all the rows and addi the to the list
        if (cursor.moveToFirst()) {
            do {
                lastId = cursor.getString(0);
                // Add row to list
            } while (cursor.moveToNext());

        }



        cursor.close();
        db.close();

        // Return the list
        return lastId;
    }




    public String updateLastId(String id){
        int n = Integer.parseInt(id);
        n++;
        return String.valueOf(n);
    }
    // Clear the table
    public void clearTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table);
        db.close();
    }

}