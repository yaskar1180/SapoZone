package com.example.sapozone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.androidnetworking.AndroidNetworking;

public class Ajout_articleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_article);

    }

    public void validateAddPrestation(View view) {

        Intent intent = new Intent(this,MenuActivity.class);
        // Start the activity
        startActivity(intent);
    }

}
