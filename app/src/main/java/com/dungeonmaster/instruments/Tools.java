package com.dungeonmaster.instruments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.GameCounters;
import com.dungeonmaster.instruments.dice.CubeScene;
import com.dungeonmaster.menu.MainMenu;
import com.menu.MenuBar;

public class Tools extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
    }

    public void onClickOpenDice(View view) {
        Intent cubeScene = new Intent(this, CubeScene.class);
        startActivity(cubeScene);
    }

    public void onClickOpenGameCounters(View view) {
        Intent gameCounters = new Intent(this, GameCounters.class);
        startActivity(gameCounters);
    }

    // TODO: нарисовать экран заметок
    public void onClickOpenNotes(View view) {
        // Intent cubeScene = new Intent(this, Notes.class);
        // startActivity(cubeScene);
    }

    @Override
    public void onBackPressed() {
        setNextScreen(MainMenu.class, TO_LEFT);
    }
}