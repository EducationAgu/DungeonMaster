package com.dungeonmaster.instruments.counters.typical;

import android.app.Activity;
import android.os.Bundle;

import com.dungeonmaster.R;
import com.dungeonmaster.menu.MenuBar;

public class MagicTheGathering extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_the_gathering);
    }
}