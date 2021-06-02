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
import com.dungeonmaster.instruments.counters.typical.munchkin.Player;
import com.dungeonmaster.menu.MainMenu;
import com.google.gson.Gson;
import com.menu.MunchkinExpandableListAdapter;
import com.serverconnection.Server;
import com.serverconnection.URLs;
import com.serverconnection.model.GameProgress;

import org.springframework.http.HttpMethod;

import java.util.ArrayList;

public class Munchkin extends Activity {

    public static final String GAME_NAME = "MUNCHKIN";

    ExpandingList expandingList;
    MunchkinExpandableListAdapter expListAdapter;

    ArrayList<Player> playersList;
    private Long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manchcken);
        expandingList = findViewById(R.id.munchkinPlayersList);

        id = getIntent().getLongExtra("id", 0);
        createStartGroup();
    }

    private void createStartGroup() {
        String payload = getIntent().getStringExtra("AllTheData");
        Player[] playersLists;
        if (payload != null) {
            playersLists = (new Gson()).fromJson(payload, Player[].class);
            playersList = new ArrayList<>();
            for(int i = 0; i < playersLists.length; i++) {
                addPlayer(playersLists[i]);
            }
        }
        else {
            playersList = new ArrayList<>();
            for(int i = 0; i < 3; i++) {
                addPlayer(null);
            }
        }
    }

    public void addPlayer(Player playerIn) {
        ExpandingItem item = expandingList.createNewItem(R.layout.munchkin_list_item);
        item.createSubItems(1);

        TextView textView = item.findViewById(R.id.munchkinTextViewPlayerPower);
        textView.setOnClickListener(v -> item.toggleExpanded());
        expListAdapter = new MunchkinExpandableListAdapter(this, playersList);
// создаю нового игрока
        Player player;
        if (playerIn == null){
            player = new Player("Player " + playersList.size());
        } else {
            player = playerIn;
        }
        playersList.add(player);

        TextView playerName = item.findViewById(R.id.munchkinNamePlayer);
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

    public void onClickAddMunchkinPlayer(View view) {
        addPlayer(null);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, TypicalCounters.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void onClickSaveMunchkinGame(View view){
        Gson gson = new Gson();
        String payload = gson.toJson(playersList.toArray());
        GameProgress gp = new GameProgress();
        gp.setGameName(GAME_NAME);
        gp.setPayload(payload);
        if (id != 0) {
            gp.setId(id);
        }
        Server.passRequest(HttpMethod.POST, URLs.SAVE_PROGRESS, gp);
        Server.getQueryResult(URLs.SAVE_PROGRESS);

        Toast.makeText(this, "Успешно сохранено", Toast.LENGTH_LONG).show();

        startActivity(new Intent(this, MainMenu.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}