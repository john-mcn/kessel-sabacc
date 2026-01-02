package com.johnm.sabacc.backend.domain.player;

import com.johnm.sabacc.backend.domain.game.components.Card;
import com.johnm.sabacc.backend.domain.game.components.CardFamily;
import com.johnm.sabacc.backend.domain.game.components.CardRank;

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

    // Sabacc if cards are the same rank, or one is a Sylop
    public boolean isSabacc() {
        return bloodCard.getRank().equals(sandCard.getRank())
                || (bloodCard.isSylop() || sandCard.isSylop());
    }

    public int rankDifference() { return bloodCard.rankDifference(sandCard); }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerHand playerHand = (PlayerHand) o;
        return playerHand.getBloodCard().equals(bloodCard) && playerHand.getSandCard().equals(sandCard);
    }
}
