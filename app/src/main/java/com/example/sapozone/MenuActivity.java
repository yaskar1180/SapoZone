package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sapozone.adapters.ShopAdapter;
import com.example.sapozone.data.shop.Shop;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    private SharedPreferences sharedPref = null;


    private ListView displayedShops;



    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_menu);


        this.displayedShops = findViewById(R.id.displayedShops);


        List<Shop> shops = new ArrayList<Shop>();

        Shop s1 = new Shop(1, "Test Store",92000);
        Shop s2 = new Shop(2, "backet Store",75001);
        Shop s3 = new Shop(3, "bag Store",94000);
        Shop s4 = new Shop(4, "custizi Store",91000);
        Shop s5 = new Shop(1, "EREN Store",92000);
        Shop s6 = new Shop(2, "carrefour Store",75015);
        Shop s7 = new Shop(3, "Kozi Store",94420);
        Shop s8 = new Shop(4, "Nike Store",95100);
        Shop s11 = new Shop(1, "Adidas Store",92000);
        Shop s21 = new Shop(2, "Pron Store",75001);
        Shop s31 = new Shop(3, "nairobi Store",94000);
        Shop s41 = new Shop(4, "Test Store",91000);

        shops.add(s1);
        shops.add(s2);
        shops.add(s3);
        shops.add(s4);
        shops.add(s11);
        shops.add(s21);
        shops.add(s31);
        shops.add(s41);
        shops.add(s5);
        shops.add(s6);
        shops.add(s7);
        shops.add(s8);




        getAllShops(shops);


        ShopAdapter adapter = new ShopAdapter(MenuActivity.this, shops);

        displayedShops.setAdapter(adapter);
        System.out.println("ON A PAS ATTENDUUUUUUUUUu");

        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.searchFloatingButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "On va ouvrir un pop up de recherche avancée", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_movie, R.id.nav_renewal,
                R.id.nav_subscription, R.id.nav_consumable, R.id.nav_ticket)
                .setDrawerLayout(drawer)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void disconnect(View view){
        Intent intent = new Intent(this, MainActivity.class);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        startActivity(intent);
    }

    public boolean onStores(MenuItem item) {

        Intent intent = new Intent(this, StoreActivity.class);
        // Start the activity
        startActivity(intent);
        return true;

    }


    private  void getAllShops( List<Shop> shops ){



        String url = "https://api-sapozone.herokuapp.com/stores/";


        Shop s1 = new Shop(1, "Test",1);
        shops.add(s1);
        System.out.println(shops);

    }




}

