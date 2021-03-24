package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

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
        setContentView(R.layout.activity_main);
        this.db = new Database(this);
        Intent intent = new Intent(this, MenuActivity.class);
        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = 0;
        id = sharedPref.getInt("idUser", id);

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
                            String firstname = user.getString("firstname");
                            String lastname = user.getString("lastname");
                            String phonenumber = user.getString("phone_number");
                            String email = user.getString("email");
                            String streetnumber = user.getString("street_number");
                            String streetname = user.getString("street_name");
                            String city = user.getString("city");
                            String postalcode = user.getString("postal_code");
                            String bio = user.getString("bio");

                            System.out.println(username+","+finalId+","+firstname+","+lastname+","+phonenumber+","+email+","+streetname+","+city+","+postalcode+","+bio);
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

                            db.addRow("account",account);
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

    public void register(View view) {
        // Create an intent for the register activity
        Intent intent = new Intent(this, RegisterActivity.class);
        // Start the activity
        startActivity(intent);
    }

    public void login(View view) {
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
                    System.out.println(response.toString());
                    sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    int id = 0;

                    try {
                        id = response.getInt("id");
                    } catch (JSONException e){
                        System.out.println("somthing didnt work");
                    }

                    if (id != 0) {
                        editor.putInt("idUser", id);
                        editor.apply();
                        startActivity(intent);
                    }
                }

                @Override
                public void onError(ANError error) {
                    System.out.println(error.getMessage());
                    System.out.println(error.getErrorCode());
                    System.out.println(error.getCause());
                    usernameET.setError( "Error login" );
                    passwordET.setError( "Error login" );
                }
            });
        }
    }
}