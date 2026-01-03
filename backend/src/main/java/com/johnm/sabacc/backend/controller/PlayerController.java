package com.johnm.sabacc.backend.controller;

import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.dto.player.PersonDTO;
import com.johnm.sabacc.backend.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{name}")
    public ResponseEntity<PersonDTO> getByName(@PathVariable String name) {
        PersonDTO playerDTO = playerService.getByName(name).toDto();
        return ResponseEntity.status(HttpStatus.OK).body(playerDTO);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        Person player = playerService.createPlayer(personDTO.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(player.toDto());
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deletePlayer(@PathVariable String name) {
        playerService.deletePlayer(name);
        return ResponseEntity.ok().build();
    }
}
