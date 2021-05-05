package com.example.sapozone.data.communication;

public class Message {


    private  String senderId;
    private String receiverId;

    private  String senderName;
    private String receiverName;
    private String message;
    private String date;

    public Message(String senderId, String receiverId, String senderName, String receiverName, String message, String date){
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

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
