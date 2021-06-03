package com.dungeonmaster.tablegames;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.dungeonmaster.R;
import com.dungeonmaster.menu.MainMenu;
import com.google.gson.Gson;
import com.menu.MenuBar;
import com.serverconnection.Server;
import com.serverconnection.URLs;
import com.serverconnection.model.GameRules;

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
            GameRules[] gr = gson.fromJson(response.getBody(), GameRules[].class);

            LinearLayout ll = findViewById(R.id.savesList);
            for(int i = 0; i < gr.length; i++ ) {
//                LinearLayout innerLayout = new LinearLayout(this);
//                innerLayout.setOrientation(LinearLayout.HORIZONTAL);
//                innerLayout.setBackground(getDrawable(R.drawable.btn_app_form));

                Button btn = new Button(this);
                LinearLayout.LayoutParams innerLl =  new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);

                innerLl.setMargins(0, 10,0,0);

                btn.setLayoutParams(innerLl);
                btn.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_borderless));
                btn.setText(gr[i].name);
                ll.addView(btn);


            }
        } else {
            Toast.makeText(this, "Ошибка соединения с сервером", Toast.LENGTH_LONG).show();
            setNextScreen(MainMenu.class, TO_RIGHT);
        }
    }
}
