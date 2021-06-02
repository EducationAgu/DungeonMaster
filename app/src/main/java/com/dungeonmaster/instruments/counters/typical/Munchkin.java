package com.dungeonmaster.instruments.counters.typical;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.TypicalCounters;
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
        expandingList = findViewById(R.id.munchkinPlayersList);

        createStartGroup();
    }

    private void createStartGroup() {
        playersList = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            onClickAddMunchkinPlayer(null);
        }
    }

    public void onClickAddMunchkinPlayer(View view) {
        ExpandingItem item = expandingList.createNewItem(R.layout.munchkin_list_item);
        item.createSubItems(1);

        TextView textView = item.findViewById(R.id.munchkinTextViewPlayerPower);
        textView.setOnClickListener(v -> item.toggleExpanded());
        expListAdapter = new MunchkinExpandableListAdapter(this, playersList);
// создаю нового игрока
        Player player = new Player("Player " + playersList.size());
        playersList.add(player);
        TextView playerName = item.findViewById(R.id.munchkinNamePlayer);
        playerName.setText(String.valueOf(player.getName()));

// данные по игроку
        TextView level = item.findViewById(R.id.munchkinPlayerLevel);
        level.setText(String.valueOf(player.getLevel()));

        TextView equipment = item.findViewById(R.id.munchkinPlayerEquipment);
        equipment.setText(String.valueOf(player.getEquipment()));

        TextView power = item.findViewById(R.id.munchkinPlayerPower);
        power.setText(String.valueOf(player.getPowerAmount()));
// Привязка данных игрока к кнопкам +/-
        Button increaseEquipment = item.findViewById(R.id.munchkinBtnEquipmentInc);
        increaseEquipment.setOnClickListener(v -> {
            player.incEquipment();
            equipment.setText(String.valueOf(player.getEquipment()));
            power.setText(String.valueOf(player.getPowerAmount()));
        });

        Button decreaseEquipment = item.findViewById(R.id.munchkinBtnEquipmentDec);
        decreaseEquipment.setOnClickListener(v -> {
            player.decEquipment();
            equipment.setText(String.valueOf(player.getEquipment()));
            power.setText(String.valueOf(player.getPowerAmount()));
        });

        Button increaseLevel = item.findViewById(R.id.munchkinBtnLevelInc);
        increaseLevel.setOnClickListener(v -> {
            player.incLevel();
            level.setText(String.valueOf(player.getLevel()));
            power.setText(String.valueOf(player.getPowerAmount()));
        });
        Button decreaseLevel = item.findViewById(R.id.munchkinBtnLevelDec);
        decreaseLevel.setOnClickListener(v -> {
            player.decLevel();
            level.setText(String.valueOf(player.getLevel()));
            power.setText(String.valueOf(player.getPowerAmount()));
        });
        Button deleteSelf = item.findViewById(R.id.btnDeleteMunchkinPlayer);
        deleteSelf.setOnClickListener(v -> {
            playersList.remove(player);
            expandingList.removeItem(item);
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TypicalCounters.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}