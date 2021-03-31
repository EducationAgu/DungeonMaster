package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;

import com.dungeonmaster.LoadingScreen;
import com.serverconnection.Server;
import com.serverconnection.model.User;

import org.springframework.http.HttpMethod;

import java.io.IOException;

public class DataSynchronizer implements Runnable {

    private Activity loadingScreen;

    public DataSynchronizer(LoadingScreen loadingScreen) {
        this.loadingScreen = loadingScreen;
    }

    @Override
    public void run() {
        Intent nextScreen = new Intent(loadingScreen, MainMenu.class);
        try {
            new Server(loadingScreen);
        } catch (IOException e) {
            nextScreen = new Intent(loadingScreen, Login.class);
        }


        loadingScreen.startActivity(nextScreen);
    }
}
