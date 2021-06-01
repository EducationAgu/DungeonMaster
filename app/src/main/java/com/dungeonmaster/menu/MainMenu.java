package com.dungeonmaster.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.dungeonmaster.R;
import com.dungeonmaster.games.dnd.DnDHelper;
import com.dungeonmaster.instruments.Tools;
import com.dungeonmaster.tablegames.TableGames;
import com.menu.MenuBar;
import com.serverconnection.Server;

public class MainMenu extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        doubleBackToExitPressedOnce = false;
        if (!Server.isAvailable() || !Server.isUserLogged()){
//            findViewById(R.id.menu1).setVisibility(View.GONE);
        }
    }

    public void onClickTableGames(View view) {
        setNextScreen(TableGames.class, TO_RIGHT);
    }

    public void onClickInstruments(View view) {
        setNextScreen(Tools.class, TO_RIGHT);
    }

    public void onClickFavoriteGame(View view) {
        setNextScreen(DnDHelper.class, TO_RIGHT);
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