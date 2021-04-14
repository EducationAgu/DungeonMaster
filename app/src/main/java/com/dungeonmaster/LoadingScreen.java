package com.dungeonmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dungeonmaster.menu.DataSynchronizer;
import com.dungeonmaster.menu.MainMenu;
import com.serverconnection.Server;
import com.serverconnection.URLs;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class LoadingScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        String lastUrl = Server.getLastUrl();
        if (lastUrl != null) {
            ResponseEntity<String> response = Server.getQueryResult(lastUrl);
            if (lastUrl.equals(URLs.REGISTRATION)) {
                if (response.getStatusCode() == HttpStatus.CREATED) {

                } else {
                    Toast.makeText(this, "Ошибка при попытке зарегистрироваться!",
                            Toast.LENGTH_SHORT).show();
                }
                Intent nextScreen = new Intent(this, MainMenu.class);
                startActivity(nextScreen);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                return;
            }
        }

        // Запускую отдельный поток на загрузку данных с сервера.
        Thread synchronizer = new Thread(new DataSynchronizer(this));
        synchronizer.start();
    }
}