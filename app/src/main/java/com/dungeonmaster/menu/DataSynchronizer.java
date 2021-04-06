package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;

import com.dungeonmaster.LoadingScreen;
import com.error.NoConnection;
import com.serverconnection.Server;
import com.serverconnection.model.User;

import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.dungeonmaster.R;

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
        } catch (NoConnection e) {
            nextScreen = new Intent(loadingScreen, MainMenu.class);
            loadingScreen.startActivity(nextScreen);
            loadingScreen.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return;
        } catch (IOException e) {
            nextScreen = new Intent(loadingScreen, Registration.class);
            loadingScreen.startActivity(nextScreen);
            loadingScreen.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return;
        }
        loadingScreen.startActivity(nextScreen);
        loadingScreen.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
