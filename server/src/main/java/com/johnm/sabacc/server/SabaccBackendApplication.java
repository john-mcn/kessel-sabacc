package com.johnm.sabacc.server;

import com.johnm.sabacc.server.config.RsaKeyProperties;
import com.johnm.sabacc.server.domain.game.components.ShiftToken;
import com.johnm.sabacc.server.domain.player.Person;
import com.johnm.sabacc.server.repository.PlayerRepository;
import com.johnm.sabacc.server.service.PlayerService;
import com.johnm.sabacc.server.service.GameHistoryService;
import com.johnm.sabacc.server.service.ShiftTokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SabaccBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SabaccBackendApplication.class, args);
	}

    @Bean
    CommandLineRunner init(PlayerService playerService, GameHistoryService gameHistoryService, ShiftTokenService shiftTokenService, PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        // Create all shift tokens
        for (ShiftToken token : ShiftToken.values()) {
            shiftTokenService.createShiftTokenFromEnum(token);
        }

        return args -> {
            List<Person> people = List.of(
                new Person("dev", "password", "ROLE_ADMIN", "Dev", 9999, List.of(ShiftToken.values())),
                new Person("test1", "password", "ROLE_USER", "Test1", 600, List.of(ShiftToken.IMMUNITY, ShiftToken.COOK_THE_BOOKS)),
                new Person("test2", "password", "ROLE_USER", "Test2", 320, List.of(ShiftToken.GENERAL_AUDIT)),
                new Person("test3", "password", "ROLE_USER", "Test3", 210, List.of(ShiftToken.DIRECT_TRANSACTION))
            );

            for (Person person : people) {
                playerService.createPlayer(person);
            }
        };
    }

}
