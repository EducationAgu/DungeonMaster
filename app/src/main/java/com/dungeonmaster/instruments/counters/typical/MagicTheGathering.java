package com.dungeonmaster.instruments.counters.typical;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.TypicalCounters;
import com.dungeonmaster.instruments.counters.typical.mtg.PlayField;
import com.dungeonmaster.instruments.counters.typical.mtg.Player;
import com.google.gson.Gson;
import com.menu.MenuBar;
import com.menu.MtgPlayersListAdapter;

import java.util.ArrayList;

public class MagicTheGathering extends Activity {

    ExpandingList expandingList;
    MtgPlayersListAdapter expListAdapter;

    ArrayList<Player> playersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic_the_gathering);
        expandingList = findViewById(R.id.mtgPlayersList);

        createStartGroup();
    }

    private void createStartGroup() {
        playersList = new ArrayList<>();
        for(int i = 0; i < 2; i++) {
            onClickAddMtgPlayer(null);
        }
    }

    public void onClickAddMtgPlayer(View view) {

        ExpandingItem item = expandingList.createNewItem(R.layout.mtg_list_item);
        item.createSubItems(1);

// создаю нового игрока
        Player player = new Player("Player " + playersList.size());
        playersList.add(player);

        TextView leftView = item.findViewById(R.id.mtgRightDummy);
        leftView.setOnClickListener(v -> item.toggleExpanded());

        TextView rightView = item.findViewById(R.id.mtgLeftDummy);
        rightView.setOnClickListener(v -> item.toggleExpanded());

        expListAdapter = new MtgPlayersListAdapter(this, playersList);
        TextView playerName = item.findViewById(R.id.mtgNamePlayer);
        playerName.setText(String.valueOf(player.getName()));

        playerName.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Имя игрока");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            input.setText(playerName.getText());
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                String newName = input.getText().toString();
                if (newName.length() > 10) {
                    Toast.makeText(this, "Имя должно быть менее 10 символов",  Toast.LENGTH_LONG).show();
                } else {
                    playerName.setText(newName);
                    player.setName(newName);
                }
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();

            Button btnOk = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);

            btnOk.setBackgroundColor(getColor(R.color.main_background));
            btnOk.setTextColor(getColor(R.color.button_text_color));
        });

        Button deleteSelf = item.findViewById(R.id.btnDeleteMtgPlayer);
        deleteSelf.setOnClickListener(v -> {
            playersList.remove(player);
            expandingList.removeItem(item);
        });
    }

    public void reset(View view) {
        // TODO сделать сброс всего
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TypicalCounters.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onClickStartMTG(View view){
        Intent intent = new Intent(this, PlayField.class);
        Gson gson = new Gson();
        intent.putExtra("AllTheData", gson.toJson(playersList.toArray()));

        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

}