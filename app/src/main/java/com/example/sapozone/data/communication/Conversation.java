package com.example.sapozone.data.communication;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Conversation {



    private  String senderId;
    private String receiverId;

    private  String senderName;
    private String receiverName;
    private String message;
    private String date;


    public Conversation(String senderId, String receiverId, String senderName, String receiverName, String message, String date) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.message = message;
        this.date = date;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }



    public List<Conversation> convFromMessages(List<Message> messages) {

        ArrayList<Conversation> convs = new ArrayList<>();
        Boolean convIsIn;
        for (Iterator<Message> it = messages.iterator(); it.hasNext();) {
            Message mess = it.next();
            convIsIn = false;
            for (Iterator<Conversation> it1 = convs.iterator(); it1.hasNext(); ) {
                Conversation conversation = it1.next();
                if (conversation.getSenderId() == mess.getSenderId() && conversation.getReceiverId() == mess.getReceiverId()) {
                    convIsIn = true;
                }

            }
            if (!convIsIn) {
                //convs.add(new Conversation(mess.getSenderId(), mess.getReceiverId()));
            }
        }
        return convs;

    }


    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getDate() {
        return date;
    }


    public String toString(){
        return getSenderName();
    }
}