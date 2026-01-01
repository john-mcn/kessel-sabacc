package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.game.GameRound;
import com.johnm.sabacc.backend.domain.game.SabaccGame;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.dto.game.ActionRequestDTO;
import com.johnm.sabacc.backend.dto.game.GameStateDTO;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

// In-memory game service
@Service
public class GameManager {
    private final ReentrantLock lock = new ReentrantLock();

    // single game at a time
    private SabaccGame currentGame;
    private GameRound currentRound;

    public void createGame(SabaccGame game) {
        lock.lock();
        try {
            // optional: validate peopleToPlay credits, buyIn, etc.
            this.currentGame = game;
            this.currentRound = null;
        } finally {
            lock.unlock();
        }
    }

    public Optional<SabaccGame> getCurrentGame() {
        lock.lock();
        try {
            return Optional.ofNullable(currentGame);
        } finally {
            lock.unlock();
        }
    }

    public Optional<GameRound> getCurrentRound() {
        lock.lock();
        try {
            return Optional.ofNullable(currentRound);
        } finally {
            lock.unlock();
        }
    }

    public GameRound startRound() {
        //NOTE assume game is set up
        lock.lock();
        try {
            if (currentGame == null) {
                throw new IllegalStateException("No game exists");
            }
            // ensure players setup (call game.setup or game.runGame style)
            currentRound = new GameRound(currentGame);
            currentRound.setup(); // prepare decks & hands
            return currentRound;
        } finally {
            lock.unlock();
        }
    }

    public GameStateDTO performAction(ActionRequestDTO request) {
        lock.lock();
        try {
            if (currentRound == null) {
                throw new IllegalStateException("No round in progress");
            }
            // find player by name
            Player player = currentRound.getPlayers().stream()
                    .filter(p -> p.getName().equals(request.getPlayerName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown player"));

            // validate it is that player's turn (optional)
            Player currentPlayer = currentRound.getPlayers().get(currentRound.getCurrPlayerIndex());
            if (!currentPlayer.equals(player)) {
                throw new IllegalActionException("Not player's turn, current player is " + currentPlayer.getName());
            }

            // delegate to GameRound with action DTO
            currentRound.performAction(request);

            // after action performed, optionally advance turn index inside GameRound.performAction
            // return snapshot for frontend
            return GameStateDTO.fromEntities(currentGame, currentRound);
        } finally {
            lock.unlock();
        }
    }

    public void resetGame() {
        lock.lock();
        try {
            currentGame = null;
            currentRound = null;
        } finally {
            lock.unlock();
        }
    }
}

