package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dungeonmaster.LoadingScreen;
import com.dungeonmaster.R;
import com.serverconnection.Server;
import com.serverconnection.model.User;

public class Registration extends Activity {

    TextView username, email, password, passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_window);

        username = findViewById(R.id.usernameField);
        email = findViewById(R.id.emailField);
        password = findViewById(R.id.passwordField);
        passwordConfirm = findViewById(R.id.passwordConfirmField);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, SignIn.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onClickRegistration(View view) {
        if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
            //вывести сообщение об ошибке
            return;
        }
        User user = new User(username.getText().toString(), password.getText().toString(),
                email.getText().toString());
        try {
            Server.register(user, this);
            startActivity(new Intent(this, LoadingScreen.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } catch (Exception  e) {
            e.printStackTrace();
        }
    }

}
