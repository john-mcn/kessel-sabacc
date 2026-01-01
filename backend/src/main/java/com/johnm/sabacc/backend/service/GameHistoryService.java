package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.game.GameHistory;
import com.johnm.sabacc.backend.exceptions.EntityAlreadyExistsException;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.GameHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GameHistoryService {
    private final GameHistoryRepository gameHistoryRepository;

    public GameHistoryService(GameHistoryRepository gameHistoryRepository) {
        this.gameHistoryRepository = gameHistoryRepository;
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

    public void deleteById(Integer id) {
        gameHistoryRepository.deleteById(id);
    }
}
