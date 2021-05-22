package com.dungeonmaster;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.dungeonmaster.menu.DataSynchronizer;
import com.dungeonmaster.menu.MainMenu;
import com.dungeonmaster.menu.Registration;
import com.dungeonmaster.menu.SignIn;
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
            switch(lastUrl){
                case URLs.REGISTRATION:
                    new WaitRegistration().execute(this);
                    return;
                case URLs.LOGIN:
                    new WaitLogin().execute(this);
                    return;
                default:
                    break;
            }
        }

        // Запускую отдельный поток на загрузку данных с сервера.
        Thread synchronizer = new Thread(new DataSynchronizer(this));
        synchronizer.start();
    }

    class WaitRegistration extends AsyncTask<Activity, Void, Void> {

        @Override
        protected Void doInBackground(Activity... activities) {
            Activity activity = activities[0];

            Intent nextScreen = new Intent(activity, MainMenu.class); // экран в который идём если всё чотко
            int animIn = R.anim.slide_in_right; // анимация
            int animOut = R.anim.slide_out_left; // если всё чотко

            ResponseEntity<String> response = Server.getQueryResult(URLs.REGISTRATION);
            if (response.getStatusCode() != HttpStatus.CREATED) {
                String body = response.getBody();
                activity.runOnUiThread(() -> Toast.makeText(activity, "Ошибка при регистрации!\n" + body,
                        Toast.LENGTH_LONG).show());
                nextScreen = new Intent(activity, Registration.class);
                animIn = R.anim.slide_in_left;
                animOut = R.anim.slide_out_right;
            }
            String username = response.getBody();
            ((ApplicationVariables) getApplicationContext()).setUsername(username);
            activity.startActivity(nextScreen);
            activity.overridePendingTransition(animIn, animOut);
            return null;
        }

    }

    class WaitLogin extends AsyncTask<Activity, Void, Void> {

        @Override
        protected Void doInBackground(Activity... activities) {
            Activity activity = activities[0];

            Intent nextScreen = new Intent(activity, MainMenu.class); // экран в который идём если всё чотко
            int animIn = R.anim.slide_in_right; // анимация
            int animOut = R.anim.slide_out_left; // если всё чотко

            ResponseEntity<String> response = Server.getQueryResult(URLs.LOGIN);
            if (response.getStatusCode() != HttpStatus.OK) {
                if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                    activity.runOnUiThread(() -> Toast.makeText(activity, "Ошибка при попытке войти!\nНеверный логин/пароль!",
                            Toast.LENGTH_SHORT).show());
                    nextScreen = new Intent(activity, SignIn.class);
                    animIn = R.anim.slide_in_left;
                    animOut = R.anim.slide_out_right;
                }
            }
            String name = response.getBody();
            ((ApplicationVariables) getApplicationContext()).setUsername(name);
            activity.startActivity(nextScreen);
            activity.overridePendingTransition(animIn, animOut);
            return null;
        }

    }

}