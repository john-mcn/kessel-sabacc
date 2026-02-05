package com.johnm.sabacc.server.util;

import com.johnm.sabacc.server.domain.game.components.Card;
import com.johnm.sabacc.server.domain.game.components.CardFamily;
import com.johnm.sabacc.server.domain.game.components.CardRank;

import java.util.ArrayList;
import java.util.List;

public class GameUtils {
    //TODO better way to represent decks or create full deck?
    public static List<Card> fullDeck() {
        List<Card> allCards = new ArrayList<>();

        //NOTE treats duplicates as separate objects
        for (CardFamily token : CardFamily.values()) {
            for (CardRank rank : CardRank.values()) {
                // All combinations have 3 cards except Sylop, which has 1
                if (!rank.equals(CardRank.SYLOP)) {
                    for (int i = 0; i < 2; i++) { allCards.add(new Card(token, rank)); }
                }
                allCards.add(new Card(token, rank));
            }
        }

        return allCards;
    }

    public static int[] roll2d6() {
        return new int[]{
                (int) (Math.random() * 6) + 1,
                (int) (Math.random() * 6) + 1
        };
    }
}
