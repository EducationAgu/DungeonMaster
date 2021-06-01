package com.dungeonmaster.instruments.counters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.Tools;
import com.menu.MenuBar;

public class GameCounters extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_counters);
    }

    //TODO: нарисовать экран универсального счётчика
    public void onClickOpenUniversalCounter(View view) {
//        Intent universalCounter = new Intent(this, UniversalCounters.class);
//        startActivity(universalCounter);
    }

    public void onClickOpenTypicalCounters(View view) {
        Intent universalCounter = new Intent(this, TypicalCounters.class);
        startActivity(universalCounter);
    }

    @Override
    public void onBackPressed() {
        setNextScreen(Tools.class, TO_LEFT);
    }
}