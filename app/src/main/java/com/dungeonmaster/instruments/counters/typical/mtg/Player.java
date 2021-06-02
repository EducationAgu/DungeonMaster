package com.dungeonmaster.instruments.counters.typical.mtg;

public class Player {
    private String name;
    private int health;
    private int infect;
    private int cardsPlayed;
    private int white;
    private int black;
    private int blue;
    private int green;
    private int red;
    private int colorless;

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

    public int getInfect() {
        return infect;
    }

    public void setInfect(int infect) {
        this.infect = infect;
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

    public int getColorless() {
        return colorless;
    }

    public void setColorless(int noColor) {
        this.colorless = noColor;
    }

    public void incHealth() {
        health++;
    }
    public void decHealth() {
        if (health > 0) {
            health--;
        }
    }

    public void incInfect() {
        infect++;
    }
    public void decInfect() {
        if (infect > 0){
            infect--;
        }
    }

    public void incCardsPlayed() {
        cardsPlayed++;
    }
    public void decCardsPlayed() {
        if (cardsPlayed > 0){
            cardsPlayed--;
        }
    }

    public void incWhite() {
        white++;
    }
    public void decWhite() {
        if (white > 0){
            white--;
        }
    }

    public void incBlack() {
        black++;
    }
    public void decBlack() {
        if (black > 0){
            black--;
        }
    }

    public void incBlue() {
        blue++;
    }
    public void decBlue() {
        if (blue > 0){
            blue--;
        }
    }

    public void incGreen() {
        green++;
    }
    public void decGreen() {
        if (green > 0){
            green--;
        }
    }

    public void incRed() {
        red++;
    }
    public void decRed() {
        if (red > 0){
            red--;
        }
    }

    public void incColorless() {
        colorless++;
    }
    public void decColorless() {
        if (colorless > 0){
            colorless--;
        }
    }

    public void reset() {
       health = 0;
       infect = 0;
       cardsPlayed = 0;
       white = 0;
       black = 0;
       blue = 0;
       green = 0;
       red = 0;
       colorless = 0;
    }
}
