package com.dungeonmaster.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;
import com.dungeonmaster.games.dnd.DnDHelper;
import com.dungeonmaster.instruments.Tools;
import com.dungeonmaster.tablegames.TableGames;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
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
}