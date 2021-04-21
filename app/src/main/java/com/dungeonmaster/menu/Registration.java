package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dungeonmaster.LoadingScreen;
import com.dungeonmaster.R;
import com.serverconnection.Server;
import com.serverconnection.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends Activity {

    TextView username, email, password, passwordConfirm;

    private UserDataChecker userDataChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_window);

        username = findViewById(R.id.username);
        email = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);
        passwordConfirm = findViewById(R.id.passwordConfirmField);

        userDataChecker = new UserDataChecker(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SignIn.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onClickRegistration(View view) {
        if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG).show();
            return;
        }

        String passwordString = password.getText().toString();
        String usernameString = username.getText().toString();
        String emailString = email.getText().toString();

        if (userDataChecker.isValidPassword(passwordString) && userDataChecker.isValidEmail(emailString) && userDataChecker.isValidUsername(usernameString)) {
            User user = new User(usernameString, passwordString, emailString);

            try {
                Server.register(user, this);
                startActivity(new Intent(this, LoadingScreen.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } catch (Exception  e) {
                e.printStackTrace();
            }
        }
    }

}
