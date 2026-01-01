package com.johnm.sabacc.backend.domain.game.components;

import com.johnm.sabacc.backend.dto.game.CardDTO;
import com.johnm.sabacc.backend.util.EnumUtils;

public class Card {

    private CardFamily family;
    private CardRank rank;

    public Card(CardFamily family, CardRank rank) {
        this.family = family;
        this.rank = rank;
    }

    public Card(String family, String rank) {
        this.family = CardFamily.fromString(family);
        this.rank = CardRank.fromString(rank);
    }

    public CardFamily getFamily() { return family; }
    public void setFamily(CardFamily family) { this.family = family; }
    public boolean isSand() { return family.equals(CardFamily.SAND); }
    public boolean isBlood() { return family.equals(CardFamily.BLOOD); }

    public CardRank getRank() { return rank; }
    public void setRank(CardRank rank) { this.rank = rank; }
    public boolean isSylop() { return rank.equals(CardRank.SYLOP); }
    public boolean isImposter() { return rank.equals(CardRank.IMPOSTER); }

    public CardDTO toDTO() {
        return new CardDTO(
                EnumUtils.sanitiseStringFromEnum(family.name()),
                EnumUtils.sanitiseStringFromEnum(rank.name()));
    }

    @Override
    public String toString() {
        return family + " " + rank;
    }
}
