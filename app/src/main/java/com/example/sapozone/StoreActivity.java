package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.widget.EditText;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.HashMap;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

    }

    public void createStore(View view)
    {
        // Create an intent
        Intent intent = new Intent(this, MenuActivity.class);

        // Get the informations
        EditText store_nameET = findViewById(R.id.name_store);
        EditText store_street_nameET = findViewById(R.id.store_street_name);
        EditText store_postal_codeET = findViewById(R.id.store_postale_code);
        EditText store_cityET = findViewById(R.id.store_city);
        EditText store_bioET = findViewById(R.id.store_bio);
        EditText store_phoneNumberET = findViewById(R.id.store_phoneNumber);
        EditText store_streetNumberET = findViewById(R.id.store_streetNumber);

        // Extract the store information
        String store_name = store_nameET.getText().toString();
        String store_street_name = store_street_nameET.getText().toString();
        String store_postal_code = store_postal_codeET.getText().toString();
        String store_city = store_cityET.getText().toString();
        String store_bio = store_bioET.getText().toString();
        int store_phoneNumber = Integer.parseInt(store_phoneNumberET.getText().toString());
        int store_streetNumber = Integer.parseInt(store_streetNumberET.getText().toString());

        if(store_name.trim().equals("")) {
            store_nameET.setError("Name is required");
            store_nameET.setHint("Please enter your shop's name");
        }
        else
        {
            //save Store info
            HashMap<String,Object> strore = new HashMap<String,Object>();
            strore.put("name",store_name);
            strore.put("street_name",store_street_name);
            strore.put("postal_code",store_postal_code);
            strore.put("city",store_city);
            strore.put("bio",store_bio);
            strore.put("phone_number",store_phoneNumber);
            strore.put("street_number",store_streetNumber);
            SharedPreferences sharedPref = sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            JSONObject jsonObject_insert = new JSONObject();
            try {
                jsonObject_insert.put("name", store_name);
                jsonObject_insert.put("owner", sharedPref.getInt("idUser",0));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestQueue queue = Volley.newRequestQueue(this);
            AndroidNetworking.post("https://api-sapozone.herokuapp.com/stores/")
                    .addJSONObjectBody(jsonObject_insert)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener(){


                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println("retour api ok");
                            try {
                                String id = response.getString("id");
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put("streetname",store_street_name);
                                jsonObject.put("postal_code",store_postal_code);
                                jsonObject.put("city",store_city);
                                jsonObject.put("bio",store_bio);
                                jsonObject.put("phone_number",store_phoneNumber);
                                jsonObject.put("street_number",store_streetNumber);


                                String url = "https://api-sapozone.herokuapp.com/stores/"+id;


                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                        (Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                System.out.println(response.toString());
                                                startActivity(intent);
                                            }
                                        }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                // TODO: Handle error

                                                System.out.println(error.getMessage());
                                                System.out.println("erreur lors de l'update");

                                            }
                                        });

                                queue.add(jsonObjectRequest);
                            }
                            catch (JSONException e){
                                System.out.println(e.getMessage());
                            }


                        }

                        @Override
                        public void onError(ANError error) {
                            store_nameET.setError( "SOmething went wrong " );
                            System.out.println( error.getMessage() );
                            System.out.println( error.getErrorCode() );
                            System.out.println( error.getCause() );
                            System.out.println("erreur lors de l'insertion");
                        }
                    });
        }


    }
    public void updatestore(View view,int id){
        RequestQueue queue = Volley.newRequestQueue(this);
        EditText store_nameET = findViewById(R.id.name_store);
        EditText store_street_nameET = findViewById(R.id.store_street_name);
        EditText store_postal_codeET = findViewById(R.id.store_postale_code);
        EditText store_cityET = findViewById(R.id.store_city);
        EditText store_bioET = findViewById(R.id.store_bio);
        EditText store_phoneNumberET = findViewById(R.id.store_phoneNumber);
        EditText store_streetNumberET = findViewById(R.id.store_streetNumber);
        Intent intent = new Intent(this, MenuActivity.class);

        // Extract the store information
        String store_name = store_nameET.getText().toString();
        String store_street_name = store_street_nameET.getText().toString();
        String store_postal_code = store_postal_codeET.getText().toString();
        String store_city = store_cityET.getText().toString();
        String store_bio = store_bioET.getText().toString();
        int store_phoneNumber = Integer.parseInt(store_phoneNumberET.getText().toString());
        int store_streetNumber = Integer.parseInt(store_streetNumberET.getText().toString());
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("streetname",store_street_name);
            jsonObject.put("postal_code",store_postal_code);
            jsonObject.put("city",store_city);
            jsonObject.put("bio",store_bio);
            jsonObject.put("phone_number",store_phoneNumber);
            jsonObject.put("street_number",store_streetNumber);


            String url = "https://api-sapozone.herokuapp.com/stores/"+id;


            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            System.out.println(response.toString());
                           startActivity(intent);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                            System.out.println(error.getMessage());
                            System.out.println("erreur lors de l'update");

                        }
                    });

            queue.add(jsonObjectRequest);
        }
        catch (JSONException e){
            System.out.println(e.getMessage());
        }
    }
}