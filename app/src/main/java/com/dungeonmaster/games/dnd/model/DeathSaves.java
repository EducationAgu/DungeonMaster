package com.dungeonmaster.games.dnd.model;

public class DeathSaves {

    private int successes;
    private int failures;

    public DeathSaves() {}

    public DeathSaves(int successes, int failures){
        this.successes = successes;
        this.failures = failures;
    }

    public int getSuccesses() {
        return successes;
    }

    public void setSuccesses(int successes) {
        this.successes = successes;
    }

    public void incSuccesses() {
        if (successes < 3){
            successes++;
        }
    }

    public void decSuccesses() {
        if (successes > 0) {
            successes--;
        }
    }

    public int getFailures() {
        return failures;
    }

    public void setFailures(int failures) {
        this.failures = failures;
    }

    public void incFailures() {
        if (failures < 3){
            failures++;
        }
    }

    public void decFailures() {
        if (failures > 0) {
            failures--;
        }
    }
}
