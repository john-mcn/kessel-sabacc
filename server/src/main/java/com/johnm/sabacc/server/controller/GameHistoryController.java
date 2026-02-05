package com.johnm.sabacc.server.controller;

import com.johnm.sabacc.server.domain.game.GameHistory;
import com.johnm.sabacc.server.dto.game.GameHistoryDTO;
import com.johnm.sabacc.server.service.PlayerService;
import com.johnm.sabacc.server.service.GameHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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

    @PreAuthorize("hasAuthority(T(com.johnm.sabacc.server.config.Authorities).ROLE_ADMIN)")
    @PostMapping({"", "/"})
    public ResponseEntity<GameHistoryDTO> createGame(@RequestBody GameHistoryDTO dto) {
        GameHistory sabaccGame = dto.toEntity();
        // sabaccGame.setPlayerNames(dto.getPlayerNames());
        // sabaccGame.setWinners(playerService.getByNames(dto.getWinnerNames()));

        GameHistoryDTO gameHistoryDTO = gameHistoryService.createGame(sabaccGame).toDTO();
        return ResponseEntity.status(HttpStatus.CREATED).body(gameHistoryDTO);
    }

    @PreAuthorize("hasAuthority(T(com.johnm.sabacc.server.config.Authorities).ROLE_ADMIN)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Integer id, Authentication auth) {
        gameHistoryService.deleteById(id, auth);
        return ResponseEntity.ok().build();
    }
}
