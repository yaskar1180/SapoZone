package com.example.sapozone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.androidnetworking.AndroidNetworking;

public class MyStoreManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_management);
    }

    public void getTotalInfoShop(View view) {

        Intent intent = new Intent(this,MenuActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void managePrestation(View view) {

        Intent intent = new Intent(this,ManageMyPrestationActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void manageDevis(View view) {

        Intent intent = new Intent(this,MenuActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void manageCommande(View view) {

        Intent intent = new Intent(this,MenuActivity.class);
        // Start the activity
        startActivity(intent);
    }
}