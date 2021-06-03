package com.dungeonmaster.tablegames;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.typical.mtg.PlayField;
import com.dungeonmaster.menu.MainMenu;
import com.google.gson.Gson;
import com.menu.MenuBar;
import com.serverconnection.Server;
import com.serverconnection.URLs;
import com.serverconnection.model.GameRule;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GameListActivity extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_games);

        Server.passRequest(HttpMethod.GET, URLs.TABLE_GAMES_LIST, "");
        ResponseEntity<String> response = Server.getQueryResult(URLs.TABLE_GAMES_LIST);

        if (response.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            GameRule[] gr = gson.fromJson(response.getBody(), GameRule[].class);

            LinearLayout ll = findViewById(R.id.savesList);
            for(int i = 0; i < gr.length; i++ ) {

                Button btn = new Button(this);
                LinearLayout.LayoutParams innerLl =  new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

                innerLl.setMargins(0, 10,0,0);

                btn.setLayoutParams(innerLl);
                btn.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_borderless));
                btn.setText(gr[i].name);
                GameRule gameRule = gr[i];
                btn.setOnClickListener(v -> {
                    Intent intent = new Intent(this, GamesInfoActivity.class);
                    intent.putExtra("AllTheData", gson.toJson(gameRule));

                    startActivity(intent);

                    overridePendingTransition(TO_LEFT[0], TO_LEFT[1]);
                });

                ll.addView(btn);


            }
        } else {
            Toast.makeText(this, "Ошибка соединения с сервером", Toast.LENGTH_LONG).show();
            setNextScreen(MainMenu.class, TO_RIGHT);
        }
    }
}
