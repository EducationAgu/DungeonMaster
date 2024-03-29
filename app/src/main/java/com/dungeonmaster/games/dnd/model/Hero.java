package com.dungeonmaster.games.dnd.model;

import java.util.ArrayList;

public class Hero {

    public ArrayList<Ability> abilities;
    public CharacterInfo info;
    public Characteristics characteristics;
    public DeathSaves deathSaves;
    public ArrayList<Equipment> equipment;
    public ArrayList<SavingThrow> savingThrows;
    public ArrayList<Skill> skills;


    public CharacterInfo getInfo() {
        return info;
    }

    public void setInfo(CharacterInfo info) {
        this.info = info;
    }

    public Characteristics getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(Characteristics characteristics) {
        this.characteristics = characteristics;
    }

    public DeathSaves getDeathSaves() {
        return deathSaves;
    }

    public void setDeathSaves(DeathSaves deathSaves) {
        this.deathSaves = deathSaves;
    }

    public ArrayList<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(ArrayList<Ability> abilities) {
        this.abilities = abilities;
    }

    public ArrayList<SavingThrow> getSavingThrows() {
        return savingThrows;
    }

    public void setSavingThrows(ArrayList<SavingThrow> savingThrows) {
        this.savingThrows = savingThrows;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }
}
