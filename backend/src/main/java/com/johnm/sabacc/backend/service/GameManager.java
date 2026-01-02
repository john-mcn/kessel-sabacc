package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.game.GameHistory;
import com.johnm.sabacc.backend.domain.game.GameRound;
import com.johnm.sabacc.backend.domain.game.SabaccGame;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.dto.game.ActionRequestDTO;
import com.johnm.sabacc.backend.dto.game.GameHistoryDTO;
import com.johnm.sabacc.backend.dto.game.GameStateDTO;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

// In-memory game service
@Service
public class GameManager {
    private final ReentrantLock lock = new ReentrantLock();

    private final GameHistoryService gameHistoryService;

    // single game at a time
    private SabaccGame currentGame;
    private GameRound currentRound;

    public GameManager(GameHistoryService gameHistoryService) {
        this.gameHistoryService = gameHistoryService;
    }

    // set up game and set up round
    public void createGame(SabaccGame game) {
        lock.lock();
        try {
            // optional: validate peopleToPlay credits, buyIn, etc.
            this.currentGame = game;
            game.setup();
            this.currentRound = startRound();
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
                System.err.println("CURRENT ROND NULL");
                throw new IllegalStateException("No round in progress");
            }
            // find player by name
            Player player = currentRound.getPlayers().stream()
                    .filter(p -> p.getName().equals(request.getPlayerName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown player"));

            // validate it is that player's turn (optional)
            Player currentPlayer = currentRound.getPlayers().get(currentRound.getCurrPlayerIndex());
            if (!currentPlayer.getName().equals(player.getName())) {
                throw new IllegalActionException("Not player's turn, current player is " + currentPlayer.getName());
            }

            // delegate to GameRound with action DTO
            currentRound.performAction(request);

            if (currentRound.getTurnNumber() == 4 || currentRound.getInStand().size() == currentRound.getPlayers().size()) {
                currentRound.revealCards();
                List<Player> playersInGame = currentGame.getPlayers().stream().filter(p -> p.getStock() > 0).toList();
                if (playersInGame.size() == 1) {
                    endGame();
                }
                // else { startRound(); }
            }

            // after action performed, optionally advance turn index inside GameRound.performAction
            // return snapshot for frontend
            return GameStateDTO.fromEntities(currentGame, currentRound);
        } finally {
            lock.unlock();
        }
    }

    public GameStateDTO endGame() {
        lock.lock();
        try {
            System.err.println("===END GAME===");
            currentGame.endGame();
            System.err.println("Winner: " +  currentGame.getWinner());
            GameHistory gameHistory = currentGame.toGameHistory();
            gameHistoryService.createGame(gameHistory);
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

