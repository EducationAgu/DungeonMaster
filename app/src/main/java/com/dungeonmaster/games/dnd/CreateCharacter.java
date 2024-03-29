package com.dungeonmaster.games.dnd;

import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.ApplicationVariables;
import com.dungeonmaster.R;
import com.dungeonmaster.games.dnd.character.Abilities;
import com.dungeonmaster.games.dnd.character.CharacterData;
import com.dungeonmaster.games.dnd.character.Characteristics;
import com.dungeonmaster.games.dnd.character.EquipmentActivity;
import com.dungeonmaster.games.dnd.model.Hero;
import com.menu.MenuBar;

public class CreateCharacter extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_character);
        ((ApplicationVariables) getApplicationContext()).hero = new Hero();
    }

    public void onClickOpenCharacterData(View view){
        setNextScreen(CharacterData.class, TO_LEFT);
    }

    public void onClickOpenCharacteristic(View view){
        setNextScreen(Characteristics.class, TO_LEFT);
    }

    public void onClickOpenAbilities(View view){
        setNextScreen(Abilities.class, TO_LEFT);
    }

    public void onClickOpenEquipment(View view){
        setNextScreen(EquipmentActivity.class, TO_LEFT);
    }

    @Override
    public void onBackPressed() {
        ((ApplicationVariables) getApplicationContext()).hero = null;
        setNextScreen(DnDHelper.class, TO_LEFT);
    }
}