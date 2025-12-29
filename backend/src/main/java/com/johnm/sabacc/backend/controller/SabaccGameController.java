package com.johnm.sabacc.backend.controller;

import com.johnm.sabacc.backend.domain.SabaccGame;
import com.johnm.sabacc.backend.dto.SabaccGameDTO;
import com.johnm.sabacc.backend.service.PlayerService;
import com.johnm.sabacc.backend.service.SabaccGameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class SabaccGameController {
    private SabaccGameService sabaccGameService;
    private PlayerService playerService;

    public SabaccGameController(SabaccGameService sabaccGameService, PlayerService playerService) {
        this.sabaccGameService = sabaccGameService;
        this.playerService = playerService;
    }

    @GetMapping({"", "/"})
    public List<SabaccGameDTO> getGames() {
        return sabaccGameService.getAll().stream().map(SabaccGame::toDTO).toList();
    }

    @GetMapping({"/{id}"})
    public SabaccGameDTO getGame(@PathVariable Integer id) {
        return sabaccGameService.getById(id).toDTO();
    }

    @PostMapping({"", "/"})
    public SabaccGameDTO createGame(@RequestBody SabaccGameDTO dto) {
        SabaccGame sabaccGame = dto.toEntity();
        sabaccGame.setPeopleToPlay(playerService.getByNames(dto.getPlayerNames()));
        sabaccGame.setWinners(playerService.getByNames(dto.getWinnerNames()));

        return sabaccGameService.createGame(sabaccGame).toDTO();
    }

}
