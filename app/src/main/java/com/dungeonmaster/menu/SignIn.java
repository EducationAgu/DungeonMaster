package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dungeonmaster.LoadingScreen;
import com.dungeonmaster.R;
import com.serverconnection.Server;
import com.serverconnection.model.User;

import java.io.Console;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignIn extends Activity {

    private EditText loginTextBox;
    private EditText passwordTextBox;

    private UserDataChecker userDataChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_window);
        loginTextBox = findViewById(R.id.emailField);
        passwordTextBox = findViewById(R.id.passwordField);

        userDataChecker = new UserDataChecker(this);
    }

    public void onClickLogin(View view) {
        User user = new User();

        String login = loginTextBox.getText().toString();
        String password = passwordTextBox.getText().toString();
        // userDataChecker.isValidEmail(login) &&
        if (userDataChecker.isValidPassword(password)) {
            user.setLogin(login);
            user.setPassword(password);

            Server.login(user, this);
            Intent loadingScreen = new Intent(this, LoadingScreen.class);
            startActivity(loadingScreen);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }

    }

    public void onClickOpenRegistration(View view){
        Intent nextScreen = new Intent(this, Registration.class);
        startActivity(nextScreen);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
