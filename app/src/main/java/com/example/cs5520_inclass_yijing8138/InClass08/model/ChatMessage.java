package com.example.cs5520_inclass_yijing8138.InClass08.model;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage implements Serializable {
    private String messageText;
    private String chatUser;
    private String time;
    private String number;
    private String isImageUri;

    public ChatMessage() {
    }

    @SuppressLint("SimpleDateFormat")
    public ChatMessage(String messageText, String chatUser, String number, String isImageUri) {
        this.messageText = messageText;
        this.chatUser = chatUser;
        this.number = number;
        this.isImageUri = isImageUri;
        this.time = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss").format(new Date().getTime());
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getChatUser() {
        return chatUser;
    }

    public void setChatUser(String chatUser) {
        this.chatUser = chatUser;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIsImageUri() {
        return isImageUri;
    }

    public void setIsImageUri(String isImageUri) {
        this.isImageUri = isImageUri;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
