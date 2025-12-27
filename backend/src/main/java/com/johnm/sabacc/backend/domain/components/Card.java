package com.johnm.sabacc.backend.domain.components;

public class Card {
    public enum CardFamily {BLOOD, SAND}
    public enum CardRank {ONE, TWO, THREE, FOUR, FIVE, SIX, SYLOP, IMPOSTER}

    private CardFamily family;
    private CardRank rank;

    public Card(CardFamily family, CardRank rank) {
        this.family = family;
        this.rank = rank;
    }

    public CardFamily getFamily() { return family; }
    public void setFamily(CardFamily family) { this.family = family; }

    public CardRank getRank() { return rank; }
    public void setRank(CardRank rank) { this.rank = rank; }

    @Override
    public String toString() {
        return family + " " + rank;
    }
}
