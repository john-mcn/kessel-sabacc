package com.johnm.sabacc.backend.domain;

import com.johnm.sabacc.backend.domain.components.Card;

public class PlayerHand {
    private Card bloodCard;
    private Card sandCard;

    public PlayerHand(Card bloodCard, Card sandCard) {
        if (bloodCard.getFamily().equals(Card.CardFamily.BLOOD) && sandCard.getFamily().equals(Card.CardFamily.SAND)) {
            this.bloodCard = bloodCard;
            this.sandCard = sandCard;
        } else {
            System.err.println("WRONG FAMILIES");
        }
    }

    public Card getBloodCard() { return bloodCard; }
    public void setBloodCard(Card bloodCard) {
        if (sandCard.getFamily().equals(Card.CardFamily.BLOOD)) {
            System.err.println("SAND CARD CANNOT BE SET AS BLOOD CARD");
        }
        this.bloodCard = bloodCard;
    }

    public Card getSandCard() { return sandCard; }
    public void setSandCard(Card sandCard) {
        if (sandCard.getFamily().equals(Card.CardFamily.BLOOD)) {
            System.err.println("BLOOD CARD CANNOT BE SET AS SAND CARD");
        }
        this.sandCard = sandCard;
    }

    // Change the existing card of the matching family to the new card, and return the previous card
    public Card swapCard(Card newCard) {
        Card prevCard = null;

        switch (newCard.getFamily()) {
            case BLOOD:
                prevCard = bloodCard;
                setBloodCard(newCard);
                break;
            case SAND:
                prevCard = sandCard;
                setSandCard(newCard);
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
