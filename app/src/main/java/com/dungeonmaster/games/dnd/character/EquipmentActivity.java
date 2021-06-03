package com.dungeonmaster.games.dnd.character;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.dungeonmaster.ApplicationVariables;
import com.dungeonmaster.R;
import com.dungeonmaster.games.dnd.CreateCharacter;
import com.dungeonmaster.games.dnd.model.CharacterInfo;
import com.dungeonmaster.games.dnd.model.Equipment;
import com.dungeonmaster.games.dnd.model.Hero;
import com.menu.MenuBar;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

public class EquipmentActivity extends MenuBar {

    private boolean isWeared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment);


        Hero hero = ((ApplicationVariables) getApplicationContext()).hero;
        hero.equipment = new ArrayList<>();
        ((ApplicationVariables) getApplicationContext()).hero = hero;

        Switch s = findViewById(R.id.equipmentPutOn);
        s.setOnCheckedChangeListener((buttonView, isChecked) -> isWeared = isChecked);
    }


    public void onClickAddEquipment(View View) {
        Hero hero = ((ApplicationVariables) getApplicationContext()).hero;

        hero.equipment.add(new Equipment(
                ((TextView) findViewById(R.id.equipmentName)).getText().toString(),
                ((TextView) findViewById(R.id.equipmentInfo)).getText().toString(),
                isWeared
        ));

        ((ApplicationVariables) getApplicationContext()).hero = hero;

        ((TextView) findViewById(R.id.equipmentName)).setText("");
        ((TextView) findViewById(R.id.equipmentInfo)).setText("");
    }
}
