package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sapozone.data.shop.Shop;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.preference.PreferenceManager;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    private SharedPreferences sharedPref = null;


    private ListView displayedShops;


    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Sophie", "Tristan", "Ulric", "Vincent", "Willy", "Xavier",
            "Yann", "Zoé"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_menu);


        this.displayedShops = findViewById(R.id.displayedShops);


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MenuActivity.this,
                android.R.layout.simple_list_item_1, prenoms);
        displayedShops.setAdapter(adapter);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.searchFloatingButton);
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
                R.id.nav_subscription, R.id.nav_consumable, R.id.nav_ticket)
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


    private List<Shop> getAllShops(){
        List<Shop> shops = new ArrayList<Shop>();


        AndroidNetworking.post("https://api-sapozone.herokuapp.com/sign_in/")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){


                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sharedPref.edit();
                        int id=0;
                        try {
                            id=response.getInt("id");
                        }
                        catch (JSONException e){
                            System.out.println("somthing didnt work");

                        }
                        if (id!=0) {
                            editor.putInt("idUser", id);
                            editor.apply();
                            startActivity(intent);
                        }



                    }

                    @Override
                    public void onError(ANError error) {
                        System.out.println( error.getMessage() );
                        System.out.println( error.getErrorCode() );
                        System.out.println( error.getCause() );
                        usernameET.setError( "Error login" );
                        passwordET.setError( "Error login" );
                    }
                });








        return shops;
    }

}

