package com.dungeonmaster.instruments.counters.typical;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;

public class Munchkin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manchcken);
    }

    public void onClickLevelCounter(View view) {
        System.out.println(1);
    }
}