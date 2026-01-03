package com.johnm.sabacc.backend.controller;

import com.johnm.sabacc.backend.domain.game.GameHistory;
import com.johnm.sabacc.backend.dto.game.GameHistoryDTO;
import com.johnm.sabacc.backend.service.PlayerService;
import com.johnm.sabacc.backend.service.GameHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameHistoryController {
    private GameHistoryService gameHistoryService;
    private PlayerService playerService;

    public GameHistoryController(GameHistoryService gameHistoryService, PlayerService playerService) {
        this.gameHistoryService = gameHistoryService;
        this.playerService = playerService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<GameHistoryDTO>> getGames() {
        List<GameHistoryDTO> gameDTOs =  gameHistoryService.getAll().stream().map(GameHistory::toDTO).toList();
        return ResponseEntity.ok(gameDTOs);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<GameHistoryDTO> getGame(@PathVariable Integer id) {
        GameHistoryDTO gameDTO = gameHistoryService.getById(id).toDTO();
        return ResponseEntity.ok(gameDTO);
    }

    @GetMapping("/byPlayer/{playerName}")
    public ResponseEntity<List<GameHistoryDTO>> getByPlayerName(@PathVariable String playerName) {
        List<GameHistoryDTO> gameDTOS = gameHistoryService.getByPlayerName(playerName).stream().map(GameHistory::toDTO).toList();
        return ResponseEntity.ok(gameDTOS);
    }

    @PostMapping({"", "/"})
    public ResponseEntity<GameHistoryDTO> createGame(@RequestBody GameHistoryDTO dto) {
        GameHistory sabaccGame = dto.toEntity();
        // sabaccGame.setPlayerNames(dto.getPlayerNames());
        // sabaccGame.setWinners(playerService.getByNames(dto.getWinnerNames()));

        GameHistoryDTO gameHistoryDTO = gameHistoryService.createGame(sabaccGame).toDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(gameHistoryDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Integer id) {
        gameHistoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
