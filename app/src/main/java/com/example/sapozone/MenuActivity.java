package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    private SharedPreferences sharedPref = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_menu);


<<<<<<< Updated upstream
=======
        this.displayedShops = findViewById(R.id.displayedShops);



        String url = "https://api-sapozone.herokuapp.com/stores/";

        Shop s1 = new Shop(1, "Test",1);
        this.shops.add(s1);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Display the first 500 characters of the response string.
                        System.out.println("Response menu is: "+ response);


                        try {

                            System.out.println("ON EST DEDANS");

                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject test = jsonArray.getJSONObject(i);
                                Shop shop = new Shop(test.getInt("id"),test.getString("name"),11 );
                                shops.add(shop);
                                System.out.println(shop.getId());
                            }

                            ShopAdapter adapter = new ShopAdapter(MenuActivity.this, shops);

                            displayedShops.setAdapter(adapter);


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

        System.out.println(this.shops);



        System.out.println("ON A PAS ATTENDUUUUUUUUUu");

>>>>>>> Stashed changes
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.search);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "On va ouvrir un pop up de recherche avancée", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_movie, R.id.nav_renewal,
                R.id.nav_account, R.id.nav_consumable, R.id.nav_ticket)
                .setDrawerLayout(drawer)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    public void disconnect(View view){
        Intent intent = new Intent(this, MainActivity.class);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
        startActivity(intent);
    }

    public void myAccount(MenuItem view){
        Intent intent = new Intent(this, MyAccountActivity.class);
        startActivity(intent);
    }



}

