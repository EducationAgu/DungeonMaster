package com.dungeonmaster.games.dnd.model;

public class CharacterInfo {

    private String playerName;
    private String characterName;
    private String characterClass;
    private String race;
    private String characterBackground;
    private int level;
    private int exp;
    private String alignment;
    private String personalityTraits;
    private String ideals;
    private String bonds;
    private String flaws;

    public CharacterInfo() {}

    public CharacterInfo(String playerName, String characterName, String characterClass,
                         String race, String characterBackground, int level, int exp, String alignment,
                         String personalityTraits, String ideals, String bonds, String flaws){
        this.playerName = playerName;
        this.characterName = characterName;
        this.characterClass = characterClass;
        this.race = race;
        this.characterBackground = characterBackground;
        this.level = level;
        this.exp = exp;
        this.alignment = alignment;
        this.personalityTraits = personalityTraits;
        this.ideals = ideals;
        this.bonds = bonds;
        this.flaws = flaws;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCharacterBackground() {
        return characterBackground;
    }

    public void setCharacterBackground(String characterBackground) {
        this.characterBackground = characterBackground;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void incLevel() {
        level++;
    }

    public void decLevel() {
        if (level > 1) {
            level--;
        }
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void incExp() {
        exp++;
    }

    public void decExp() {
        if (exp > 0) {
            exp--;
        }
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getPersonalityTraits() {
        return personalityTraits;
    }

    public void setPersonalityTraits(String personalityTraits) {
        this.personalityTraits = personalityTraits;
    }

    public String getIdeals() {
        return ideals;
    }

    public void setIdeals(String ideals) {
        this.ideals = ideals;
    }

    public String getBonds() {
        return bonds;
    }

    public void setBonds(String bonds) {
        this.bonds = bonds;
    }

    public String getFlaws() {
        return flaws;
    }

    public void setFlaws(String flaws) {
        this.flaws = flaws;
    }
}
