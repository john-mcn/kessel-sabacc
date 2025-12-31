package com.johnm.sabacc.backend.controller;

import com.johnm.sabacc.backend.domain.game.GameHistory;
import com.johnm.sabacc.backend.dto.GameHistoryDTO;
import com.johnm.sabacc.backend.service.PlayerService;
import com.johnm.sabacc.backend.service.SabaccGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//NOTE Uses GameHistory class
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
    public ResponseEntity<List<GameHistoryDTO>> getGames() {
        List<GameHistoryDTO> gameDTOs =  sabaccGameService.getAll().stream().map(GameHistory::toDTO).toList();
        return ResponseEntity.ok(gameDTOs);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<GameHistoryDTO> getGame(@PathVariable Integer id) {
        GameHistoryDTO gameDTO = sabaccGameService.getById(id).toDTO();
        return ResponseEntity.ok(gameDTO);
    }

    @GetMapping("/byPlayer/{playerName}")
    public ResponseEntity<List<GameHistoryDTO>> getByPlayerName(@PathVariable String playerName) {
        List<GameHistoryDTO> gameDTOS = sabaccGameService.getByPlayerName(playerName).stream().map(GameHistory::toDTO).toList();
        return ResponseEntity.ok(gameDTOS);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<GameHistoryDTO> createGame(@RequestBody GameHistoryDTO dto) {
        GameHistory sabaccGame = dto.toEntity();
        // sabaccGame.setPlayerNames(dto.getPlayerNames());
        // sabaccGame.setWinners(playerService.getByNames(dto.getWinnerNames()));

        GameHistoryDTO gameHistoryDTO = sabaccGameService.createGame(sabaccGame).toDTO();
        System.err.println("bludclart" + gameHistoryDTO.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(gameHistoryDTO);
    }

}
