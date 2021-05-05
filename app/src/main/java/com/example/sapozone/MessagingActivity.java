package com.example.sapozone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sapozone.adapters.MessageAdapter;
import com.example.sapozone.data.communication.Message;
import com.example.sapozone.data.shop.Shop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessagingActivity extends AppCompatActivity {


    String myId;
    String interlocutorId;
    String interlocutorName;

    List<Message> messages = new ArrayList<Message>();

    private ListView displayedMessages;
    private RequestQueue queue;


    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_messaging);
        this.savedInstanceState = savedInstanceState;
        this.displayedMessages = findViewById(R.id.displayedMessages);



        Bundle extras = getIntent().getExtras();
        myId = extras.getString("myId");
        interlocutorId = extras.getString("interlocutorId");
        interlocutorName = extras.getString("interlucutorName");

        System.out.println(myId+"ENNENEN");
        System.out.println(interlocutorId+"ENENDDE");

        TextView t = (TextView) findViewById(R.id.seenMessageName);
        t.setText(interlocutorName);



        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);


        String urlMessages = "https://api-sapozone.herokuapp.com/messages/user1/"+myId+"/user2/"+interlocutorId;

        System.out.println(urlMessages);
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, urlMessages,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response2) {

                        try {
                            System.out.println(response2 + "EST OKEYYY");


                            JSONArray jsonArray = new JSONArray(response2);
                            System.out.println(jsonArray.toString() + "EST OKEYYY");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject test = jsonArray.getJSONObject(i);

                                Message message = new Message(test.getString("sender_id"), test.getString("receiver_id"), test.getString("sender"),test.getString("receiver"), test.getString("content"), test.getString("date"));
                                messages.add(message);

                                System.out.println(message.getSenderId()+"EN AAAAA");

                                System.out.println(message.toString()+"EN YYYYYYYYYYYY");
                                System.out.println(messages+"EN ZZZZZZZZZZZZ");

                            }

                            System.out.println(messages+"EN ZZZZZZZZZZZZ");
                            MessageAdapter adapter = new MessageAdapter(MessagingActivity.this, messages);
                            adapter.getView(adapter.getPosition(),adapter.getConvertView(),adapter.getViewGroup()).setBackgroundColor(Color.CYAN);
                            displayedMessages.setAdapter(adapter);


                        }catch (JSONException err){
                            System.out.println("ERREEEE");
                            Log.d("Error", err.toString());
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage()+"EST PAS OKKK");
                System.out.println(error.getCause());

            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest1);


    }


    public void sendMessage(View view){

        EditText t = (EditText) findViewById(R.id.messageToSend);
        String message = t.getText().toString();
        System.out.println(message);


        String url = "https://api-sapozone.herokuapp.com/messages/";


        JSONObject requestbody = new JSONObject();
        try {
            requestbody.put("receiver_id", myId);
            requestbody.put("sender_id", interlocutorId);
            requestbody.put("id_store", "21");
            requestbody.put("content", message);
            System.out.println("request body : " + requestbody.toString());
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, requestbody, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response.toString());
                        //Recharger?
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.getMessage());
                        System.out.println(error.getCause());

                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);

    }

}