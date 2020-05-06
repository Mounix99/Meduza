package com.meduza.application.models;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;

public class Dialog {
    private ArrayList<String> users;

    public Dialog() {}
    public Dialog(ArrayList<String> users) {
        this.users = users;

    }


    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }
}

