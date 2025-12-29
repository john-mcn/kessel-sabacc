package com.johnm.sabacc.backend.domain.player;

import com.johnm.sabacc.backend.domain.components.ShiftToken;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;

import java.util.Arrays;
import java.util.List;

public class Player extends Person {
    private PlayerHand hand;
    private ShiftToken[] selectedTokens;
    private int stock, pot;

    public Player(String name, int credits, List<ShiftToken> tokens, PlayerHand hand, ShiftToken[] selectedTokens) {
        super(name, credits, tokens);
        this.hand = hand;
        this.selectedTokens = selectedTokens;
        stock = 0; pot = 0;
    }

    public void setHand(PlayerHand hand) { this.hand = hand; }
    public PlayerHand getHand() { return hand; }

    public ShiftToken[] getSelectedTokens() { return selectedTokens; }
    public void setSelectedTokens(ShiftToken[] selectedTokens) { this.selectedTokens = selectedTokens; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getPot() { return pot; }
    public void setPot(int pot) { this.pot = pot; }

    // Remove 1 chip from stock and add it to pot
    public void spendChip() {
        if (stock > 0) {
            stock--;
            pot++;
        } else {
            throw new IllegalActionException("INSUFFICIENT CHIPS");
        }
    }

    @Override
    public String toString() {
        return "'" + name
                + "', hand=" + hand
                + ", chips={stock=" +  stock + " pot=" + pot
                + "}, tokens=" + (tokens != null ? Arrays.toString(selectedTokens) : "[]") + " ";
    }
}
