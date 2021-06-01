package com.dungeonmaster.games.dnd.model;

public class Abilities {

    private String attacksAndSpells;
    private String featuresAndTraits;
    private String proficienciesAndLanguages;

    public Abilities() {}

    public Abilities(String attacksAndSpells, String featuresAndTraits,
                     String proficienciesAndLanguages){
        this.attacksAndSpells = attacksAndSpells;
        this.featuresAndTraits = featuresAndTraits;
        this.proficienciesAndLanguages = proficienciesAndLanguages;
    }

    public String getAttacksAndSpells() {
        return attacksAndSpells;
    }

    public void setAttacksAndSpells(String attacksAndSpells) {
        this.attacksAndSpells = attacksAndSpells;
    }

    public String getFeaturesAndTraits() {
        return featuresAndTraits;
    }

    public void setFeaturesAndTraits(String featuresAndTraits) {
        this.featuresAndTraits = featuresAndTraits;
    }

    public String getProficienciesAndLanguages() {
        return proficienciesAndLanguages;
    }

    public void setProficienciesAndLanguages(String proficienciesAndLanguages) {
        this.proficienciesAndLanguages = proficienciesAndLanguages;
    }
}
