package com.dungeonmaster.games.dnd.character;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dungeonmaster.ApplicationVariables;
import com.dungeonmaster.R;
import com.dungeonmaster.games.dnd.CreateCharacter;
import com.dungeonmaster.games.dnd.model.Ability;
import com.dungeonmaster.games.dnd.model.CharacterInfo;
import com.dungeonmaster.games.dnd.model.Hero;
import com.menu.MenuBar;

import java.util.ArrayList;

public class Abilities extends MenuBar {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abilities);

        Hero hero = ((ApplicationVariables) getApplicationContext()).hero;
        hero.abilities = new ArrayList<>();
        ((ApplicationVariables) getApplicationContext()).hero = hero;
    }

    public void onClickAddAbility(View view) {

        Hero hero = ((ApplicationVariables) getApplicationContext()).hero;

        hero.abilities.add(new Ability(
                ((TextView) findViewById(R.id.attacksNSpells)).getText().toString(),
                ((TextView) findViewById(R.id.skillsNAbilities)).getText().toString(),
                ((TextView) findViewById(R.id.OtherPossessionsNSkills)).getText().toString()
        ));

        ((ApplicationVariables) getApplicationContext()).hero = hero;

        setNextScreen(CreateCharacter.class, TO_RIGHT);
    }

}
