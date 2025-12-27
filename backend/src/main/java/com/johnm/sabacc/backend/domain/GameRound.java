package com.johnm.sabacc.backend.domain;

import com.johnm.sabacc.backend.domain.components.Card;
import com.johnm.sabacc.backend.domain.components.ShiftToken;
import com.johnm.sabacc.backend.util.GameUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameRound {
    private Player[] players;
    private List<ShiftToken> tokensActive;
    private List<Card> bloodDiscard;
    private List<Card> sandDiscard;

    public GameRound(Player[] players) {
        this.players = players;
        tokensActive = new ArrayList<>();
        bloodDiscard = new ArrayList<>();
        sandDiscard = new ArrayList<>();
    }

    public Player[] getPlayers() { return players; }
    public void setPlayers(Player[] players) { this.players = players; }

    public List<ShiftToken> getTokensActive() { return tokensActive; }
    public void setTokensActive(List<ShiftToken> tokensActive) { this.tokensActive = tokensActive; }

    public List<Card> getBloodDiscard() { return bloodDiscard; }
    public void setBloodDiscard(List<Card> bloodDiscard) { this.bloodDiscard = bloodDiscard; }

    public List<Card> getSandDiscard() { return sandDiscard; }
    public void setSandDiscard(List<Card> sandDiscard) { this.sandDiscard = sandDiscard; }

    public void setup() {
        List<Card> fullDeck = GameUtils.fullDeck();
        Collections.shuffle(fullDeck);

        ArrayList<Card> bloodCards = new ArrayList<>(fullDeck.stream().filter(
                c -> c.getFamily().equals(Card.CardFamily.BLOOD)).toList());
        ArrayList<Card> sandCards = new ArrayList<>(fullDeck.stream().filter(
                c -> c.getFamily().equals(Card.CardFamily.SAND)).toList());

        // Give each player their starting hand, and remove it from the deck
        for (Player player : players) {
            Card bloodCard = bloodCards.remove(0);
            Card sandCard = sandCards.remove(1);
            player.setHand(new PlayerHand(bloodCard, sandCard));
        }

        bloodDiscard = bloodCards;
        sandDiscard = sandCards;
    }

    // @Override
    // public String toString() {
    //     return "";
    // }
}
