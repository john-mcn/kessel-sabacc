package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.PlayerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PlayerService {
    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
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

    public Person createPerson(Person person) {
        return playerRepository.save(person);
    }
}
