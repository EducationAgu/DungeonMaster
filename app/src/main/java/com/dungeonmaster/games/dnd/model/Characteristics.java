package com.dungeonmaster.games.dnd.model;

public class Characteristics {

    private int currentHP;
    private int temporaryHP;
    private String armorClass;
    private int initiative;
    private int speed;
    private int inspiration;
    private int proficiencyBonus;
    private SavingThrow savingThrow;
    private Skill skill;
    private int passiveWisdom;
    private String hitDice;
    private DeathSaves deathSaves;

    public Characteristics() {}

    public Characteristics(int currentHP, int temporaryHP, String armorClass, int initiative,
                           int speed, int inspiration, int proficiencyBonus,
                           SavingThrow savingThrow, Skill skill, int passiveWisdom,
                           String hitDice, DeathSaves deathSaves) {
        this.currentHP = currentHP;
        this.temporaryHP = temporaryHP;
        this.armorClass = armorClass;
        this.initiative = initiative;
        this.speed = speed;
        this.inspiration = inspiration;
        this.proficiencyBonus = proficiencyBonus;
        this.savingThrow = savingThrow;
        this.skill = skill;
        this.passiveWisdom = passiveWisdom;
        this.hitDice = hitDice;
        this.deathSaves = deathSaves;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public void setCurrentHP(int currentHP) {
        this.currentHP = currentHP;
    }

    public void incCurrentHP() {
        currentHP++;
    }

    public void decCurrentHP() {
        if (currentHP > -1) {
            currentHP--;
        }
    }

    public int getTemporaryHP() {
        return temporaryHP;
    }

    public void setTemporaryHP(int temporaryHP) {
        this.temporaryHP = temporaryHP;
    }

    public void incTemporaryHP() {
        temporaryHP++;
    }

    public void decTemporaryHP() {
        if (temporaryHP > -1) {
            temporaryHP--;
        }
    }

    public String getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(String armorClass) {
        this.armorClass = armorClass;
    }

    public int getInitiative() {
        return initiative;
    }

    public void setInitiative(int initiative) {
        this.initiative = initiative;
    }

    public void incInitiative() {
        initiative++;
    }

    public void decInitiative() {
        if (initiative > -11) {
            initiative--;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void incSpeed() {
        speed++;
    }

    public void decSpeed() {
        if (speed > 0) {
            speed--;
        }
    }

    public int getInspiration() {
        return inspiration;
    }

    public void setInspiration(int inspiration) {
        this.inspiration = inspiration;
    }

    public void incInspiration() {
        inspiration++;
    }

    public void decInspiration() {
        if (inspiration > -11) {
            inspiration--;
        }
    }

    public int getProficiencyBonus() {
        return proficiencyBonus;
    }

    public void setProficiencyBonus(int proficiencyBonus) {
        this.proficiencyBonus = proficiencyBonus;
    }

    public void incProficiencyBonus() {
        proficiencyBonus++;
    }

    public void decProficiencyBonus() {
        if (proficiencyBonus > -11) {
            proficiencyBonus--;
        }
    }

    public SavingThrow getSavingThrows() {
        return savingThrow;
    }

    public void setSavingThrows(SavingThrow savingThrow) {
        this.savingThrow = savingThrow;
    }

    public Skill getSkills() {
        return skill;
    }

    public void setSkills(Skill skill) {
        this.skill = skill;
    }

    public int getPassiveWisdom() {
        return passiveWisdom;
    }

    public void setPassiveWisdom(int passiveWisdom) {
        this.passiveWisdom = passiveWisdom;
    }

    public void incPassiveWisdom() {
        passiveWisdom++;
    }

    public void decPassiveWisdom() {
        if (passiveWisdom > -1) {
            passiveWisdom--;
        }
    }

    public String getHitDice() {
        return hitDice;
    }

    public void setHitDice(String hitDice) {
        this.hitDice = hitDice;
    }

    public DeathSaves getDeathSaves() {
        return deathSaves;
    }

    public void setDeathSaves(DeathSaves deathSaves) {
        this.deathSaves = deathSaves;
    }
}
