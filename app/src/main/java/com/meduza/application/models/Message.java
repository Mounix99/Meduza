package com.meduza.application.models;


public class Message {
    private String userName;
    private String userAddressee;
    private String textMessage;
   // private String messageTime;

    public Message() {}
    public Message(String userName,String textMessage ) {
        this.userName = userName;
        this.textMessage = textMessage;
        //long date = new Date().getTime();
        //this.messageTime = ;

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
