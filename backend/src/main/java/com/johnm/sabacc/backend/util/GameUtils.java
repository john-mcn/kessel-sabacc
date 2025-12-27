package com.johnm.sabacc.backend.util;

import com.johnm.sabacc.backend.domain.components.Card;
import com.johnm.sabacc.backend.domain.components.ShiftToken;

import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    //TODO better way to represent decks or create full deck?
    public static List<Card> fullDeck() {
        List<Card> allCards = new ArrayList<>();

        for (Card.CardFamily token : Card.CardFamily.values()) {
            for (Card.CardRank rank : Card.CardRank.values()) {
                // All combinations have 3 cards except Sylop, which has 1
                if (!rank.equals(Card.CardRank.SYLOP)) {
                    for (int i = 0; i < 2; i++) { allCards.add(new Card(token, rank)); }
                }
                allCards.add(new Card(token, rank));
            }
        }

        return allCards;
    }
}
