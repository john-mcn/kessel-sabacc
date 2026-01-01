package com.johnm.sabacc.backend.dto.game;

import com.johnm.sabacc.backend.domain.game.GameAction;

public class ActionRequestDTO {
    private String playerName;
    private String action;
    // Attributes depending on action
    private boolean replaceCard; // whether to replace a drawn card with a hand card
    private Integer tokenIndex; //index in a player's tokens list to play

    public ActionRequestDTO(String playerName, String action) {
        this.playerName = playerName;
        this.action = action;
    }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public String getAction() { return action; }
    public GameAction getActionEnum() { return GameAction.fromString(action); }
    public void setAction(String action) { this.action = action; }

    public boolean getReplaceCard() { return replaceCard; }
    public void setReplaceCard(boolean replaceCard) { this.replaceCard = replaceCard; }

    public Integer getTokenIndex() { return tokenIndex; }
    public void setTokenIndex(Integer tokenIndex) { this.tokenIndex = tokenIndex; }
}
