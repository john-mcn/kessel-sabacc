package com.johnm.sabacc.backend.domain;

import com.johnm.sabacc.backend.domain.components.ShiftToken;

import java.util.List;

public class Player extends Person {
    private PlayerHand hand;
    private ShiftToken[] selectedTokens;

    public Player(String name, int credits, List<ShiftToken> tokens, PlayerHand hand, ShiftToken[] selectedTokens) {
        super(name, credits, tokens);
        this.hand = hand;
        this.selectedTokens = selectedTokens;
    }

    public void setHand(PlayerHand hand) { this.hand = hand; }
    public PlayerHand getHand() { return hand; }

    public ShiftToken[] getSelectedTokens() { return selectedTokens; }
    public void setSelectedTokens(ShiftToken[] selectedTokens) { this.selectedTokens = selectedTokens; }
}
