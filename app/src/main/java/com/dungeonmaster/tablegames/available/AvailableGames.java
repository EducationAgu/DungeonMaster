package com.dungeonmaster.tablegames.available;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import com.dungeonmaster.R;
import com.dungeonmaster.instruments.counters.typical.MagicTheGathering;
import com.dungeonmaster.instruments.counters.typical.mtg.PlayField;
import com.google.gson.Gson;
import com.menu.MenuBar;
import com.serverconnection.Server;
import com.serverconnection.URLs;
import com.serverconnection.model.GameProgress;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class AvailableGames extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_games);

        Server.passRequest(HttpMethod.GET, URLs.SAVES_LIST, "");
        ResponseEntity<String> response = Server.getQueryResult(URLs.SAVES_LIST);
        if (response.getStatusCode() == HttpStatus.OK) {
            Gson gson = new Gson();
            GameProgress[] gps;
            String body = response.getBody();
            gps = gson.fromJson(body, GameProgress[].class);
            for(int i = 0; i < gps.length; i++){
                LinearLayout ll = findViewById(R.id.savesList);

                Button btn = new Button(this);
                LinearLayout.LayoutParams innerLl =  new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                innerLl.setMargins(0, 10,0,0);
                btn.setLayoutParams(innerLl);
                btn.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_app_form));
                btn.setText(gps[i].getShowName() + " " + gps[i].getDateLastChange());


                ll.addView(btn);

                String name = gps[i].getGameName();
                Long id = gps[i].getId();
                String payload = gps[i].getPayload();

                btn.setOnClickListener(v -> {
                   switch (name){
                       case PlayField.GAME_NAME:
                          Intent intent = new Intent(this, PlayField.class);
                          intent.putExtra("AllTheData",  payload);
                           intent.putExtra("id",  id);

                          startActivity(intent);
                          overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                       break;
                   }

                });
            }
        }
    }
}