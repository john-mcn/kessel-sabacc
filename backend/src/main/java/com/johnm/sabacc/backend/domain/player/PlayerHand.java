package com.johnm.sabacc.backend.domain.player;

import com.johnm.sabacc.backend.domain.components.Card;
import com.johnm.sabacc.backend.domain.components.CardFamily;
import com.johnm.sabacc.backend.domain.components.CardRank;

public class PlayerHand {
    private Card bloodCard;
    private Card sandCard;

    public PlayerHand(Card bloodCard, Card sandCard) {
        if (bloodCard.isBlood() && sandCard.isSand()) {
            this.bloodCard = bloodCard;
            this.sandCard = sandCard;
        } else {
            System.err.println("WRONG FAMILIES");
        }
    }

    public PlayerHand(CardRank bloodRank, CardRank sandRank) {
        bloodCard = new Card(CardFamily.BLOOD, bloodRank);
        sandCard = new Card(CardFamily.SAND, sandRank);
    }

    public Card getBloodCard() { return bloodCard; }
    public void setBloodCard(CardRank rank) {
        bloodCard = new Card(CardFamily.BLOOD, rank);
    }

    public Card getSandCard() { return sandCard; }
    public void setSandCard(CardRank rank) {
        sandCard = new Card(CardFamily.SAND, rank);
    }

    // Change the existing card of the matching family to the new card, and return the previous card
    public Card swapCard(Card newCard) {
        Card prevCard = null;

        switch (newCard.getFamily()) {
            case BLOOD:
                prevCard = bloodCard;
                setBloodCard(newCard.getRank());
                break;
            case SAND:
                prevCard = sandCard;
                setSandCard(newCard.getRank());
                break;
            default:
                System.err.println("NOT SAND OR BLOOD CARD");
                break;
        }

        return prevCard;
    }

    @Override
    public String toString() {
        return "{" + bloodCard + ", " + sandCard + "}";
    }
}
