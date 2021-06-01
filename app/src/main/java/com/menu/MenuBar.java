package com.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dungeonmaster.ApplicationVariables;
import com.dungeonmaster.R;
import com.dungeonmaster.menu.SignIn;
import com.serverconnection.Server;

public class MenuBar extends MainMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_menu);
        String usernameStr = getApplicationVariables().getUsername();
        if (usernameStr != null) {
            setTitle(usernameStr);
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