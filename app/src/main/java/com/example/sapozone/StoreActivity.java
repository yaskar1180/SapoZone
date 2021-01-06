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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;

import android.widget.EditText;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URL;
import java.util.HashMap;

public class StoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

    }

    public void createStore(View view)
    {
        // Create an intent
        Intent intent = new Intent(this, MenuActivity.class);

        // Get the informations
        EditText store_nameET = findViewById(R.id.name_store);

        EditText store_bioET = findViewById(R.id.store_bio);


        // Extract the store information
        String store_name = store_nameET.getText().toString();

        String store_bio = store_bioET.getText().toString();


        if(store_name.trim().equals("")) {
            store_nameET.setError("Name is required");
            store_nameET.setHint("Please enter your shop's name");
        }
        else
        {
            //save Store info
            HashMap<String,Object> strore = new HashMap<String,Object>();
            strore.put("name",store_name);

            strore.put("bio",store_bio);


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", store_name);

                jsonObject.put("bio", store_bio);

            } catch (JSONException e) {
                e.printStackTrace();
            }


            AndroidNetworking.post("https://api-sapozone.herokuapp.com/users/")
                    .addJSONObjectBody(jsonObject)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener(){


                        @Override
                        public void onResponse(JSONObject response) {
                            startActivity(intent);
                            System.out.println("retour api ok");

                        }

                        @Override
                        public void onError(ANError error) {
                            store_nameET.setError( "SOmething went wrong " );
                            System.out.println( error.getMessage() );
                            System.out.println( error.getErrorCode() );
                            System.out.println( error.getCause() );
                        }
                    });
        }


    }
}