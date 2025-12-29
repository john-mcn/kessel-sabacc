package com.johnm.sabacc.backend;

import com.johnm.sabacc.backend.domain.GameRound;
import com.johnm.sabacc.backend.domain.components.CardRank;
import com.johnm.sabacc.backend.domain.components.ShiftToken;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.domain.SabaccGame;
import com.johnm.sabacc.backend.domain.player.PlayerHand;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class SabaccBackendApplication {

	public static void main(String[] args) {
		// SpringApplication.run(SabaccBackendApplication.class, args);

        Player[] players = {
                new Player("One", 100, null, null, new ShiftToken[]{ShiftToken.REFUND}),
                new Player("Two", 100, null, null, null),
                new Player("Three", 100, null, null, null),
        };
        SabaccGame testGame = new SabaccGame(players, 50, 1);
        testGame.setup();

        GameRound testRound = new GameRound(testGame);
        testRound.runFullgame();
        // System.out.println(Arrays.toString(testRound.getPlayers()));

        // testRound.performTurn(players[0]);
        // testRound.performTurn(players[0]);

        // System.out.println(testRound.revealCards());
	}

}
