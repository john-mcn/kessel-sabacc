package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.PlayerRepository;
import com.johnm.sabacc.backend.repository.GameHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final GameHistoryRepository gameHistoryRepository;

    public PlayerService(PlayerRepository playerRepository, GameHistoryRepository gameHistoryRepository) {
        this.playerRepository = playerRepository;
        this.gameHistoryRepository = gameHistoryRepository;
    }

    public List<Person> getAll() { return playerRepository.findAll(); }

    public List<Person> getByNames(List<String> names) {
        return playerRepository.findByNameIn(names);
    }

    public Person getByName(String name) {
        return playerRepository.findById(name)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No person with name '" + name + "'"));
    }

    public Person createPlayer(Person player) {
        return playerRepository.save(player);
    }


    public Person updatePlayer(Person player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(String name) {
        playerRepository.deleteById(name);
    }
}
