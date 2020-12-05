package com.example.sapozone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import java.util.Iterator;


import com.example.sapozone.data.users.Account;


public class MainActivity extends AppCompatActivity {


    private SharedPreferences sharedPref = null;
    private com.example.sapozone.Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.db = new Database(this);


        this.sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(!sharedPref.getString("username", "").trim().equals("")) {
            if(!sharedPref.getString("password", "").trim().equals("")){
                Intent intent = new Intent(this, MenuActivity.class);

                ArrayList<Account> accounts = (ArrayList<Account>)db.getAllRows(Database.ACCOUNT_TABLE);
                int connect = 0;

                for(Iterator<Account> it = accounts.iterator(); it.hasNext();){

                    Account current =it.next();
                    System.out.println(current.getUsername());
                    System.out.println(sharedPref.getString("username",""));

                    if(current.getUsername().equalsIgnoreCase(sharedPref.getString("username",""))){
                        System.out.println("Testsee");

                        if(current.getPassword().equalsIgnoreCase(sharedPref.getString("password",""))){
                            System.out.println("Testee");
                            connect = 1;
                        }
                    }
                }
                if(connect==1){
                    // Kill this activity
                    finish();

                    // Start the activity
                    startActivity(intent);

                }

            }
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

        if(username.trim().equals("")) {
            usernameET.setError( "Merci d'indiquer un nom de compte" );
            usernameET.setHint("Nom de compte");
        }
        else if(password.trim().equals("")) {
            passwordET.setError( "Merci d'indiquer un mot de passe" );
            passwordET.setHint("Mot de passe");
        }
        else {

            int connect = 0;
            //search if account exist
            System.out.println(db.getAllRows(Database.ACCOUNT_TABLE));
            ArrayList<Account> accounts = new ArrayList<Account>();
            accounts = (ArrayList<Account>)db.getAllRows(Database.ACCOUNT_TABLE);

            for(Iterator<Account>it=accounts.iterator();it.hasNext();){

                Account current =it.next();
                System.out.println(current.getUsername());
                if(current.getUsername().equalsIgnoreCase(username)){
                    if(current.getPassword().equalsIgnoreCase(password)){
                        connect = 1;
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", username);
                        editor.putString("password",password);
                        editor.apply();
                    }
                }
            }
            if(connect==1){
                startActivity(intent);
            }
            else{
                usernameET.setError( "Error login" );
                passwordET.setError( "Error login" );
            }
        }

    }


}