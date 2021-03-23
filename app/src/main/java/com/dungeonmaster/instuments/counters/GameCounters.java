package com.dungeonmaster.instuments.counters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;

public class GameCounters extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_counters);
    }

    //TODO: нарисовать экран универсального счётчика
    public void onClickOpenUniversalCounter(View view) {
//        Intent universalCounter = new Intent(this, UniversalCounters.class);
//        startActivity(universalCounter);
    }

    public void onClickOpenTypicalCounters(View view) {
        Intent universalCounter = new Intent(this, TypicalCounters.class);
        startActivity(universalCounter);
    }
}