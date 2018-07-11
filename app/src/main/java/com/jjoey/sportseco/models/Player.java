package com.jjoey.sportseco.models;

public class Player {

    public String playerId;
    public String playerName;
    public boolean isPresent;

    public Player() {
    }

    public Player(String playerId, String playerName, boolean isPresent) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.isPresent = isPresent;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }
}
