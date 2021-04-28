package com.dungeonmaster.instruments.counters.typical.munchkin;

public class Player {
    private String name;
    private int level;
    private int equipment;

    public Player(String name) {
        this.name = name;
        level = 0;
        equipment = 0;
    }

    public int getPowerAmount() {
        return level + equipment;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setEquipment(int equipment) {
        this.equipment = equipment;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getEquipment() {
        return equipment;
    }
}
