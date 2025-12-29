package com.johnm.sabacc.backend.controller;

import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.dto.player.PersonDTO;
import com.johnm.sabacc.backend.service.PlayerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping({"", "/"})
    public List<PersonDTO> getAll() {
        return playerService.getAll().stream().map(Person::toDto).toList();
    }

    @GetMapping("/{name}")
    public PersonDTO getByName(@PathVariable String name) {
        return playerService.getByName(name).toDto();
    }

    @PostMapping
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
        return playerService.createPerson(personDTO.toEntity()).toDto();
    }
}
