package com.dungeonmaster.instruments.counters.typical;

import android.content.Intent;
import android.os.Bundle;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.TypicalCounters;
import com.menu.MenuBar;

public class MagicTheGathering extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_the_gathering);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TypicalCounters.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}