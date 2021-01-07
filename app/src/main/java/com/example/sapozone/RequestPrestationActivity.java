package com.example.sapozone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.androidnetworking.AndroidNetworking;

public class RequestPrestationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_prestation);
    }

    public  void validateRequest(View view)

    {
        Intent intent = new Intent(this,MenuActivity.class);
        // Start the activity
        startActivity(intent);
    }
}