package com.example.sapozone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArraySet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sapozone.data.users.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditMyAccountActivity extends AppCompatActivity {
    Database db;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.db = new Database(this);

        ArrayList<Account> accounts = (ArrayList<Account>)db.getAllRows(Database.ACCOUNT_TABLE);

        EditText usernameTE = findViewById(R.id.edit_username);
        EditText emailTE = findViewById(R.id.edit_email);
        EditText phoneTE = findViewById(R.id.edit_phone);
        EditText bioTE = findViewById(R.id.edit_bio);


        if(accounts.size()!=0) {
            System.out.println(" account found edit");
            Account user = accounts.get(0);
            System.out.println(user.getEmail());

            usernameTE.setText(user.getUsername());
            emailTE.setText(user.getEmail());
            phoneTE.setText(user.getPhonenumber());
            bioTE.setText(user.getBio());
        }
        else System.out.println("no account found");

        Button update_pp = findViewById(R.id.Update_pp);
        update_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("changement de photo de profile");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,21);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==21 && resultCode ==RESULT_OK && data!=null){
            Uri path = data.getData();
            System.out.println(path);
