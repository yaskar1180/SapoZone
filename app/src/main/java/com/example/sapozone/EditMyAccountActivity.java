package com.example.sapozone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.collection.ArraySet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.sapozone.VolleyRequests.VolleyMultipartRequest;
import com.example.sapozone.data.users.Account;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditMyAccountActivity extends AppCompatActivity {
    Database db;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_account);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.db = new Database(this);

        ArrayList<Account> accounts = (ArrayList<Account>)db.getAllRows(Database.ACCOUNT_TABLE);

        EditText usernameTE = findViewById(R.id.edit_username);
        EditText emailTE = findViewById(R.id.edit_email);
        EditText phoneTE = findViewById(R.id.edit_phone);
        EditText bioTE = findViewById(R.id.edit_bio);



        if(accounts.size()!=0) {
            System.out.println(" account found edit");
            Account user = accounts.get(0);
            System.out.println(user.getEmail());

            usernameTE.setText(user.getUsername());
            emailTE.setText(user.getEmail());
            phoneTE.setText(user.getPhonenumber());
            bioTE.setText(user.getBio());
        }
        else System.out.println("no account found");

        Button update_pp = findViewById(R.id.Update_pp);
        update_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("changement de photo de profile");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,21);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==21 && resultCode ==RESULT_OK && data!=null){
            Uri path = data.getData();
            System.out.println(path);

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                uploadBitmap(bitmap);
                /*ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,75,outputStream);
                byte[] image = outputStream.toByteArray();*/
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    private void uploadBitmap(final Bitmap bitmap) {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, "https://api-sapozone.herokuapp.com/api/media_objects",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            System.out.println("image envoyé");
                            JSONObject obj = new JSONObject(new String(response.data));
                            String url = obj.getString("contentUrl");
                            System.out.println(url);
                            updateProfilePicture(url);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("GotError",""+error.getMessage());
                    }
                }) {


            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new VolleyMultipartRequest.DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    public void updateProfilePicture(String Url){
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("pp", Url);


        }
        catch (JSONException e){}
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = 0;
        id = sharedPref.getInt("idUser",id);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api-sapozone.herokuapp.com/users/"+id;
        int finalId = id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            System.out.println("Reponse d'edit il y'a");
                            String username = response.getString("username");
                            String firstname = response.getString("firstname");
                            String lastname = response.getString("lastname");
                            String phonenumber = response.getString("phone_number");
                            String email = response.getString("email");
                            String streetnumber = response.getString("street_number");
                            String streetname = response.getString("street_name");
                            String city = response.getString("city");
                            String postalcode = response.getString("postal_code");
                            String bio = response.getString("bio");
                            String pp = response.getString("pp");

                            System.out.println(username+","+ finalId +","+firstname+","+lastname+","+phonenumber+","+email+","+streetname+","+city+","+postalcode+","+bio);
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

                            System.out.println("new pp"+ pp);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("pp", pp);
                            editor.apply();


                            db.clearTable("account");
                            db.addRow("account",account);


                        }
                        catch (JSONException e ){
                            System.out.println("echec JSON");
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        System.out.println(error.getMessage());

                    }
                });

        queue.add(jsonObjectRequest);


    }


    public void updateAccount(View view){

        EditText usernameTE = findViewById(R.id.edit_username);
        EditText emailTE = findViewById(R.id.edit_email);
        EditText phoneTE = findViewById(R.id.edit_phone);
        EditText bioTE = findViewById(R.id.edit_bio);

        Intent intent = new Intent(this, MyAccountActivity.class);

        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put("username", usernameTE.getText().toString());
            jsonObject.put("email", emailTE.getText().toString());
            jsonObject.put("phone_number", phoneTE.getText().toString());
            jsonObject.put("bio", bioTE.getText().toString());

        }
        catch (JSONException e){}
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int id = 0;
        id = sharedPref.getInt("idUser",id);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api-sapozone.herokuapp.com/users/"+id;
        int finalId = id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            System.out.println("Reponse d'edit il y'a");
                            String username = response.getString("username");
                            String firstname = response.getString("firstname");
                            String lastname = response.getString("lastname");
                            String phonenumber = response.getString("phone_number");
                            String email = response.getString("email");
                            String streetnumber = response.getString("street_number");
                            String streetname = response.getString("street_name");
                            String city = response.getString("city");
                            String postalcode = response.getString("postal_code");
                            String bio = response.getString("bio");

                            System.out.println(username+","+ finalId +","+firstname+","+lastname+","+phonenumber+","+email+","+streetname+","+city+","+postalcode+","+bio);
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

                            db.clearTable("account");
                            db.addRow("account",account);

                            startActivity(intent);
                        }
                        catch (JSONException e ){
                            System.out.println("echec JSON");
                            System.out.println(e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        System.out.println(error.getMessage());

                    }
                });

        queue.add(jsonObjectRequest);


    }
}