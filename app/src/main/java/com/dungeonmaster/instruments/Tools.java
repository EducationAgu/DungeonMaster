package com.dungeonmaster.instruments;

import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.TypicalCounters;
import com.dungeonmaster.instruments.dice.CubeScene;
import com.dungeonmaster.instruments.notes.NotesActivity;
import com.dungeonmaster.menu.MainMenu;
import com.menu.MenuBar;

public class Tools extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
    }

    public void onClickOpenDice(View view) {
        setNextScreen(CubeScene.class, TO_RIGHT);
    }

    public void onClickOpenGameCounters(View view) {
        setNextScreen(TypicalCounters.class, TO_RIGHT);
    }

    public void onClickOpenNotes(View view) {
        setNextScreen(NotesActivity.class, TO_RIGHT);
    }

    @Override
    public void onBackPressed() {
        setNextScreen(MainMenu.class, TO_LEFT);
    }
}