package com.example.sapozone;

import android.content.Intent;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RegisterActivity extends AppCompatActivity {

    private com.example.sapozone.Database db = new com.example.sapozone.Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {

        // Create an intent for the second activity
        Intent intent = new Intent(this, MainActivity.class);

        // Get the name and email values
        EditText usernameET = findViewById(R.id.username);
        EditText emailET = findViewById(R.id.email);
        EditText passwordET = findViewById(R.id.password);

        // Extract the user information
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        String email = emailET.getText().toString();

        if(username.trim().equals("")) {
            usernameET.setError( "Name is required" );
            usernameET.setHint("Please enter your name");
        } else if(password.trim().equals("")) {
            passwordET.setError( "Email is required" );
            passwordET.setHint("Please enter your email");
        } else {

            // Save user information
            HashMap<String,Object> account = new HashMap<String,Object>();
            account.put("username",username);
            account.put("password",password);
            account.put("email",email);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("username", username);
                jsonObject.put("password", password);
                jsonObject.put("email", email);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            db.clearTable("account");
            db.addRow("account", account);
            AndroidNetworking.post("https://api-sapozone.herokuapp.com/users/")
                .addJSONObjectBody(jsonObject)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener(){

                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(getApplicationContext(), "Le compte a été créé avec succès.", Toast.LENGTH_SHORT).show();
                    System.out.println(" ***** REPONSE *****" + response.toString());
                    startActivity(intent);
                }

                @Override
                public void onError(ANError error) {
                    String errorBody = error.getErrorBody();
                    String message = "An error has occured. Please try Again.";
                    JsonParser parser = new JsonParser();
                    JsonObject messageJson = (JsonObject) parser.parse(errorBody);
                    if(messageJson.has("error")) {
                        message = messageJson.get("error").getAsString();
                    }
                    emailET.setError(error.toString());
                    usernameET.setError(error.toString());
                    Log.e("REGISTER ERROR : ", String.valueOf(error.getErrorCode()));
                    Toast.makeText(getApplicationContext(), "Error : " + message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}