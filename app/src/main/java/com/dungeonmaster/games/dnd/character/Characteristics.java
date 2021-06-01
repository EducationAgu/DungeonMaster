package com.dungeonmaster.games.dnd.character;

import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;
import com.dungeonmaster.games.dnd.character.throwers.DeathSavingThrows;
import com.menu.MenuBar;

public class Characteristics extends MenuBar {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characteristic);
    }

    public void onClickOpenDeathThrows(View view){
        setNextScreen(DeathSavingThrows.class, TO_RIGHT);
    }
}
