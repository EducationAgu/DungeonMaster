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
        try {
            new Server(loadingScreen);
        } catch (IOException e) {
            // Если нет файла с данными пользователя то программа свалится сюда
            // Нужно будет вызвать экран регистрации
            try {
                Server.register(new User("login","password","mail@gmail.com"), loadingScreen);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        Intent mainMenu = new Intent(loadingScreen, MainMenu.class);
        loadingScreen.startActivity(mainMenu);
    }
}
