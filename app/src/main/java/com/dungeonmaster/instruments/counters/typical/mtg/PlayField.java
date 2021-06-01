package com.dungeonmaster.instruments.counters.typical.mtg;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.dungeonmaster.R;
import com.google.gson.Gson;
import com.menu.MainMtgAdapter;
import com.menu.MtgPlayersListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class PlayField extends Activity {

    ExpandingList expandingList;
    MainMtgAdapter expListAdapter;
    ArrayList<Player> playersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mtg_main);
        expandingList = findViewById(R.id.listOfMtgPlayers);
        Player[] playersList = (new Gson()).fromJson(getIntent().getStringExtra("AllTheData"),
                Player[].class);

        createStartGroup(playersList);
    }

    private void createStartGroup(Player[] players) {
        playersList = new ArrayList<>();

        for(int i = 0; i < 2; i++) {
            ExpandingItem item = expandingList.createNewItem(R.layout.mtg_main_list_item);
            item.createSubItems(1);

            // кнопка открытия/закрытия
            ImageButton button = item.findViewById(R.id.buttonDopMenu);
            button.setOnClickListener(v -> item.toggleExpanded());

            // создаю нового игрока
            Player player = new Player(players[i].getName());
            playersList.add(player);

            TextView playerName = item.findViewById(R.id.NamePlayer);
            playerName.setText(String.valueOf(player.getName()));

            Button addHealth = item.findViewById(R.id.mtgSCIncHealth);
            addHealth.setOnClickListener(v -> {
                player.incHealth();
            });
            Button decHealth = item.findViewById(R.id.mtgSCIncHealth);
            decHealth.setOnClickListener(v -> {
                player.decHealth();
            });

        }

        expListAdapter = new MainMtgAdapter(this, playersList);
    }
}
