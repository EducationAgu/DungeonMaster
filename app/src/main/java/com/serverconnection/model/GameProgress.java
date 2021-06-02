package com.serverconnection.model;

import com.dungeonmaster.instruments.counters.typical.Munchkin;
import com.dungeonmaster.instruments.counters.typical.mtg.PlayField;


public class GameProgress {
    private Long id;
    private String gameName;
    private String payload;

    private String dateCreated;
    private String dateLastChange;

    public String getGameName() {
        return gameName;
    }

    public String getShowName(){
        switch (gameName) {
            case PlayField.GAME_NAME:
                return "Magic The Gathering";
            case Munchkin.GAME_NAME:
                return "Munchkin";
        }
        return "";
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDateLastChange() {
        return dateLastChange;
    }

    public void setDateLastChange(String dateLastChange) {
        this.dateLastChange = dateLastChange;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
