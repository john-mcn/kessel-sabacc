package com.johnm.sabacc.backend.controller;

import com.johnm.sabacc.backend.domain.game.GameRound;
import com.johnm.sabacc.backend.domain.game.SabaccGame;
import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.dto.game.ActionRequestDTO;
import com.johnm.sabacc.backend.dto.game.GameStateDTO;
import com.johnm.sabacc.backend.dto.game.ResolveImposterDTO;
import com.johnm.sabacc.backend.dto.player.PlayerDTO;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;
import com.johnm.sabacc.backend.service.GameManager;
import com.johnm.sabacc.backend.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/play")
public class GameController {
    private final GameManager manager;
    private final PlayerService playerService;

    public GameController(GameManager manager, PlayerService playerService) {
        this.manager = manager;
        this.playerService = playerService;
    }

    @PostMapping("/start-game")
    public ResponseEntity<?> createGame(@RequestBody GameStateDTO dto) {
        SabaccGame g = dto.toEntity(); // create SabaccGame without persisting
        List<Person> peopleToPlay = playerService.getByNames(dto.players.stream().map(PlayerDTO::getName).toList());
        g.setPeopleToPlay(peopleToPlay);
        manager.createGame(g);
        return ResponseEntity.ok(GameStateDTO.fromEntities(g, null));
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
