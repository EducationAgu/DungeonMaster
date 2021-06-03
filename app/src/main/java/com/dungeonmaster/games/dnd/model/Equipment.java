package com.dungeonmaster.games.dnd.model;

public class Equipment {

    private String name;
    private String desc;
    private boolean weared;

    public Equipment() {}

    public Equipment(String name, String desc, boolean weared){
        this.name = name;
        this.desc = desc;
        this.weared = weared;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isWeared() {
        return weared;
    }

    public void setWeared(boolean weared) {
        this.weared = weared;
    }
}
