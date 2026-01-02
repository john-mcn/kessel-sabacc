package com.johnm.sabacc.backend.domain.player;

import com.johnm.sabacc.backend.domain.game.components.Card;
import com.johnm.sabacc.backend.domain.game.components.ShiftToken;
import com.johnm.sabacc.backend.dto.player.PlayerDTO;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;
import com.johnm.sabacc.backend.util.EnumUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Player extends Person {
    // private String name;
    private PlayerHand hand;
    private Card drawnCard;
    private List<ShiftToken> selectedTokens;
    private int stock, pot;

    // Full constructor, used for DTO -> entity conversion
    public Player(String name, PlayerHand hand, List<ShiftToken> selectedTokens, int stock, int pot) {
        this.name = name;
        this.hand = hand;
        this.selectedTokens = selectedTokens;
        this.stock = stock;
        this.pot = pot;
    }

    // Constructor that sets stock and pot to 0, used for initial creation
    public Player(String name, PlayerHand hand, List<ShiftToken> selectedTokens) {
        this(name, hand, selectedTokens, 0, 0);
    }

    public void setHand(PlayerHand hand) { this.hand = hand; }
    public PlayerHand getHand() { return hand; }

    public Card getDrawnCard() { return drawnCard; }
    public void setDrawnCard(Card drawnCard) { this.drawnCard = drawnCard; }

    public List<ShiftToken> getSelectedTokens() { return selectedTokens; }
    public void setSelectedTokens(List<ShiftToken> selectedTokens) { this.selectedTokens = selectedTokens; }

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

    // Remove n chips from player stock
    public int tax(int taxAmnt) {
        int amntToRemove = Math.min(taxAmnt, stock);
        stock -= amntToRemove;
        return amntToRemove;
    }

    public PlayerDTO toDTO() {
        PlayerDTO playerDTO = new PlayerDTO(
                name,
                (selectedTokens == null)? new ArrayList<>() : selectedTokens.stream().map(t -> EnumUtils.sanitiseStringFromEnum(t.name())).toList(),
                stock,
                pot
        );

        if (hand != null) { playerDTO.setHand(List.of(hand.getBloodCard().toDTO(), hand.getSandCard().toDTO())); }
        if (drawnCard != null) { playerDTO.setDrawnCard(drawnCard.toDTO()); }
        return playerDTO;
    }

    @Override
    public String toString() {
        return "'" + name
                + "', hand=" + hand
                + ", chips={stock=" +  stock + " pot=" + pot
                + "}, tokens=" + (tokens != null ? selectedTokens.toString() : "[]") + " ";
    }
}
