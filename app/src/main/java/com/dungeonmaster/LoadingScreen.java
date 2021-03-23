package com.dungeonmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.dungeonmaster.menu.DataSynchronizer;
import com.dungeonmaster.menu.MainMenu;

public class LoadingScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        // Запускую отдельный поток на загрузку данных с сервера.
        Thread synchronizer = new Thread(new DataSynchronizer(this));
        synchronizer.start();
    }
}