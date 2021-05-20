package com.dungeonmaster.instruments.counters;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.typical.MagicTheGathering;
import com.dungeonmaster.instruments.counters.typical.Munchkin;
import com.dungeonmaster.menu.MenuBar;

public class TypicalCounters extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_typical_counters);
    }

    public void onMunchkinClick(View view) {
        Intent munchkin = new Intent(this, Munchkin.class);
        startActivity(munchkin);
    }

    public void onClickMagicTheGathering(View view) {
        Intent munchkin = new Intent(this, MagicTheGathering.class);
        startActivity(munchkin);
    }
}