package com.example.sapozone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestPrestationActivity extends AppCompatActivity {
    String storeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_prestation);
        Bundle extras = getIntent().getExtras();
        storeId = extras.getString("storeId");
    }

    public  void validateRequest(View view) {
        Intent intent= new Intent(this,MenuActivity.class);
        RequestQueue queue = Volley.newRequestQueue(this);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = 0;
        id = sharedPref.getInt("idUser", id);
        String url = "https://api-sapozone.herokuapp.com/requests/";
        System.out.println(url);
        EditText descriptionTE = findViewById(R.id.requestdetails);

        JSONObject requestbody = new JSONObject();
        try {
            requestbody.put("detail", descriptionTE.getText());
            requestbody.put("customer", String.valueOf(id));
            requestbody.put("store", this.storeId);
            System.out.println("request body : " + requestbody.toString());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestbody, new Response.Listener<JSONObject>() {

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
                        System.out.println(error.getCause());

                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);


    }
}