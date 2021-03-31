package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;

import com.dungeonmaster.LoadingScreen;
import com.dungeonmaster.R;

public class DataSynchronizer implements Runnable {

    private Activity loadingScreen;

    public DataSynchronizer(LoadingScreen loadingScreen) {
        this.loadingScreen = loadingScreen;
    }

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
        Intent mainMenu = new Intent(loadingScreen, MainMenu.class);
        loadingScreen.startActivity(mainMenu);
        loadingScreen.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}
