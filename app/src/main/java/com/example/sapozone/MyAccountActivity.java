package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.sapozone.data.users.Account;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAccountActivity extends AppCompatActivity {
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = new Database(this);
        setContentView(R.layout.activity_my_account);

        ArrayList<Account> accounts = (ArrayList<Account>) db.getAllRows(Database.ACCOUNT_TABLE);
        TextView usernameTV = findViewById(R.id.profile_username);
        TextView emailTV = findViewById(R.id.profile_email);
        TextView firstnameTV = findViewById(R.id.profile_firstname);
        TextView lastnameTV = findViewById(R.id.profile_lastname);
        TextView phoneTV = findViewById(R.id.profile_phone);
        TextView bioTV = findViewById(R.id.profile_bio);
        ImageView pp_display = findViewById(R.id.profile_picture);

        if(accounts.size() != 0) {
            System.out.println(" account found");
            Account user = accounts.get(0);
            System.out.println(user.getEmail());

            usernameTV.setText(user.getUsername());
            emailTV.setText(user.getEmail());
            firstnameTV.setText(user.getFirstname());
            phoneTV.setText(user.getPhonenumber());
            lastnameTV.setText(user.getLastname());
            bioTV.setText(user.getBio());
        } else {
            System.out.println("no account found");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }

        System.out.println("Requete de recuperation de l'image");
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mImageURLString = "https://api-sapozone.herokuapp.com/"+sharedPref.getString("pp","");
        System.out.println("Requete de recuperation de l'image");
        ImageRequest imageRequest = new ImageRequest(
            mImageURLString, // Image URL
            new Response.Listener<Bitmap>() { // Bitmap listener
                @Override
                public void onResponse(Bitmap response) {
                    System.out.println("retour de l'image");
                    // Do something with response
                    pp_display.setImageBitmap(response);
                    /*
                    // Save this downloaded bitmap to internal storage
                    Uri uri = saveImageToInternalStorage(response);*/

                }
            },
            500, // Image width
            500, // Image height
            ImageView.ScaleType.CENTER_CROP, // Image scale type
            Bitmap.Config.RGB_565, // Image decode configuration
            new Response.ErrorListener() { // Error listener
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("erreur lors du chargement de l'image");
                    // Do something with error response
                    error.printStackTrace();
                    // Snackbar.make(mCLayout,"Error",Snackbar.LENGTH_LONG).show();
                }
            }
        );

        // Add ImageRequest to the RequestQueue
        requestQueue.add(imageRequest);
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