package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.model.Progress;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.example.sapozone.data.users.Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPref = null;
    private com.example.sapozone.Database db;

    protected void onCreate(Bundle savedInstanceState) {

        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        this.db = new Database(this);
        Intent intent = new Intent(this, MenuActivity.class);
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = 0;
        id = this.sharedPref.getInt("idUser", id);

        if (id != 0){
            this.db.clearTable("account");
            int finalId = id;
            System.out.println(id);
            AndroidNetworking.get("https://api-sapozone.herokuapp.com/users/{id}")
                .addPathParameter("id",String.valueOf(id))
                .build()
                .getAsJSONArray(new JSONArrayRequestListener(){
                    @Override
                    public void onResponse(JSONArray response) {

                        System.out.println(response.toString());
                        System.out.println("api retour check Update photo de profil");
                        try {
                            JSONObject user = response.getJSONObject(0);
                            String username = user.getString("username");
                            String firstname = user.optString("firstname", "");
                            String lastname = user.optString("lastname", "");
                            String phonenumber = user.optString("phone_number", "");
                            String email = user.getString("email");
                            String streetnumber = user.optString("street_number", "");
                            String streetname = user.optString("street_name", "");
                            String city = user.optString("city", "");
                            String postalcode = user.optString("postal_code", "");
                            String bio = user.optString("bio", "");
                            System.out.println("DONNEES RECUP : "+username+","+finalId+","+firstname+","+lastname+","+phonenumber+","+email+","+streetname+","+city+","+postalcode+","+bio);
                            HashMap<String,Object> account = new HashMap<String,Object>();

                            account.put("id", String.valueOf(finalId));
                            account.put("username",username);
                            account.put("email",email);
                            account.put("firstname",firstname);
                            account.put("lastname",lastname);
                            account.put("phonenumber",phonenumber);
                            account.put("streetnumber",streetnumber);
                            account.put("streetname",streetname);
                            account.put("city",city);
                            account.put("postalcode",postalcode);
                            account.put("bio",bio);

                            db.addRow("account", account);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        System.out.println( error.getMessage() );
                        System.out.println( error.getErrorCode() );
                        System.out.println( error.getCause() );
                    }
                });

            finish();
            startActivity(intent);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        setContentView(R.layout.activity_accueil);
    }

    public void goToLogIn(View view) {
        setContentView(R.layout.activity_main);
    }

    public void register(View view) {
        // Create an intent for the register activity
        Intent intent = new Intent(this, RegisterActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void login(View view) {

        // Show loader
        ProgressBar loaderBar = (ProgressBar) findViewById(R.id.login_loader);
        loaderBar.setVisibility(View.VISIBLE);

        // Create an intent for the second activity
        Intent intent = new Intent(this, MenuActivity.class);

        // Get the name and email values
        EditText usernameET = findViewById(R.id.username);
        EditText passwordET = findViewById(R.id.password);

        // Extract the user information
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();

        if (username.trim().equals("") || password.trim().equals("")) {
            if (username.trim().equals("")) {
                usernameET.setError( "Merci d'indiquer un nom de compte" );
            }

            if (password.trim().equals("")) {
                passwordET.setError("Merci d'indiquer un mot de passe");
            }
        } else {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username);
                jsonObject.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AndroidNetworking.post("https://api-sapozone.herokuapp.com/sign_in/")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){
                @Override
                public void onResponse(JSONObject response) {

                    int id = 0;

                    // Hide loader
                    ProgressBar loaderBar = (ProgressBar) findViewById(R.id.login_loader);
                    loaderBar.setVisibility(View.INVISIBLE);

                    System.out.println(response.toString());
                    sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();

                    try {
                        id = response.getInt("id");
                        if (id != 0) {
                            editor.putInt("idUser", id);
                            editor.apply();
                            startActivity(intent);
                        }
                    } catch (JSONException e){
                        System.out.println("something didnt work");
                        usernameET.setError("Wrong set of login.");
                        passwordET.setError("Wrong set of login");
                    }
                }

                @Override
                public void onError(ANError error) {
                    // Hide loader
                    ProgressBar loaderBar = (ProgressBar) findViewById(R.id.login_loader);
                    loaderBar.setVisibility(View.INVISIBLE);

                    System.out.println(error.getMessage());
                    System.out.println(error.getErrorCode());
                    System.out.println(error.getCause());
                    usernameET.setError("Error login");
                    passwordET.setError("Error login");
                }
            });
        }
    }
}