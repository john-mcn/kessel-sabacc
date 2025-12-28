package com.johnm.sabacc.backend.util;

import com.johnm.sabacc.backend.domain.components.Card;
import com.johnm.sabacc.backend.domain.components.CardRank;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameUtilTest {
    @Test
    void fullDeck_shouldHaveAllCards() {
        List<Card> fullDeck = GameUtils.fullDeck();
        List<Card> sylops = fullDeck.stream().filter(c -> c.getRank() == CardRank.SYLOP).toList();

        assertEquals(44, fullDeck.size(), "Full deck should have 3 of each rank (but only 1 Sylop) for each family");
        assertEquals(2, sylops.size(), "Full deck has 1 sylop of each family");
    }
}
