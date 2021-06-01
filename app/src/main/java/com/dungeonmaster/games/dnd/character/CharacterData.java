package com.dungeonmaster.games.dnd.character;

import android.os.Bundle;

import com.dungeonmaster.R;
import com.menu.MenuBar;

public class CharacterData extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_data);
    }

}
