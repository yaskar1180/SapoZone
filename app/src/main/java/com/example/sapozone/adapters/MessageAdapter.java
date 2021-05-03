package com.example.sapozone.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sapozone.R;
import com.example.sapozone.data.communication.Message;

import com.example.sapozone.viewHolders.MessageHolder;


import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {


    int position;
    View convertView;
    ViewGroup viewGroup;


    public MessageAdapter(Context context, List<Message> messages
    ) {
        super(context, 0, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_row,parent, false);
        }

        MessageHolder viewHolder = (MessageHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new MessageHolder();
            viewHolder.message = (TextView) convertView.findViewById(R.id.messageText);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);

            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Message message = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.message.setText(message.getMessage());
        viewHolder.date.setText(message.getDate().toString());


        convertView.setBackgroundResource(R.drawable.messages_colors);


        return convertView;
    }

    public int getPosition() {
        return position;
    }

    public View getConvertView() {
        return convertView;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }
}
