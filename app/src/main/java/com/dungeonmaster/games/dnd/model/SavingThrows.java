package com.dungeonmaster.games.dnd.model;

public class SavingThrows {

    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    public SavingThrows() {}

    public  SavingThrows(int strength, int dexterity, int constitution, int intelligence,
                         int wisdom, int charisma){
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void incStrength() {
        strength++;
    }

    public void decStrength() {
        if (strength > -11) {
            strength--;
        }
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void incDexterity() {
        dexterity++;
    }

    public void decDexterity() {
        if (dexterity > -11) {
            dexterity--;
        }
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public void incConstitution() {
        constitution++;
    }

    public void decConstitution() {
        if (constitution > -11) {
            constitution--;
        }
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public void incIntelligence() {
        intelligence++;
    }

    public void decIntelligence() {
        if (intelligence > -11) {
            intelligence--;
        }
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public void incWisdom() {
        wisdom++;
    }

    public void decWisdom() {
        if (wisdom > -11) {
            wisdom--;
        }
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void incCharisma() {
        charisma++;
    }

    public void decCharisma() {
        if (charisma > -11) {
            charisma--;
        }
    }
}
