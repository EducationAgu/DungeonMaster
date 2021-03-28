package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;

import com.dungeonmaster.LoadingScreen;
import com.serverconnection.Person;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
            loadingThread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person.connect();
        Intent mainMenu = new Intent(loadingScreen, MainMenu.class);
        loadingScreen.startActivity(mainMenu);
    }
}
