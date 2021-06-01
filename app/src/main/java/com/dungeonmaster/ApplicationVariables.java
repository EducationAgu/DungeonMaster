package com.dungeonmaster;

import android.app.Application;

import com.serverconnection.model.User;

public class ApplicationVariables extends Application {
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
