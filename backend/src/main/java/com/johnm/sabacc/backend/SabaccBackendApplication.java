package com.johnm.sabacc.backend;

import com.johnm.sabacc.backend.config.RsaKeyProperties;
import com.johnm.sabacc.backend.domain.Syndicate;
import com.johnm.sabacc.backend.domain.game.GamePreset;
import com.johnm.sabacc.backend.domain.game.Rewards;
import com.johnm.sabacc.backend.domain.game.components.ShiftToken;
import com.johnm.sabacc.backend.domain.game.GameHistory;
import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.repository.GamePresetRepository;
import com.johnm.sabacc.backend.repository.PlayerRepository;
import com.johnm.sabacc.backend.repository.SyndicateRepository;
import com.johnm.sabacc.backend.service.GamePresetService;
import com.johnm.sabacc.backend.service.PlayerService;
import com.johnm.sabacc.backend.service.GameHistoryService;
import com.johnm.sabacc.backend.service.ShiftTokenService;
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
    CommandLineRunner init(PlayerService playerService, GameHistoryService gameHistoryService,
                           ShiftTokenService shiftTokenService, PlayerRepository playerRepository,
                           PasswordEncoder passwordEncoder, SyndicateRepository syndicateRepository,
                           GamePresetService gamePresetService) {
        // Create all shift tokens
        for (ShiftToken token : ShiftToken.values()) {
            shiftTokenService.createShiftTokenFromEnum(token);
        }

        // Create Syndicates
        syndicateRepository.save(new Syndicate("crimson_dawn", "Crimson Dawn", "Crimson Dawn focused on secrets and espionage"));
        syndicateRepository.save(new Syndicate("hutt", "Hutt Clan", "Hutt Clan based on Tattooine"));
        syndicateRepository.save(new Syndicate("pyke", "Pyke Syndicate", "Pyke Syndicate focused on spice trade"));

        // Create game presets
        List<GamePreset> presets = List.of(
                new GamePreset(1, 20, 3, 0, 0, 0, null),
                new GamePreset(2, 200, 5, 0, 30, 0, new Rewards(0, 10, 0, 0, -10, 0))
        );
        gamePresetService.createAll(presets);

        return args -> {
            List<Person> people = List.of(
                new Person("dev", "password", "ROLE_ADMIN", "Dev", 9999, List.of(ShiftToken.values()), 100, 100, 100),
                new Person("test1", "password", "ROLE_USER", "Test1", 600, List.of(ShiftToken.IMMUNITY, ShiftToken.COOK_THE_BOOKS), 0, 0, 0),
                new Person("test2", "password", "ROLE_USER", "Test2", 320, List.of(ShiftToken.GENERAL_AUDIT), 0, 0, 0),
                new Person("test3", "password", "ROLE_USER", "Test3", 210, List.of(ShiftToken.DIRECT_TRANSACTION), 0, 0, 0)
            );

            for (Person person : people) {
                playerService.createPlayer(person);
            }
        };
    }

}
