package com.dungeonmaster.games.dnd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.dungeonmaster.R;
import com.dungeonmaster.menu.MenuBar;

public class CreateCharacter extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);
    }
}