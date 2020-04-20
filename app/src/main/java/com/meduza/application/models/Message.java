package com.meduza.application.models;

import com.google.firebase.Timestamp;

import java.util.Date;


public class Message {
    private String userName;
    private String userAddressee;
    private String textMessage;
   // private String messageTime;

    public Message() {}
    public Message(String userName,  String userAddressee, String textMessage ) {
        this.userName = userName;
        this.userAddressee = userAddressee;
        this.textMessage = textMessage;
        //long date = new Date().getTime();
        //this.messageTime = ;

    }


    public String getUserAddressee() {
        return userAddressee;
    }

    public void setUserAddressee(String userAddressee) {
        this.userAddressee = userAddressee;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMassage(String textMassage) {
        this.textMessage = textMassage;
    }

   /* public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }*/
}
