package com.dungeonmaster.games.dnd.model;

public class Skills {

    private int acrobatics;
    private int investigation;
    private int athletics;
    private int perception;
    private int survival;
    private int performance;
    private int intimidation;
    private int history;
    private int sleightOfHand;
    private int arcana;
    private int medicine;
    private int deception;
    private int nature;
    private int insight;
    private int religion;
    private int stealth;
    private int persuasion;
    private int animalHandling;

    public Skills() {}

    public Skills(int acrobatics, int investigation, int athletics, int perception, int survival,
                  int performance, int intimidation, int history, int sleightOfHand, int arcana,
                  int medicine, int deception, int nature, int insight, int religion, int stealth,
                  int persuasion, int animalHandling) {
        this.acrobatics = acrobatics;
        this.investigation = investigation;
        this.athletics = athletics;
        this.perception = perception;
        this.survival = survival;
        this.performance = performance;
        this.intimidation = intimidation;
        this.history = history;
        this.sleightOfHand = sleightOfHand;
        this.arcana = arcana;
        this.medicine = medicine;
        this.deception = deception;
        this.nature = nature;
        this.insight = insight;
        this.religion = religion;
        this.stealth = stealth;
        this.persuasion = persuasion;
        this.animalHandling = animalHandling;
    }

    public int getAcrobatics() {
        return acrobatics;
    }

    public void setAcrobatics(int acrobatics) {
        this.acrobatics = acrobatics;
    }

    public void incAcrobatics() {
        acrobatics++;
    }

    public void decAcrobatics() {
        if (acrobatics > -11) {
            acrobatics--;
        }
    }

    public int getInvestigation() {
        return investigation;
    }

    public void setInvestigation(int investigation) {
        this.investigation = investigation;
    }

    public void incInvestigation() {
        investigation++;
    }

    public void decInvestigation() {
        if (investigation > -11) {
            investigation--;
        }
    }

    public int getAthletics() {
        return athletics;
    }

    public void setAthletics(int athletics) {
        this.athletics = athletics;
    }

    public void incAthletics() {
        athletics++;
    }

    public void decAthletics() {
        if (athletics > -11) {
            athletics--;
        }
    }

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    public void incPerception() {
        perception++;
    }

    public void decPerception() {
        if (perception > -11) {
            perception--;
        }
    }

    public int getSurvival() {
        return survival;
    }

    public void setSurvival(int survival) {
        this.survival = survival;
    }

    public void incSurvival() {
        survival++;
    }

    public void decSurvival() {
        if (survival > -11) {
            survival--;
        }
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public void incPerformance() {
        performance++;
    }

    public void decPerformance() {
        if (performance > -11) {
            performance--;
        }
    }

    public int getIntimidation() {
        return intimidation;
    }

    public void setIntimidation(int intimidation) {
        this.intimidation = intimidation;
    }

    public void incIntimidation() {
        intimidation++;
    }

    public void decIntimidation() {
        if (intimidation > -11) {
            intimidation--;
        }
    }

    public int getHistory() {
        return history;
    }

    public void setHistory(int history) {
        this.history = history;
    }

    public void incHistory() {
        history++;
    }

    public void decHistory() {
        if (history > -11) {
            history--;
        }
    }

    public int getSleightOfHand() {
        return sleightOfHand;
    }

    public void setSleightOfHand(int sleightOfHand) {
        this.sleightOfHand = sleightOfHand;
    }

    public void incSleightOfHand() {
        sleightOfHand++;
    }

    public void decSleightOfHand() {
        if (sleightOfHand > -11) {
            sleightOfHand--;
        }
    }

    public int getArcana() {
        return arcana;
    }

    public void setArcana(int arcana) {
        this.arcana = arcana;
    }

    public void incArcana() {
        arcana++;
    }

    public void decArcana() {
        if (arcana > -11) {
            arcana--;
        }
    }

    public int getMedicine() {
        return medicine;
    }

    public void setMedicine(int medicine) {
        this.medicine = medicine;
    }

    public void incMedicine() {
        medicine++;
    }

    public void decMedicine() {
        if (medicine > -11) {
            medicine--;
        }
    }

    public int getDeception() {
        return deception;
    }

    public void setDeception(int deception) {
        this.deception = deception;
    }

    public void incDeception() {
        deception++;
    }

    public void decDeception() {
        if (deception > -11) {
            deception--;
        }
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }

    public void incNature() {
        nature++;
    }

    public void decNature() {
        if (nature > -11) {
            nature--;
        }
    }

    public int getInsight() {
        return insight;
    }

    public void setInsight(int insight) {
        this.insight = insight;
    }

    public void incInsight() {
        insight++;
    }

    public void decInsight() {
        if (insight > -11) {
            insight--;
        }
    }

    public int getReligion() {
        return religion;
    }

    public void setReligion(int religion) {
        this.religion = religion;
    }

    public void incReligion() {
        religion++;
    }

    public void decReligion() {
        if (religion > -11) {
            religion--;
        }
    }

    public int getStealth() {
        return stealth;
    }

    public void setStealth(int stealth) {
        this.stealth = stealth;
    }

    public void incStealth() {
        stealth++;
    }

    public void decStealth() {
        if (stealth > -11) {
            stealth--;
        }
    }

    public int getPersuasion() {
        return persuasion;
    }

    public void setPersuasion(int persuasion) {
        this.persuasion = persuasion;
    }

    public void incPersuasion() {
        persuasion++;
    }

    public void decPersuasion() {
        if (persuasion > -11) {
            persuasion--;
        }
    }

    public int getAnimalHandling() {
        return animalHandling;
    }

    public void setAnimalHandling(int animalHandling) {
        this.animalHandling = animalHandling;
    }

    public void incAnimalHandling() {
        animalHandling++;
    }

    public void decAnimalHandling() {
        if (animalHandling > -11) {
            animalHandling--;
        }
    }
}
