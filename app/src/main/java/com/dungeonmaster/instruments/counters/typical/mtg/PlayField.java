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

import java.util.ArrayList;

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

            // Блок здоровья
            TextView health = item.findViewById(R.id.TextViewSCHealth);

            Button addHealth = item.findViewById(R.id.mtgSCIncHealth);
            addHealth.setOnClickListener(v -> {
                player.incHealth();
                health.setText(String.valueOf(player.getHealth()));
            });
            Button decHealth = item.findViewById(R.id.mtgSCDecHealth);
            decHealth.setOnClickListener(v -> {
                player.decHealth();
                health.setText(String.valueOf(player.getHealth()));
            });
            Button cancelHealth = item.findViewById(R.id.buttonSCResetHealth);
            cancelHealth.setOnClickListener(v -> {
                player.setHealth(0);
                health.setText(String.valueOf(player.getHealth()));
            });

            // Блок infect
            TextView infectTextView = item.findViewById(R.id.TextViewSCInfect);

            Button addInfect = item.findViewById(R.id.mtgSCIncInfect);
            addInfect.setOnClickListener(v -> {
                player.incInfect();
                infectTextView.setText(String.valueOf(player.getInfect()));
            });
            Button decInfect = item.findViewById(R.id.mtgSCDecInfect);
            decInfect.setOnClickListener(v -> {
                player.decInfect();
                infectTextView.setText(String.valueOf(player.getInfect()));
            });
            Button cancelInfect = item.findViewById(R.id.buttonSCResetInfect);
            cancelInfect.setOnClickListener(v -> {
                player.setInfect(0);
                infectTextView.setText(String.valueOf(player.getInfect()));
            });

            // Блок сыгранных карт
            TextView playedCardTextView = item.findViewById(R.id.TextViewSC);

            Button addTSC = item.findViewById(R.id.mtgSCInc);
            addTSC.setOnClickListener(v -> {
                player.incCardsPlayed();
                playedCardTextView.setText(String.valueOf(player.getCardsPlayed()));
            });
            Button decTSC = item.findViewById(R.id.mtgSCDec);
            decTSC.setOnClickListener(v -> {
                player.decCardsPlayed();
                playedCardTextView.setText(String.valueOf(player.getCardsPlayed()));
            });
            Button cancelSC = item.findViewById(R.id.buttonSCReset);
            cancelSC.setOnClickListener(v -> {
                player.setCardsPlayed(0);
                playedCardTextView.setText(String.valueOf(player.getCardsPlayed()));
            });


            // Белый блок
            TextView white = item.findViewById(R.id.TextViewSCW);

            Button whiteInc = item.findViewById(R.id.mtgSCIncW);
            whiteInc.setOnClickListener(v -> {
                player.incWhite();
                white.setText(String.valueOf(player.getWhite()));
            });
            Button whiteDec = item.findViewById(R.id.mtgSCDecW);
            whiteDec.setOnClickListener(v -> {
                player.decWhite();
                white.setText(String.valueOf(player.getWhite()));
            });
            Button cancelWhite = item.findViewById(R.id.buttonSCResetW);
            cancelWhite.setOnClickListener(v -> {
                player.setWhite(0);
                white.setText(String.valueOf(player.getWhite()));
            });

            // Чёрный блок
            TextView black = item.findViewById(R.id.TextViewSCBlack);

            Button blackInc = item.findViewById(R.id.mtgSCIncBlack);
            blackInc.setOnClickListener(v -> {
                player.incBlack();
                black.setText(String.valueOf(player.getBlack()));
            });
            Button blackDec = item.findViewById(R.id.mtgSCDecBlack);
            blackDec.setOnClickListener(v -> {
                player.decBlack();
                black.setText(String.valueOf(player.getBlack()));
            });
            Button cancelBlack = item.findViewById(R.id.buttonSCResetBlack);
            cancelBlack.setOnClickListener(v -> {
                player.setBlack(0);
                black.setText(String.valueOf(player.getBlack()));
            });

            // Синий блок
            TextView blue = item.findViewById(R.id.TextViewSCBlue);

            Button blueInc = item.findViewById(R.id.mtgSCIncBlue);
            blueInc.setOnClickListener(v -> {
                player.incBlue();
                blue.setText(String.valueOf(player.getBlue()));
            });
            Button blueDec = item.findViewById(R.id.mtgSCDecBlue);
            blueDec.setOnClickListener(v -> {
                player.decBlue();
                blue.setText(String.valueOf(player.getBlue()));
            });
            Button cancelBlue = item.findViewById(R.id.buttonSCResetBlue);
            cancelBlue.setOnClickListener(v -> {
                player.setBlue(0);
                blue.setText(String.valueOf(player.getBlue()));
            });

            // Зелёный блок
            TextView green = item.findViewById(R.id.TextViewSCGreen);

            Button greenInc = item.findViewById(R.id.mtgSCIncGreen);
            greenInc.setOnClickListener(v -> {
                player.incGreen();
                green.setText(String.valueOf(player.getGreen()));
            });
            Button greenDec = item.findViewById(R.id.mtgSCDecGreen);
            greenDec.setOnClickListener(v -> {
                player.decGreen();
                green.setText(String.valueOf(player.getGreen()));
            });
            Button cancelGreen = item.findViewById(R.id.buttonSCResetGreen);
            cancelGreen.setOnClickListener(v -> {
                player.setGreen(0);
                green.setText(String.valueOf(player.getGreen()));
            });

            // Красный блок
            TextView red = item.findViewById(R.id.TextViewSCRed);

            Button redInc = item.findViewById(R.id.mtgSCIncRed);
            redInc.setOnClickListener(v -> {
                player.incRed();
                red.setText(String.valueOf(player.getRed()));
            });
            Button redDec = item.findViewById(R.id.mtgSCDecRed);
            redDec.setOnClickListener(v -> {
                player.decRed();
                red.setText(String.valueOf(player.getRed()));
            });
            Button cancelRed = item.findViewById(R.id.buttonSCResetRed);
            cancelRed.setOnClickListener(v -> {
                player.setRed(0);
                red.setText(String.valueOf(player.getRed()));
            });

            // Бесцветный> блок
            TextView colorless = item.findViewById(R.id.TextViewSCColorless);

            Button colorlessInc = item.findViewById(R.id.mtgSCIncColorless);
            colorlessInc.setOnClickListener(v -> {
                player.incColorless();
                colorless.setText(String.valueOf(player.getColorless()));
            });
            Button colorlessDec = item.findViewById(R.id.mtgSCDecColorless);
            colorlessDec.setOnClickListener(v -> {
                player.decColorless();
                colorless.setText(String.valueOf(player.getColorless()));
            });
            Button cancelColorless = item.findViewById(R.id.buttonSCResetColorless);
            cancelColorless.setOnClickListener(v -> {
                player.setColorless(0);
                colorless.setText(String.valueOf(player.getColorless()));
            });
        }

        expListAdapter = new MainMtgAdapter(this, playersList);
    }


}
