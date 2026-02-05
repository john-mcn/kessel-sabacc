package com.johnm.sabacc.server.dto.game;

import com.johnm.sabacc.server.domain.game.GameAction;

public class ActionRequestDTO {
    private String playerName;
    private String action;
    // Attributes depending on action
    private Integer tokenIndex; //index in a player's tokens list to play
    private Integer selectedValue; // number selected for, say, rolling Imposter dice

    public ActionRequestDTO() {}

    public ActionRequestDTO(String playerName, String action) {
        this.playerName = playerName;
        this.action = action;
    }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getAction() { return action; }
    public GameAction getActionEnum() { return GameAction.fromString(action); }
    public void setAction(String action) { this.action = action; }

    public Integer getTokenIndex() { return tokenIndex; }
    public void setTokenIndex(Integer tokenIndex) { this.tokenIndex = tokenIndex; }

    public Integer getSelectedValue() { return selectedValue; }
    public void setSelectedValue(Integer selectedValue) { this.selectedValue = selectedValue; }
}
