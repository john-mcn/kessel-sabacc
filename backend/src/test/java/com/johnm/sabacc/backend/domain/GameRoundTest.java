package com.johnm.sabacc.backend.domain;

import com.johnm.sabacc.backend.domain.components.Card;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.domain.player.PlayerHand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameRoundTest {

    @Test
    public void testSetup() {
        Player[] players = {
                new Player("One", 100, null, null, null),
                new Player("Two", 100, null, null, null),
                new Player("Three", 100, null, null, null),
        };
        SabaccGame testGame = new SabaccGame(players, 50, 4);
        GameRound testRound = new GameRound(testGame);
        testRound.setup();

        List<Card> allPlayerCards = new ArrayList<>();
        for (Player player : testRound.getPlayers()) {
            allPlayerCards.add(player.getHand().getBloodCard());
            allPlayerCards.add(player.getHand().getSandCard());
        }

        assertEquals(6, allPlayerCards.size(), "n players have 2n cards");

        List<Card> allCards = testRound.getBloodDraw(); allCards.addAll(testRound.getSandDraw());
        boolean cardsWereRemoved = true;
        for (Card card : allPlayerCards) {
            if (allCards.contains(card)) { cardsWereRemoved = false; }
        }
        PlayerHand firstPlayerHand = testRound.getPlayers()[0].getHand();
        assertTrue(cardsWereRemoved, "players' cards are removed from discard");
        assertTrue(firstPlayerHand.getBloodCard().isBlood() && firstPlayerHand.getSandCard().isSand(),
                "a player's starting hand has one sand and one blood card");
    }
}
