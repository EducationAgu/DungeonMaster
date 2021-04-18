package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dungeonmaster.LoadingScreen;
import com.dungeonmaster.R;
import com.serverconnection.Server;
import com.serverconnection.URLs;
import com.serverconnection.model.User;

import org.springframework.http.HttpMethod;

public class SignIn extends Activity {

    private EditText loginTextBox;
    private EditText passwordTextBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_window);
        loginTextBox = findViewById(R.id.emailField);
        passwordTextBox = findViewById(R.id.passwordField);
    }

    public void onClickLogin(View view) {
        User user = new User();

        String login = loginTextBox.getText().toString();
        user.setLogin(login);

        user.setPassword(passwordTextBox.getText().toString());

        Server.login(user, this);
        Intent loadingScreen = new Intent(this, LoadingScreen.class);
        startActivity(loadingScreen);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onClickOpenRegistration(View view){
        Intent nextScreen = new Intent(this, Registration.class);
        startActivity(nextScreen);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
