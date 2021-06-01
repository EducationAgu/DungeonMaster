package com.dungeonmaster.instruments.counters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.Tools;
import com.dungeonmaster.instruments.counters.typical.MagicTheGathering;
import com.dungeonmaster.instruments.counters.typical.Munchkin;
import com.menu.MenuBar;

public class TypicalCounters extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typical_counters);
    }

    public void onMunchkinClick(View view) {
        setNextScreen(Munchkin.class, TO_RIGHT);
    }

    public void onClickMagicTheGathering(View view) {
        setNextScreen(MagicTheGathering.class, TO_RIGHT);
    }

    @Override
    public void onBackPressed() {
        setNextScreen(Tools.class, TO_LEFT);
    }
}