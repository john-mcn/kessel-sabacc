package com.johnm.sabacc.backend.dto.game;

import com.johnm.sabacc.backend.domain.game.components.Card;
import com.johnm.sabacc.backend.domain.game.components.CardFamily;
import com.johnm.sabacc.backend.domain.game.components.CardRank;

public class CardDTO {
    private String family;
    private String rank;

    public CardDTO(String family, String rank) {
        this.family = family;
        this.rank = rank;
    }

    public String getFamily() { return family; }
    public void setFamily(String family) { this.family = family; }

    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }

    public Card toEntity() {
        Card card = new Card(
                CardFamily.fromString(family),
                CardRank.fromString(rank)
        );
        return card;
    }
}
