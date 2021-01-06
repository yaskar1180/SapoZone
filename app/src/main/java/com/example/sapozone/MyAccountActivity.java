package com.example.sapozone;

import android.content.Intent;
import android.os.Bundle;

import com.example.sapozone.data.users.Account;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAccountActivity extends AppCompatActivity {
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.db = new Database(this);

        ArrayList<Account> accounts = (ArrayList<Account>)db.getAllRows(Database.ACCOUNT_TABLE);

        TextView usernameTV = findViewById(R.id.profile_username);
        TextView emailTV = findViewById(R.id.profile_email);
        TextView firstnameTV = findViewById(R.id.profile_firstname);
        TextView lastnameTV = findViewById(R.id.profile_lastname);
        TextView phoneTV = findViewById(R.id.profile_phone);
        TextView bioTV = findViewById(R.id.profile_bio);


        if(accounts.size()!=0) {
            System.out.println(" account found");
         Account user = accounts.get(0);
        System.out.println(user.getEmail());

         usernameTV.setText(user.getUsername());
         emailTV.setText(user.getEmail());
         firstnameTV.setText(user.getFirstname());
         phoneTV.setText(user.getPhonenumber());
         lastnameTV.setText(user.getLastname());
         bioTV.setText(user.getBio());
        }
        else System.out.println("no account found");



    }
    public void edit(View view){
        Intent intent = new Intent(this, EditMyAccountActivity.class);
        startActivity(intent);

    }
    public void address(View view){
        Intent intent = new Intent(this, MyAddressActivity.class);
        startActivity(intent);

    }





}