package com.johnm.sabacc.backend;

import com.johnm.sabacc.backend.domain.components.ShiftToken;
import com.johnm.sabacc.backend.domain.game.GameHistory;
import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.domain.game.SabaccGame;
import com.johnm.sabacc.backend.service.PlayerService;
import com.johnm.sabacc.backend.service.SabaccGameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SabaccBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SabaccBackendApplication.class, args);

        // List<Person> people = new ArrayList<>(List.of(
        //         new Player("One", 100, null, null, null),
        //         new Player("Two", 100, null, null, null),
        //         new Player("Three", 100, null, null, null)));
        // SabaccGame testGame = new SabaccGame(people, 50, 3, "N/A");
        // testGame.setup();
        //
        // GameRound testRound = new GameRound(testGame);
        // List<Player> winners = testRound.runFullRound();
        //
        // System.out.println(winners.toString());
	}

    @Bean
    CommandLineRunner init(PlayerService playerService, SabaccGameService sabaccGameService) {
        return args -> {
            List<Person> people = List.of(
                new Person("Dev", 9999, null),
                new Person("Test1", 600, List.of(ShiftToken.IMMUNITY, ShiftToken.COOK_THE_BOOKS)),
                new Person("Test2", 320, List.of(ShiftToken.GENERAL_AUDIT)),
                new Person("Test3", 210, List.of(ShiftToken.DIRECT_TRANSACTION))
            );

            for (Person person : people) {
                playerService.createPlayer(person);
            }

            GameHistory testGame = new GameHistory(people.stream().map(Person::getName).toList(), null, 150, 6, List.of("N/A"));
            testGame.setWinnerNames(List.of(people.get(0).getName()));
            sabaccGameService.createGame(testGame);
        };
    }

}
