package com.dungeonmaster;

import android.app.Application;

import com.dungeonmaster.games.dnd.model.Hero;
import com.serverconnection.model.User;

public class ApplicationVariables extends Application {
    private String username;

    public String getUsername() {
        return username;
    }

    public Hero hero;

    public void setUsername(String username) {
        this.username = username;
    }


}
