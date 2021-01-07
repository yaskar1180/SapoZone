package com.example.sapozone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.androidnetworking.AndroidNetworking;

public class ManageMyPrestationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_my_prestation);
    }

    public void getMyPrestation(View view) {

        Intent intent = new Intent(this,MenuActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void addMyPrestation(View view) {

        Intent intent = new Intent(this, Add_ProductActivity.class);
        // Start the activity
        startActivity(intent);
    }
}