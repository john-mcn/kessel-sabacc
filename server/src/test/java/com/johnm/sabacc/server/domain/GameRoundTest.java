package com.johnm.sabacc.server.domain;

import com.johnm.sabacc.server.domain.game.components.Card;
import com.johnm.sabacc.server.domain.game.GameRound;
import com.johnm.sabacc.server.domain.game.SabaccGame;
import com.johnm.sabacc.server.domain.player.Person;
import com.johnm.sabacc.server.domain.player.Player;
import com.johnm.sabacc.server.domain.player.PlayerHand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameRoundTest {
    
    @Test
    void testSetup() {
        List<Person> people = List.of(
                new Person("One", 100, null),
                new Person("Two", 100, null),
                new Person("Three", 100, null));
        SabaccGame testGame = new SabaccGame(people, 50, 4, null);
        testGame.setup();
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
        PlayerHand firstPlayerHand = testRound.getPlayers().get(0).getHand();
        assertTrue(cardsWereRemoved, "players' cards are removed from discard");
        assertTrue(firstPlayerHand.getBloodCard().isBlood() && firstPlayerHand.getSandCard().isSand(),
                "a player's starting hand has one sand and one blood card");
    }

    @Test
    void testSortPlayers() {
        List<Person> people = List.of(
                new Person("One", 100, null),
                new Person("Two", 100, null),
                new Person("Three", 100, null)
        );
        SabaccGame testGame = new SabaccGame(people, 50, 4, null);
        testGame.setup();
        GameRound testRound = new GameRound(testGame);

        testRound.getPlayers().get(0).setHand(new PlayerHand(new Card("blood", "one"), new Card("sand", "six")));
        testRound.getPlayers().get(1).setHand(new PlayerHand(new Card("blood", "one"), new Card("sand", "one")));
        testRound.getPlayers().get(2).setHand(new PlayerHand(new Card("blood", "one"), new Card("sand", "three")));

        List<String> correctOrder = List.of(people.get(1).getName(), people.get(2).getName(), people.get(0).getName());
        assertTrue(testRound.sortPlayers().stream().map(Player::getName).toList().equals(correctOrder), "Players should be sorted correctly");
    }

    @Test
    void findWinners_shouldFindWinner() {
        List<Person> people = List.of(
                new Person("One", 100, null),
                new Person("Two", 100, null),
                new Person("Three", 100, null)
        );
        SabaccGame testGame = new SabaccGame(people, 50, 4, null);
        testGame.setup();
        GameRound testRound = new GameRound(testGame);

        // Simulate GameRound state
        testRound.getPlayers().get(0).setHand(new PlayerHand(new Card("blood", "one"), new Card("sand", "one")));
        testRound.getPlayers().get(1).setHand(new PlayerHand(new Card("blood", "one"), new Card("sand", "three")));
        testRound.getPlayers().get(2).setHand(new PlayerHand(new Card("blood", "one"), new Card("sand", "four")));

        List<Player> winners = testRound.findWinners();
        boolean winnersCorrect = winners.get(0).getName().equals("One")
                && winners.size() == 1;

        assertTrue(winnersCorrect, "Correct winner should be found");
    }
    
    @Test
    void findWinners_shouldFindWinners() {
        List<Person> people = List.of(
                new Person("One", 100, null),
                new Person("Two", 100, null),
                new Person("Three", 100, null)
        );
        SabaccGame testGame = new SabaccGame(people, 50, 4, null);
        testGame.setup();
        GameRound testRound = new GameRound(testGame);

        // Simulate GameRound state
        testRound.getPlayers().get(0).setHand(new PlayerHand(new Card("blood", "one"), new Card("sand", "one")));
        testRound.getPlayers().get(1).setHand(new PlayerHand(new Card("blood", "one"), new Card("sand", "one")));
        testRound.getPlayers().get(2).setHand(new PlayerHand(new Card("blood", "one"), new Card("sand", "three")));

        List<Player> winners = testRound.findWinners();
        boolean winnersCorrect = winners.stream().allMatch(p -> p.getName().equals("One") || p.getName().equals("Two"))
                && winners.size() == 2;

        assertTrue(winnersCorrect, "Correct winner should be found");
    }
}
