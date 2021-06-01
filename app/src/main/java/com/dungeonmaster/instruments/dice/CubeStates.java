package com.dungeonmaster.instruments.dice;

import com.dungeonmaster.R;
import com.threed.jpct.Logger;

import java.util.Random;

public class CubeStates {
    public static final float[] ONE = new float[]{
            1,  0,  0, 0,
            0,  0,  1, 0,
            0, -1,  0, 0,
            0,  0,  0, 1};
    public static final float[] TWO = new float[]{
            0,  0,  -1, 0,
            0,  1,  0, 0,
            1, 0,  0, 0,
            0,  0,  0, 1};
    public static final float[] THREE = new float[]{
            1,  0,  0, 0,
            0, -1,  0, 0,
            0, 0,  -1, 0,
            0,  0,  0, 1};
    public static final float[] FOUR = new float[]{
            1,  0,  0, 0,
            0,  1,  0, 0,
            0,  0,  1, 0,
            0,  0,  0, 1};
    public static final float[] FIVE = new float[]{
            0,  0,  1, 0,
            0,  1,  0, 0,
            -1, 0,  0, 0,
            0,  0,  0, 1};
    public static final float[] SIX = new float[]{
            1,  0,  0, 0,
            0,  0, -1, 0,
            0,  1,  0, 0,
            0,  0,  0, 1};

    public float[][] cube;
    private Random random;
    public CubeStates() {
        random = new Random();

        cube = new float[6][16];
        cube[0] = ONE;
        cube[1] = TWO;
        cube[2] = THREE;
        cube[3] = FOUR;
        cube[4] = FIVE;
        cube[5] = SIX;
    }

    public float[] toss() {
        return cube[Math.floorDiv(random.nextInt(59),10)];
    }
}
