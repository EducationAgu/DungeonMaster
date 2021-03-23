package com.dungeonmaster.tablegames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;
import com.dungeonmaster.tablegames.available.AvailableGames;

public class TableGames extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_games);
    }

    public void onClickAvailableGames(View view) {
        Intent availableGames = new Intent(this, AvailableGames.class);
        startActivity(availableGames);
    }
    public void onClickMyGames(View view) {
        Intent availableGames = new Intent(this, AvailableGames.class);
        startActivity(availableGames);
    }
}