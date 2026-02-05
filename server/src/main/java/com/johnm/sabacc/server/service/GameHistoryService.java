package com.johnm.sabacc.server.service;

import com.johnm.sabacc.server.config.Authorities;
import com.johnm.sabacc.server.domain.game.GameHistory;
import com.johnm.sabacc.server.domain.player.Person;
import com.johnm.sabacc.server.exceptions.AccessForbiddenException;
import com.johnm.sabacc.server.exceptions.EntityAlreadyExistsException;
import com.johnm.sabacc.server.exceptions.EntityNotFoundException;
import com.johnm.sabacc.server.repository.GameHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GameHistoryService {
    private final GameHistoryRepository gameHistoryRepository;
    private final PlayerService playerService;

    public GameHistoryService(GameHistoryRepository gameHistoryRepository,  PlayerService playerService) {
        this.gameHistoryRepository = gameHistoryRepository;
        this.playerService = playerService;
    }

    public List<GameHistory> getAll() {
        return gameHistoryRepository.findAll();
    }

    public GameHistory getById(Integer id) {
        return gameHistoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Sabacc Game with id " + id + " found"));
    }

    public List<GameHistory> getByPlayerName(String playerName) {
        return gameHistoryRepository.findByPlayerName(playerName);
    }

    public GameHistory createGame(GameHistory sabaccGame) {
        if (gameHistoryRepository.findById(sabaccGame.getId()).isPresent()) {
            throw new EntityAlreadyExistsException("Sabacc Game with id " + sabaccGame.getId() + " already exists");
        }
        return gameHistoryRepository.save(sabaccGame);
    }

    public void deleteById(Integer id, Authentication auth) {
        Person authedUser = playerService.getByUsername(auth.getName());
        if (!authedUser.getRole().equals(Authorities.ROLE_ADMIN)) {
            throw new AccessForbiddenException(
                    "User '" + authedUser.getUsername() + "' cannot delete game with ID " + id);
        }
        System.err.println("USER IS ADMIN: " + authedUser.getUsername() + " ROLE " + authedUser.getRole());
        gameHistoryRepository.deleteById(id);
    }
}
