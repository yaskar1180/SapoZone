package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.android.volley.toolbox.StringRequest;

import com.example.sapozone.adapters.ShopAdapter;
import com.example.sapozone.data.shop.Shop;
import com.example.sapozone.data.users.Account;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuActivity extends AppCompatActivity {

    private SharedPreferences sharedPref = null;
    List<Shop> shops = new ArrayList<Shop>();
    private ListView displayedShops;
    private com.example.sapozone.Database db = new com.example.sapozone.Database(this);

    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_menu);
        this.displayedShops = findViewById(R.id.displayedShops);
        String url = "https://api-sapozone.herokuapp.com/stores/";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println("Response is: "+ response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject test = jsonArray.getJSONObject(i);
                                Shop shop = new Shop(test.getInt("id"), test.getString("name"), test.getString("postal_code"));
                                shops.add(shop);
                                System.out.println(shop.getId());
                            }

                            ShopAdapter adapter = new ShopAdapter(MenuActivity.this, shops);
                            displayedShops.setAdapter(adapter);

                            displayedShops.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position,
                                                        long id) {
                                    Shop selectedShop = (Shop) displayedShops.getItemAtPosition(position);
                                    System.out.println(selectedShop.getId()+"EST CHOOSE");
                                    Intent intent = new Intent(MenuActivity.this, StoreDetails.class);
                                    String storeId = String.valueOf(selectedShop.getId());
                                    intent.putExtra("storeId", storeId);
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException err){
                            System.out.println("ERREEEE");
                            Log.d("Error", err.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });

        queue.add(stringRequest);
        System.out.println(this.shops);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.searchFloatingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "TODO: Recherche de boutiques", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        // check if logged
        this.db = new Database(this);
        ArrayList<Account> accounts = (ArrayList<Account>) db.getAllRows(Database.ACCOUNT_TABLE);
        if(accounts.size() <= 0) {
            Intent intentMain = new Intent(this, MainActivity.class);
            startActivity(intentMain);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void disconnect(View view){
        Intent intent = new Intent(this, MainActivity.class);
        SharedPreferences.Editor editor = sharedPref.edit().clear();
        editor.apply();
        db.clearTable("account");
        startActivity(intent);
    }

    public boolean onStores(MenuItem item) {

        Intent intent = new Intent(this, MenuShopActivity.class);
        // Start the activity
        startActivity(intent);
        return true;
    }

    public void myAccount(MenuItem item){
        Intent intent = new Intent(this, MyAccountActivity.class);
        startActivity(intent);
    }

    private  void getAllShops(){

        String url = "https://api-sapozone.herokuapp.com/stores/";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Display the first 500 characters of the response string.
                        System.out.println("Response is: "+ response);

                        try {
                            System.out.println("ON EST DEDANS");
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject test = jsonArray.getJSONObject(i);
                                Shop shop = new Shop(test.getInt("id"),test.getString("name"),"11" );
                                shops.add(shop);
                                System.out.println(shop.getId());
                            }
                        } catch (JSONException err) {
                            System.out.println("ERREEEE");
                            Log.d("Error", err.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });

        queue.add(stringRequest);
        System.out.println(this.shops);

    }
}