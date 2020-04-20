package com.meduza.application.models;

public class Note {

    private String title;
    private String description;
    private String userName;
    private boolean access;


    public Note() {
        //needed for Firebase
    }




    public Note(String title, String description, String userName, boolean access) {
        this.title = title;
        this.description = description;
        this.userName = userName;



    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAccess() {
        return access;
    }

    public void setAccess(boolean access) {
        this.access = access;
    }

}
