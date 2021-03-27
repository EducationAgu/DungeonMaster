package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;

import com.dungeonmaster.LoadingScreen;
import com.serverconnection.Server;

import java.io.IOException;

public class DataSynchronizer implements Runnable {

    private Activity loadingScreen;

    public DataSynchronizer(LoadingScreen loadingScreen) {
        this.loadingScreen = loadingScreen;
    }

    //Адаптер беспроводной локальной сети Беспроводная сеть: IPv4-адрес.
    public String connectionString = "http://192.168.0.103:8080";
    @Override
    public void run() {
        try {
            // Пока просто останавливаем чтобы увидеть начальный экран с лого
            // TODO: реализовать подключение к базе, синхронизацию аккаунта в момент загрузки экрана
            Thread loadingThread = new Thread();
            loadingThread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Server server = new Server();
        String result = "no connection";
        try {
             result = server.run(connectionString + "/note/2");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        Intent mainMenu = new Intent(loadingScreen, MainMenu.class);
        loadingScreen.startActivity(mainMenu);
    }

}
