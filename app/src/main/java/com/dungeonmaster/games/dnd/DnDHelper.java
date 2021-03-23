package com.dungeonmaster.games.dnd;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dungeonmaster.R;

public class DnDHelper extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dn_d_helper);
    }

    public void onClickCreateCharacter(View view) {
        Intent tools = new Intent(this, CreateCharacter.class);
        startActivity(tools);
    }

    public void  onClickChooseCharacter(View view) {
//        Intent tools = new Intent(this, ChooseCharacter.class);
//        startActivity(tools);
    }
}