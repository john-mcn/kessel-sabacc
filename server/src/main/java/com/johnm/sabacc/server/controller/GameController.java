package com.johnm.sabacc.server.controller;

import com.johnm.sabacc.server.domain.game.GameRound;
import com.johnm.sabacc.server.domain.game.SabaccGame;
import com.johnm.sabacc.server.domain.player.Person;
import com.johnm.sabacc.server.dto.game.ActionRequestDTO;
import com.johnm.sabacc.server.dto.game.GameStateDTO;
import com.johnm.sabacc.server.dto.game.ResolveImposterDTO;
import com.johnm.sabacc.server.dto.player.PlayerDTO;
import com.johnm.sabacc.server.exceptions.EntityNotFoundException;
import com.johnm.sabacc.server.exceptions.IllegalActionException;
import com.johnm.sabacc.server.service.GameManager;
import com.johnm.sabacc.server.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/play")
public class GameController {
    private final GameManager manager;
    private final PlayerService playerService;

    public GameController(GameManager manager, PlayerService playerService) {
        this.manager = manager;
        this.playerService = playerService;
    }

    @PostMapping("/start-game")
    public ResponseEntity<?> createGame(@RequestBody GameStateDTO dto, Authentication authentication) {
        SabaccGame game = dto.toEntity(); // create SabaccGame without persisting
        List<Person> peopleToPlay = playerService.getByUsernames(dto.players.stream().map(PlayerDTO::getName).toList());
        game.setPeopleToPlay(peopleToPlay);
        manager.createGame(game, authentication);
        return ResponseEntity.ok(GameStateDTO.fromEntities(game, null));
    }

    @PostMapping("/start-round")
    public ResponseEntity<?> startRound() {
        GameRound round = manager.startRound();
        return ResponseEntity.ok(GameStateDTO.fromEntities(manager.getCurrentGame().get(), round));
    }

    @PostMapping("/action")
    public ResponseEntity<?> action(@RequestBody ActionRequestDTO request) {
        try {
            GameStateDTO snapshot = manager.performAction(request);
            return ResponseEntity.ok(snapshot);
        } catch (IllegalActionException | IllegalStateException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<GameStateDTO> getGame() {
        return manager.getCurrentGame()
                .map(g -> ResponseEntity.ok(
                        GameStateDTO.fromEntities(g, manager.getCurrentRound().orElse(null))
                ))
                .orElseThrow(() -> new EntityNotFoundException("No game in progress"));
    }

    @PostMapping("/resolve-imposters")
    public ResponseEntity<?> resolveImposter(@RequestBody List<ResolveImposterDTO> dtos) {
        manager.resolveImposter(dtos);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset")
    public ResponseEntity<?> reset() {
        manager.resetGame();
        return ResponseEntity.ok(Map.of("status", "reset"));
    }
}
