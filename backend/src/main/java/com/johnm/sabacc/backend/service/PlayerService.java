package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.PlayerRepository;
import com.johnm.sabacc.backend.repository.GameHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final GameHistoryRepository gameHistoryRepository;
    private final PasswordEncoder passwordEncoder;

    public PlayerService(PlayerRepository playerRepository, GameHistoryRepository gameHistoryRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.gameHistoryRepository = gameHistoryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> getAll() { return playerRepository.findAll(); }

    public List<Person> getByUsernames(List<String> usernames) {
        return playerRepository.findByNameIn(usernames);
    }

    public Person getByUsername(String username) {
        return playerRepository.findById(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No person with name '" + username + "'"));
    }

    public Person createPlayer(Person player) {
        player.setPassword(passwordEncoder.encode(player.getPassword()));
        return playerRepository.save(player);
    }


    public Person updatePlayer(Person player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(String username) {
        playerRepository.deleteById(username);
    }
}
