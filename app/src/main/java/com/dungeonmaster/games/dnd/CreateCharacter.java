package com.dungeonmaster.games.dnd;

import android.os.Bundle;

import com.dungeonmaster.R;
import com.dungeonmaster.menu.MainMenu;
import com.menu.MenuBar;

public class CreateCharacter extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);
    }
    @Override
    public void onBackPressed() {
        setNextScreen(DnDHelper.class, TO_LEFT);
    }
}