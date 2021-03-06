package com.example.sapozone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sapozone.adapters.QuotationAdapter;
import com.example.sapozone.adapters.ShopAdapter;
import com.example.sapozone.data.shop.Quote;
import com.example.sapozone.data.shop.Shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.err;

public class StoreDetails extends AppCompatActivity {
    String storeId;


    List<Quote> quotes = new ArrayList<Quote>();

    private ListView displayedQuotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_store_details);
        this.displayedQuotes = findViewById(R.id.displayedPresta);
        Bundle extras = getIntent().getExtras();
        storeId = extras.getString("storeId");

        String urlStore = "https://api-sapozone.herokuapp.com/stores/"+storeId;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String urlQuote = "https://api-sapozone.herokuapp.com/storeproducts/"+storeId;
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, urlQuote,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response2) {

                        try {
                            System.out.println(response2 + "EST OKEYYY");
                            JSONArray jsonArray = new JSONArray(response2);
                            System.out.println(jsonArray.toString() + "EST OKEYYY");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject test = jsonArray.getJSONObject(i);

                                Quote quote = new Quote(test.getString("detail"), test.getInt("price"), test.getInt("lead"));

                                quotes.add(quote);
                                System.out.println(quote.getDetail()+"EN YYYYYYYYYYYY");
                                System.out.println(quotes+"EN ZZZZZZZZZZZZ");

                            }

                            System.out.println(quotes+"EN ZZZZZZZZZZZZ");
                            QuotationAdapter adapter = new QuotationAdapter(StoreDetails.this, quotes);

                            displayedQuotes.setAdapter(adapter);



                        }catch (JSONException err){
                            System.out.println("ERREEEE");
                            Log.d("Error", err.toString());
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage()+"EST PAS OKKK");
                System.out.println(error.getCause());

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest1);





        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlStore,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Display the first 500 characters of the response string.
                        System.out.println("Response is: "+ response);


                        try {

                            JSONObject test = new JSONObject(response);

                            System.out.println(test.getString("postal_code")+ "TEEEEEST");
                            Shop shop = new Shop(test.getInt("id"),test.getString("name"),test.getString("phone"),test.getString("postal_code"), test.getString("bio"),test.getString("picture") );

                            TextView viewName = (TextView) findViewById(R.id.name_store);
                            viewName.setText(shop.getName());

                            System.out.println(shop.getPostalCode()+"EST RECUUUUUUUUUP");
                            TextView viewPC = (TextView) findViewById(R.id.city);
                            viewPC.setText(shop.getPostalCode());

                            TextView viewPhone = (TextView) findViewById(R.id.phone);
                            viewPhone.setText(shop.getPhoneNumber());

                            TextView viewBio = (TextView) findViewById(R.id.bio);
                            viewBio.setText(shop.getBio());

                            ImageView viewPicture= findViewById(R.id.StorePicture);

                            if(shop.getPicture()!="") {

                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                String mImageURLString = "https://api-sapozone.herokuapp.com" + shop.getPicture();
                                System.out.println("Requete de recuperation de l'image");
                                System.out.println(mImageURLString);
                                ImageRequest imageRequest = new ImageRequest(
                                        mImageURLString, // Image URL
                                        new Response.Listener<Bitmap>() { // Bitmap listener
                                            @Override
                                            public void onResponse(Bitmap response) {
                                                System.out.println("retour de l'image");
                                                // Do something with response
                                                viewPicture.setImageBitmap(response);
                        /*
                        // Save this downloaded bitmap to internal storage
                        Uri uri = saveImageToInternalStorage(response);*/

                                            }
                                        },
                                        500, // Image width
                                        500, // Image height
                                        ImageView.ScaleType.CENTER_CROP, // Image scale type
                                        Bitmap.Config.RGB_565, //Image decode configuration
                                        new Response.ErrorListener() { // Error listener
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                System.out.println("erreur lors du chargement de l'image");
                                                // Do something with error response
                                                error.printStackTrace();
                                                //   Snackbar.make(mCLayout,"Error",Snackbar.LENGTH_LONG).show();
                                            }
                                        }
                                );

                                // Add ImageRequest to the RequestQueue
                                requestQueue.add(imageRequest);

                            }



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
    public void customer_request(View view){
        Intent intent = new Intent(this,RequestPrestationActivity.class);
        intent.putExtra("storeId", this.storeId);
        startActivity(intent);

    }
}