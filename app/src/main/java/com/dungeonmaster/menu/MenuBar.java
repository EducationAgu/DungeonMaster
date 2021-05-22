package com.dungeonmaster.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dungeonmaster.ApplicationVariables;
import com.dungeonmaster.R;
import com.serverconnection.Server;

public class MenuBar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView username = findViewById(R.id.application_username);
        String usernameStr = getApplicationVariables().getUsername();
        if (usernameStr != null) {
            username.setText(usernameStr);
        }
//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_actionbar);
//        setSupportActionBar(toolbar);
    }


    public ApplicationVariables getApplicationVariables(){
        return ((ApplicationVariables) getApplicationContext());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                Server.logout(this);
                Intent signIn = new Intent(this, SignIn.class);
                startActivity(signIn);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
