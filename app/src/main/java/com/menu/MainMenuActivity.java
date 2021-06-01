package com.menu;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.dungeonmaster.R;

public class MainMenuActivity extends AppCompatActivity {

    public static final int[] TO_LEFT = new int[]{R.anim.slide_in_left, R.anim.slide_out_right};
    public static final int[] TO_RIGHT = new int[]{R.anim.slide_in_right, R.anim.slide_out_left};

    public void setNextScreen(Class passedClass, int[] animation) {
        Intent nextScreen = new Intent(this, passedClass);
        startActivity(nextScreen);
        overridePendingTransition(animation[0], animation[1]);
    }
}
