package com.johnm.sabacc.backend.domain.components;

public class Card {

    private CardFamily family;
    private CardRank rank;

    public Card(CardFamily family, CardRank rank) {
        this.family = family;
        this.rank = rank;
    }

    public CardFamily getFamily() { return family; }
    public void setFamily(CardFamily family) { this.family = family; }
    public boolean isSand() { return family.equals(CardFamily.SAND); }
    public boolean isBlood() { return family.equals(CardFamily.BLOOD); }

    public CardRank getRank() { return rank; }
    public void setRank(CardRank rank) { this.rank = rank; }

    @Override
    public String toString() {
        return family + " " + rank;
    }
}
