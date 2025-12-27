package com.johnm.sabacc.backend;

import com.johnm.sabacc.backend.domain.GameRound;
import com.johnm.sabacc.backend.domain.Player;
import com.johnm.sabacc.backend.domain.components.Card;
import com.johnm.sabacc.backend.util.GameUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SabaccBackendApplication {

	public static void main(String[] args) {
		// SpringApplication.run(SabaccBackendApplication.class, args);

        Player[] players = {
                new Player("One", 100, null, null, null),
                new Player("Two", 100, null, null, null),
                new Player("Three", 100, null, null, null),
        };
        GameRound testRound = new GameRound(players);
        testRound.setup();


        //--- Test ---
        List<Card> allPlayerCards = new ArrayList<>();
        for (Player player : testRound.getPlayers()) {
            // System.out.println(player.getHand());
            allPlayerCards.add(player.getHand().getBloodCard());
            allPlayerCards.add(player.getHand().getSandCard());
        }

        System.out.println(allPlayerCards.size() == 6);
        List<Card> allCards = testRound.getBloodDiscard(); allCards.addAll(testRound.getSandDiscard());
        boolean cardsRemovd = true;
        for (Card card : allPlayerCards) {
            if (allCards.contains(card)) { cardsRemovd = false; }
        }
        System.out.println(cardsRemovd);
	}

}
