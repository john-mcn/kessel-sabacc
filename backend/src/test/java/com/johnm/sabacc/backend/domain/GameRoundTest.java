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
    void testSetup() {
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

    @Test
    void testSortPlayers() {
        Player[] players = {
                new Player("One", 100, null, new PlayerHand(new Card("blood", "one"), new Card("sand", "six")), null),
                new Player("Two", 100, null, new PlayerHand(new Card("blood", "one"), new Card("sand", "one")), null),
                new Player("Three", 100, null, new PlayerHand(new Card("blood", "one"), new Card("sand", "three")), null),
        };
        SabaccGame testGame = new SabaccGame(players, 50, 4);
        GameRound testRound = new GameRound(testGame);

        List<Player> correctOrder = new ArrayList<>(List.of(players[1], players[2], players[0]));
        assertEquals(correctOrder, testRound.sortPlayers(), "Players should be sorted correctly");
    }

    @Test
    void findWinners_shouldFindWinner() {
        Player[] players = {
                new Player("One", 100, null, new PlayerHand(new Card("blood", "one"), new Card("sand", "six")), null),
                new Player("Two", 100, null, new PlayerHand(new Card("blood", "one"), new Card("sand", "one")), null),
                new Player("Three", 100, null, new PlayerHand(new Card("blood", "one"), new Card("sand", "three")), null),
        };
        SabaccGame testGame = new SabaccGame(players, 50, 4);
        GameRound testRound = new GameRound(testGame);

        assertEquals(List.of(players[1]), testRound.findWinners(), "Correct winner should be found");
    }

    @Test
    void findWinners_shouldFindWinners() {
        Player[] players = {
                new Player("One", 100, null, new PlayerHand(new Card("blood", "one"), new Card("sand", "one")), null),
                new Player("Two", 100, null, new PlayerHand(new Card("blood", "one"), new Card("sand", "one")), null),
                new Player("Three", 100, null, new PlayerHand(new Card("blood", "one"), new Card("sand", "three")), null),
        };
        SabaccGame testGame = new SabaccGame(players, 50, 4);
        GameRound testRound = new GameRound(testGame);

        assertEquals(List.of(players[0], players[1]), testRound.findWinners(), "Correct winner should be found");
    }
}
