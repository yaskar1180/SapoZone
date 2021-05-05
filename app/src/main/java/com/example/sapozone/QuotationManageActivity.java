package com.example.sapozone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

public class QuotationManageActivity extends AppCompatActivity {


    List<Quote> quotes = new ArrayList<Quote>();

    private ListView displayedQuotes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        setContentView(R.layout.activity_quotation_manage);

        this.displayedQuotes = findViewById(R.id.displayedQuotes);

        RequestQueue queue = Volley.newRequestQueue(this);


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

                            JSONObject jsonObject = new JSONObject(response);

                            String idStore= jsonObject.getString("id");

                            String urlQuote = "https://api-sapozone.herokuapp.com/storerequests/"+idStore;


                            StringRequest stringRequest1 = new StringRequest(Request.Method.GET, urlQuote,
                                    new Response.Listener<String>() {

                                        @Override
                                        public void onResponse(String response2) {
                                            System.out.println(response2);





                                            try {


                                                JSONArray jsonArray = new JSONArray(response2);
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject test = jsonArray.getJSONObject(i);

                                                    Quote quote = new Quote(test.getString("detail"), test.getInt("price"), test.getInt("lead"));

                                                    quotes.add(quote);
                                                    System.out.println(quote.getDetail()+"EN YYYYYYYYYYYY");
                                                    System.out.println(quotes+"EN ZZZZZZZZZZZZ");

                                                }

                                                System.out.println(quotes+"EN ZZZZZZZZZZZZ");
                                                QuotationAdapter adapter = new QuotationAdapter(QuotationManageActivity.this, quotes);

                                                displayedQuotes.setAdapter(adapter);



                                            }catch (JSONException err){
                                                System.out.println("ERREEEE");
                                                Log.d("Error", err.toString());
                                            }

                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            System.out.println(error.getMessage());
                                            System.out.println(error.getCause());

                                        }
                                    });
                            // Add the request to the RequestQueue.
                            queue.add(stringRequest1);




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