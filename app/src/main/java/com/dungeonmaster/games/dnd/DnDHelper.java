package com.dungeonmaster.games.dnd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;
import com.dungeonmaster.menu.MainMenu;
import com.menu.MenuBar;

public class DnDHelper extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn_d_helper);
    }

    public void onClickCreateCharacter(View view) {
        setNextScreen(CreateCharacter.class, TO_RIGHT);
    }

    public void  onClickChooseCharacter(View view) {
//        Intent tools = new Intent(this, ChooseCharacter.class);
//        startActivity(tools);
    }
    @Override
    public void onBackPressed() {
        setNextScreen(MainMenu.class, TO_LEFT);
    }
}