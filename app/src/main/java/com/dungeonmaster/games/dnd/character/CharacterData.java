package com.dungeonmaster.games.dnd.character;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dungeonmaster.ApplicationVariables;
import com.dungeonmaster.R;
import com.dungeonmaster.games.dnd.CreateCharacter;
import com.dungeonmaster.games.dnd.DnDHelper;
import com.dungeonmaster.games.dnd.model.CharacterInfo;
import com.dungeonmaster.games.dnd.model.Hero;
import com.menu.MenuBar;
import com.rengwuxian.materialedittext.MaterialEditText;

public class CharacterData extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_data);

        Hero hero = ((ApplicationVariables) getApplicationContext()).hero;
        hero.info = new CharacterInfo();
        ((ApplicationVariables) getApplicationContext()).hero = hero;
    }

    public void onClickAddCharacterData(View View) {
        Hero hero = ((ApplicationVariables) getApplicationContext()).hero;

        hero.info.setPlayerName(((MaterialEditText) findViewById(R.id.dndPlayerName)).getText().toString());
        hero.info.setCharacterName(((MaterialEditText) findViewById(R.id.dndCharacterName)).getText().toString());
        hero.info.setCharacterClass(((MaterialEditText) findViewById(R.id.dndCharacterClass)).getText().toString());
        hero.info.setRace(((MaterialEditText) findViewById(R.id.dndRace)).getText().toString());
        hero.info.setCharacterBackground(((MaterialEditText) findViewById(R.id.dndPrehistory)).getText().toString());

        hero.info.setIdeology(((MaterialEditText) findViewById(R.id.dndCharIdeology)).getText().toString());

        hero.info.setPersonalityTraits(((MaterialEditText) findViewById(R.id.dndCharacterTraits)).getText().toString());
        hero.info.setIdeals(((MaterialEditText) findViewById(R.id.dndCharacterIdeals)).getText().toString());

        hero.info.setBonds(((MaterialEditText) findViewById(R.id.dndCharAffections)).getText().toString());

        hero.info.setFlaws(((MaterialEditText) findViewById(R.id.dndCharWeaknesses)).getText().toString());

        hero.info.setLevel(Integer.parseInt(((TextView) findViewById(R.id.dndLevel)).getText().toString()));
        hero.info.setExp(Integer.parseInt(((TextView) findViewById(R.id.expPoints)).getText().toString()));

        ((ApplicationVariables) getApplicationContext()).hero = hero;
    }

    public void onClickIncDndLevel(View view) {
        Hero hero = ((ApplicationVariables) getApplicationContext()).hero;
        hero.info.incLevel();

        TextView tv = findViewById(R.id.dndLevel);
        tv.setText(String.valueOf(hero.info.getLevel()));
    }

    public void onClickDecDndLevel(View view) {
        Hero hero = ((ApplicationVariables) getApplicationContext()).hero;
        hero.info.decLevel();

        TextView tv = findViewById(R.id.dndLevel);
        tv.setText(String.valueOf(hero.info.getLevel()));
    }

    public void onClickIncDndExp(View view) {
        Hero hero = ((ApplicationVariables) getApplicationContext()).hero;
        hero.info.incExp();

        TextView tv = findViewById(R.id.expPoints);
        tv.setText(String.valueOf(hero.info.getExp()));
    }

    public void onClickDecDndExp(View view) {
        Hero hero = ((ApplicationVariables) getApplicationContext()).hero;
        hero.info.decExp();

        TextView tv = findViewById(R.id.expPoints);
        tv.setText(String.valueOf(hero.info.getExp()));
    }

    @Override
    public void onBackPressed() {
        setNextScreen(CreateCharacter.class, TO_RIGHT);
    }
}
