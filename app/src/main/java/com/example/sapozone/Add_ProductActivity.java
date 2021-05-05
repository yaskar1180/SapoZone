package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

public class Add_ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

    }

    public void validateAddPrestation(View view) {
        System.out.println("acces a l'affichage du shop");
        RequestQueue queue = Volley.newRequestQueue(this);
        Intent intent = new Intent(this,MyStoreManagement.class);
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
                            String idstore = jsonObject.getString("id");
                            EditText delaiTE = findViewById(R.id.myprestation_delais);
                            EditText priceTE = findViewById(R.id.myprestation_price);
                            EditText descriptionTE = findViewById(R.id.description_ajout_artcle);


                            System.out.println("acces a l'jout de prestation");

                            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            int id = 0;
                            id = sharedPref.getInt("idUser",id);
                            String url = "https://api-sapozone.herokuapp.com/requests/";
                            System.out.println(url);



                            JSONObject requestbody = new JSONObject();
                            try {
                                requestbody.put("detail",descriptionTE.getText() );
                                requestbody.put("price",priceTE.getText() );
                                requestbody.put("lead", delaiTE.getText());
                                requestbody.put("customer", String.valueOf(id));
                                requestbody.put("store", idstore);
                                System.out.println("request body : "+ requestbody.toString());
                            }
                            catch (JSONException e){
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
                                            System.out.println(error.getMessage());
                                            System.out.println(error.getCause());
                                        }
                                    });
                            // Add the request to the RequestQueue.
                            queue.add(jsonObjectRequest);



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



}
