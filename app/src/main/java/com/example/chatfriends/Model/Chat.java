package com.example.chatfriends.Model;

public class Chat {

    private String sender;
    private String reciever;
    private String message;

    public String getSender() {
        return sender;
    }

    public Chat(){

    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Chat(String sender, String reciever, String message) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
    }
}