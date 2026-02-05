package com.johnm.sabacc.server.controller;

import com.johnm.sabacc.server.domain.game.components.ShiftTokenEntity;
import com.johnm.sabacc.server.domain.player.Person;
import com.johnm.sabacc.server.dto.player.PersonDTO;
import com.johnm.sabacc.server.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<PersonDTO>> getAll() {
        List<PersonDTO> playerDTOs = playerService.getAll().stream().map(Person::toDto).toList();
        return ResponseEntity.ok(playerDTOs);
    }

    @GetMapping("/{username}")
    public ResponseEntity<PersonDTO> getByName(@PathVariable String username) {
        PersonDTO playerDTO = playerService.getByUsername(username).toDto();
        return ResponseEntity.status(HttpStatus.OK).body(playerDTO);
    }

    // @PostMapping({"", "/"})
    // public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
    //     Person newPerson = new Person(
    //             personDTO.getName(),
    //             personDTO.getCredits(),
    //             personDTO.getTokens().stream().map(ShiftTokenEntity::toEnum).toList()
    //     );
    //     newPerson.setUsername(personDTO.getUsername());
    //     newPerson.setPassword(personDTO.get);
    //     Person player = playerService.createPlayer(newPerson);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(player.toDto());
    // }

    @PutMapping("/{username}")
    public ResponseEntity<PersonDTO> update(@PathVariable String username,
                                            @RequestBody PersonDTO personDTO, Authentication auth) {
        Person updatedPerson = new Person(
                personDTO.getName(),
                personDTO.getCredits(),
                personDTO.getTokens().stream().map(ShiftTokenEntity::toEnum).toList()
        );
        updatedPerson.setUsername(username);

        Person savedPlayer = playerService.updatePlayer(username, updatedPerson, auth);

        return ResponseEntity.ok().body(savedPlayer.toDto());
    }

    @PreAuthorize("hasAuthority(T(com.johnm.sabacc.server.config.Authorities).ROLE_ADMIN)")
    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deletePlayer(@PathVariable String username) {
        playerService.deletePlayer(username);
        return ResponseEntity.ok().build();
    }
}
