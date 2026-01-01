package com.johnm.sabacc.backend;

import com.johnm.sabacc.backend.domain.components.ShiftToken;
import com.johnm.sabacc.backend.domain.game.GameHistory;
import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.service.PlayerService;
import com.johnm.sabacc.backend.service.SabaccGameService;
import com.johnm.sabacc.backend.service.ShiftTokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class SabaccBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SabaccBackendApplication.class, args);
	}

    @Bean
    CommandLineRunner init(PlayerService playerService, SabaccGameService sabaccGameService, ShiftTokenService shiftTokenService) {
        // Create all shift tokens
        for (ShiftToken token : ShiftToken.values()) {
            shiftTokenService.createShiftTokenFromEnum(token);
        }

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
