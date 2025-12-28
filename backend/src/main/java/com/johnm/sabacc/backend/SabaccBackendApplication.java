package com.johnm.sabacc.backend;

import com.johnm.sabacc.backend.domain.GameRound;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.domain.SabaccGame;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SabaccBackendApplication {

	public static void main(String[] args) {
		// SpringApplication.run(SabaccBackendApplication.class, args);

        Player[] players = {
                new Player("One", 100, null, null, null),
                new Player("Two", 100, null, null, null),
                new Player("Three", 100, null, null, null),
        };
        SabaccGame testGame = new SabaccGame(players, 50, 1);
        testGame.setup();

        GameRound testRound = new GameRound(testGame);
        testRound.setup();

        // testRound.performTurn(players[0]);
        // testRound.performTurn(players[0]);

        System.out.println(testRound.sortHands());
	}

}
