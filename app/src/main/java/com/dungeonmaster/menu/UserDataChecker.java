package com.dungeonmaster.menu;

import android.app.Activity;
import android.widget.Toast;

import com.serverconnection.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataChecker {

    private Activity activity;

    private Pattern pattern;
    private Matcher matcher;

    public UserDataChecker(Activity activity) {
        this.activity = activity;
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[_A-Za-z0-9-\\\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(emailRegex);
        matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        } else {
            Toast.makeText(activity, "Ошибка в email", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean isValidPassword(String password) {
        String passwordRegex = "^[^а-яёА-ЯЁ ]+$";
        pattern = Pattern.compile(passwordRegex);
        matcher = pattern.matcher(password);
        if (matcher.matches()) {
            return true;
        }
        else {
            Toast.makeText(activity, "Ошибка в пароле", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean isValidUsername(String username) {
        String loginRegex = "^\\S+$";
        pattern = Pattern.compile(loginRegex);
        matcher = pattern.matcher(username);
        if (matcher.matches()) {
            return true;
        }
        else {
            Toast.makeText(activity, "Ошибка в логине", Toast.LENGTH_LONG).show();
            return false;
        }
    }

}
