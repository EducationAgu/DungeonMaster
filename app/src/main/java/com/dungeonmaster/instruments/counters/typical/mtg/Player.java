package com.dungeonmaster.instruments.counters.typical.mtg;

public class Player {
    private String name;
    private int health;
    private int inflect;
    private int cardsPlayed;
    private int white;
    private int black;
    private int blue;
    private int green;
    private int red;
    private int noColor;

    public Player(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getInflect() {
        return inflect;
    }

    public void setInflect(int inflect) {
        this.inflect = inflect;
    }

    public int getCardsPlayed() {
        return cardsPlayed;
    }

    public void setCardsPlayed(int cardsPlayed) {
        this.cardsPlayed = cardsPlayed;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }

    public int getBlack() {
        return black;
    }

    public void setBlack(int black) {
        this.black = black;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getNoColor() {
        return noColor;
    }

    public void setNoColor(int noColor) {
        this.noColor = noColor;
    }

    public void incHealth() {
        health++;
    }

    public void decHealth() {
        if (health>0) {
            health--;
        }
    }
}
