package com.example.sapozone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sapozone.adapters.ConversationAdapter;
import com.example.sapozone.adapters.QuotationAdapter;
import com.example.sapozone.data.communication.Conversation;
import com.example.sapozone.data.communication.Message;
import com.example.sapozone.data.shop.Quote;
import com.example.sapozone.data.shop.Shop;
import com.example.sapozone.data.users.Account;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConversationActivity extends AppCompatActivity {

    String storeId;

    //On utilise la db pour recup l'id de l'utilisateur
    Database db;


    List<Conversation> conversations = new ArrayList<Conversation>();

    private ListView displayedConversations;
    private String myId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_conversation);

        this.displayedConversations = findViewById(R.id.displayedConversations);
        System.out.println(displayedConversations+ "EST EN NOOOON");

        this.db = new Database(this);

        ArrayList<Account> accounts = (ArrayList<Account>)db.getAllRows(Database.ACCOUNT_TABLE);


        if(accounts.size()!=0) {
            System.out.println(" account found");
            Account user = accounts.get(0);
            System.out.println(user.getId()+" est le ID");

            myId = user.getId();
        }

        String conversationUrl = "https://api-sapozone.herokuapp.com/conversations/user/"+myId;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);





        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, conversationUrl,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response2) {



                        try {
                            System.out.println(response2 + "EST OKEYYY");


                            JSONArray jsonArray = new JSONArray(response2);
                            System.out.println(jsonArray.toString() + "EST OKEYYY");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject test = jsonArray.getJSONObject(i);

                                Conversation  conversation= new Conversation(test.getString("sender_id"), test.getString("receiver_id"), test.getString("sender"),test.getString("receiver"), test.getString("content"), test.getString("date"));

                                conversations.add(conversation);

                                System.out.println(conversation.toString()+"EN YYYYYYYYYYYY");

                            }

                            System.out.println(conversations.get(0)+"EN ZZZZZZZZZZZZ");
                            ConversationAdapter adapter = new ConversationAdapter(ConversationActivity.this, conversations,myId);

                            displayedConversations.setAdapter(adapter);

                            displayedConversations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position,
                                                        long id) {
                                    Conversation selectedConversation = (Conversation) displayedConversations.getItemAtPosition(position);
                                    System.out.println(selectedConversation.getMessage()+"EST CHOOSE");
                                    Intent intent = new Intent(ConversationActivity.this, MessagingActivity.class);
                                    String senderId = String.valueOf(selectedConversation.getSenderId());
                                    String receiverId = String.valueOf(selectedConversation.getReceiverId());
                                    intent.putExtra("myId", myId);
                                    if(selectedConversation.getReceiverId()==myId){
                                        intent.putExtra("interlucutorName",selectedConversation.getSenderName());
                                        intent.putExtra("interlocutorId", receiverId);

                                    }
                                    else{
                                        intent.putExtra("interlucutorName",selectedConversation.getReceiverName());
                                        intent.putExtra("interlocutorId", senderId);
                                    }
                                    startActivity(intent);
                                }
                            });



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
    public void customer_request(View view){
        Intent intent = new Intent(this,RequestPrestationActivity.class);
        intent.putExtra("storeId", this.storeId);
        startActivity(intent);
    }


    public void seeConversation(){

    }
}