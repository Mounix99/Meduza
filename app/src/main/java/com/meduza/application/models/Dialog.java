package com.meduza.application.models;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;

public class Dialog {
    private GenericTypeIndicator users;

    public Dialog() {}
    public Dialog(GenericTypeIndicator users) {
        this.users = users;

    }


    public GenericTypeIndicator getUsers() {
        return users;
    }

    public void setUsers(GenericTypeIndicator users) {
        this.users = users;
    }
}

