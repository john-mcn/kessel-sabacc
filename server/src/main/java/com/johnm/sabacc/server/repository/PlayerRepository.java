package com.johnm.sabacc.server.repository;

import com.johnm.sabacc.server.domain.player.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Person, String> {
    public List<Person> findByNameIn(List<String> usernames);
}
