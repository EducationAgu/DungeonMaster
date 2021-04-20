package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.dungeonmaster.R;
import com.dungeonmaster.games.dnd.DnDHelper;
import com.dungeonmaster.instruments.Tools;
import com.dungeonmaster.tablegames.TableGames;
import com.serverconnection.Server;

public class MainMenu extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        doubleBackToExitPressedOnce = false;
        if (!Server.isAvailable() || !Server.isUserLogged()){
            findViewById(R.id.btnSignOut).setVisibility(View.GONE);
        }
    }

    public void onClickTableGames(View view) {
        Intent tableGames = new Intent(this, TableGames.class);
        startActivity(tableGames);
    }

    public void onClickInstruments(View view) {
        Intent tools = new Intent(this, Tools.class);
        startActivity(tools);
    }

    public void onClickFavoriteGame(View view) {
        Intent dndHelper = new Intent(this, DnDHelper.class);
        startActivity(dndHelper);
    }

    public void onClickLogout(View view) {
        Server.logout(this);
        Intent signIn = new Intent(this, SignIn.class);
        startActivity(signIn);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private static boolean doubleBackToExitPressedOnce;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit",
                Toast.LENGTH_SHORT).show();
    }
}