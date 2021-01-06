package com.example.sapozone;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Ajout_articleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_article);
        String storeId = getIntent().getStringExtra("storeId");
        System.out.println(storeId + " Est dans la palce");
    }

}
