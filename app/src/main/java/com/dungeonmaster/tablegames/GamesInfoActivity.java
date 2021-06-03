package com.dungeonmaster.tablegames;

import android.os.Bundle;
import android.widget.TextView;

import com.dungeonmaster.R;
import com.google.gson.Gson;
import com.menu.MenuBar;
import com.serverconnection.model.GameRule;


public class GamesInfoActivity extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        GameRule gameRule = (new Gson()).fromJson(getIntent().getStringExtra("AllTheData"),
                GameRule.class);
        TextView tittle = findViewById(R.id.TVTile);
        tittle.setText(gameRule.name);

        TextView countPlayers = findViewById(R.id.CountPlayers);
        countPlayers.setText(gameRule.playersAmount);

        TextView complect = findViewById(R.id.Complect);
        complect.setText(gameRule.complectation);

        TextView discription = findViewById(R.id.Discription);
        discription.setText(gameRule.shortDiscriprion);

        TextView rules = findViewById(R.id.Rules);
        rules.setText(gameRule.rools);
    }
}
