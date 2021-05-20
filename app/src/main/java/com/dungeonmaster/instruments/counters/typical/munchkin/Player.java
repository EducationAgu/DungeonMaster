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

    public void incLevel() {
        if (level < 100) {
            level++;
        }
    }

    public void decLevel() {
        if (level > 0) {
            level--;
        }
    }

    public void incEquipment() {
        if (equipment < 100) {
            equipment++;
        }
    }
    public void decEquipment() {
        if (equipment > 0) {
            equipment--;
        }
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
