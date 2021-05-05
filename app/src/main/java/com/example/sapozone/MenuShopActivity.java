package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.AndroidNetworking;
import com.example.sapozone.data.shop.Shop;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MenuShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_shop);

        // TODO: Requete pour savoir si y'a déjà une boutique
        if(false) {
            Intent intent = new Intent(this,StoreActivity.class);
            // Start the activity
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MyStoreManagement.class);
            startActivity(intent);
        }
    }

    public void ajoutShop(View view) {
        Intent intent = new Intent(this,StoreActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void getTotalInfoShop(View view) {
        Intent intent = new Intent(this, MyStoreManagement.class);
        startActivity(intent);
    }

    public void manageShop(View view) {
        Intent intent = new Intent(this, MyStoreManagement.class);
        startActivity(intent);
    }
}