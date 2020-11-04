package com.example.sapozone;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {



    private com.example.sapozone.Database db = new com.example.sapozone.Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        // Create an intent for the second activity
        Intent intent = new Intent(this, MainActivity.class);

        // Get the name and email values
        EditText usernameET = findViewById(R.id.username);
        EditText passwordET = findViewById(R.id.password);

        // Extract the user information
        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();

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

            db.addRow("account",account);


            // Start the activity
            startActivity(intent);
        }

    }
}