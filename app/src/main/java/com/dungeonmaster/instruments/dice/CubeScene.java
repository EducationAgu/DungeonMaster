package com.dungeonmaster.instruments.dice;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class CubeScene extends Activity {

    protected float xpos = -1;
    protected float ypos = -1;

    protected float touchTurn = 0;
    protected float touchTurnUp = 0;

    protected CubeView mGLView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLView = new CubeView(this);

        setContentView(mGLView);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGLView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public boolean isTouching;
    public boolean onTouchEvent(MotionEvent me) {

        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            xpos = me.getX();
            ypos = me.getY();

            isTouching = true;
            return true;
        }

        if (me.getAction() == MotionEvent.ACTION_UP) {
            isTouching = false;
        }

        if (me.getAction() == MotionEvent.ACTION_MOVE) {
            float xd = me.getX() - xpos;
            float yd = me.getY() - ypos;

            xpos = me.getX();
            ypos = me.getY();

            touchTurn = xd / -100f;
            touchTurnUp = yd / -100f;
            return true;
        }

        try {
            Thread.sleep(15);
        } catch (Exception e) {}

        return super.onTouchEvent(me);
    }
    protected boolean isFullscreenOpaque() {
        return true;
    }
}