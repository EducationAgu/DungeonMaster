package com.dungeonmaster.instruments.counters.typical;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.typical.munchkin.Player;
import com.menu.MunchkinExpandableListAdapter;

import java.util.ArrayList;

public class Munchkin extends Activity {

    ExpandingList expandingList;
    MunchkinExpandableListAdapter expListAdapter;

    ArrayList<Player> playersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manchcken);
        expandingList = findViewById(R.id.listOfMunchkinPlayers);

        createStartGroup();
    }

    private void createStartGroup() {
        playersList = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            onClickAddPlayer(null);
        }
    }

    public void onClickAddPlayer(View view) {
        ExpandingItem item = expandingList.createNewItem(R.layout.munchkin_list_item);
        item.createSubItems(1);
// кнопка открытия/закрытия
        Button button = item.findViewById(R.id.munchkinBtnMore);
        button.setOnClickListener(v -> item.toggleExpanded());
        expListAdapter = new MunchkinExpandableListAdapter(this, playersList);
// создаю нового игрока
        Player player = new Player("Игрок " + playersList.size());
        playersList.add(player);


    }
}