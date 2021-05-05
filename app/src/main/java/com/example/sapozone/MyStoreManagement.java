package com.example.sapozone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

public class MyStoreManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store_management);
    }

    public void getTotalInfoShop(View view) {
    System.out.println("acces a l'affichage du shop");
        RequestQueue queue = Volley.newRequestQueue(this);
        Intent intent = new Intent(this,EditShopActivity.class);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = 0;
        id = sharedPref.getInt("idUser",id);
        String url = "https://api-sapozone.herokuapp.com/storeowner/"+id;
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Display the first 500 characters of the response string.
                        System.out.println("Response is: "+ response);


                        try {
                            System.out.println("retour de l'api");

                            JSONObject jsonObject = new JSONObject(response);

                            String idstore= jsonObject.getString("id");
                            intent.putExtra("storeId", idstore);
                            startActivity(intent);


                        }catch (JSONException err){
                            System.out.println("ERREEEE");
                            Log.d("Error", err.toString());
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void managePrestation(View view) {

        Intent intent = new Intent(this,ManageMyPrestationActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void manageDevis(View view) {

        Intent intent = new Intent(this,QuotationManageActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void manageCommande(View view) {

        Intent intent = new Intent(this,QuotationManageActivity.class);
        // Start the activity
        startActivity(intent);
    }

}