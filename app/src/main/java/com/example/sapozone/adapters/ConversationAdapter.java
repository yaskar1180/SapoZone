package com.example.sapozone.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sapozone.Database;
import com.example.sapozone.R;
import com.example.sapozone.data.communication.Conversation;
import com.example.sapozone.data.communication.Message;
import com.example.sapozone.data.users.Account;
import com.example.sapozone.viewHolders.ConversationHolder;
import com.example.sapozone.viewHolders.MessageHolder;

import java.util.ArrayList;
import java.util.List;



public class ConversationAdapter extends ArrayAdapter<Conversation> {



    private Database db;
    private String myId = "";


    public ConversationAdapter(Context context, List<Conversation> conversations, String myId
    ) {
        super(context, 0, conversations);
        this.myId = myId;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.conversation_row,parent, false);
        }

        ConversationHolder viewHolder = (ConversationHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ConversationHolder();

            viewHolder.senderName = (TextView) convertView.findViewById(R.id.senderName);
            viewHolder.message = (TextView) convertView.findViewById(R.id.messageText);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<conversation>
        Conversation conversation = getItem(position);

        //il ne reste plus qu'à remplir notre vue

        //on verifie quel username affiché


        if(conversation.getReceiverId()==myId){
            viewHolder.senderName.setText(conversation.getSenderName());
        }
        else{
            viewHolder.senderName.setText(conversation.getReceiverName());
        }
        viewHolder.message.setText(conversation.getMessage()+" €");
        viewHolder.date.setText(conversation.getDate().toString());

        return convertView;
    }

}
