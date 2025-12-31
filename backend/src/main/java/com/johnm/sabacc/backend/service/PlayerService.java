package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.PlayerRepository;
import com.johnm.sabacc.backend.repository.SabaccGameRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PlayerService {
    private PlayerRepository playerRepository;
    private SabaccGameRepository sabaccGameRepository;

    public PlayerService(PlayerRepository playerRepository, SabaccGameRepository sabaccGameRepository) {
        this.playerRepository = playerRepository;
        this.sabaccGameRepository = sabaccGameRepository;
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
        sabaccGameRepository.deleteFromPeopleToPlayJoinTable(name);
        sabaccGameRepository.deleteFromWinnersJoinTable(name);
        playerRepository.deleteById(name);
    }
}
