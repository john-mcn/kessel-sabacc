package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.game.GameHistory;
import com.johnm.sabacc.backend.domain.game.SabaccGame;
import com.johnm.sabacc.backend.exceptions.EntityAlreadyExistsException;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.SabaccGameRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SabaccGameService {
    private SabaccGameRepository sabaccGameRepository;

    public SabaccGameService(SabaccGameRepository sabaccGameRepository) {
        this.sabaccGameRepository = sabaccGameRepository;
    }

    public List<GameHistory> getAll() {
        return sabaccGameRepository.findAll();
    }

    public GameHistory getById(Integer id) {
        return sabaccGameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Sabacc Game with id " + id + " found"));
    }

    public List<GameHistory> getByPlayerName(String playerName) {
        System.err.println(playerName);
        System.err.println(sabaccGameRepository.findByPlayerName(playerName));
        return sabaccGameRepository.findByPlayerName(playerName);
    }

    public GameHistory createGame(GameHistory sabaccGame) {
        if (sabaccGameRepository.findById(sabaccGame.getId()).isPresent()) {
            throw new EntityAlreadyExistsException("Sabacc Game with id " + sabaccGame.getId() + " already exists");
        }
        return sabaccGameRepository.save(sabaccGame);
    }

    public void deleteById(Integer id) {
        sabaccGameRepository.deleteById(id);
    }
}
