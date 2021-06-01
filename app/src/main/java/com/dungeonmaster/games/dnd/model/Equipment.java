package com.dungeonmaster.games.dnd.model;

public class Equipment {

    private String listOfEquipment;

    public Equipment() {}

    public Equipment(String listOfEquipment){
        this.listOfEquipment = listOfEquipment;
    }

    public String getListOfEquipment() {
        return listOfEquipment;
    }

    public void setListOfEquipment(String listOfEquipment) {
        this.listOfEquipment = listOfEquipment;
    }
}
