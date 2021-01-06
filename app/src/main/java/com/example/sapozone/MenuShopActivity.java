package com.example.sapozone;

import android.content.Intent;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

public class MenuShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_shop);


    }

    public void ajoutShop(View view) {

        Intent intent = new Intent(this,StoreActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void getTotalInfoShop(View view) {

        Intent intent = new Intent(this,MenuActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void manageShop(View view) {
        Intent intent = new Intent(this, MyStoreManagement.class);
        startActivity(intent);
    }


}