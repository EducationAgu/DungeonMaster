package com.dungeonmaster.tablegames.available;

import android.os.Bundle;

import com.dungeonmaster.R;
import com.menu.MenuBar;

public class AvailableGames extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_games);
    }
}