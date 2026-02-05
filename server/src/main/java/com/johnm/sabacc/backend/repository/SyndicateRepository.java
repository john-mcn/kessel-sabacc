package com.johnm.sabacc.backend.repository;

import com.johnm.sabacc.backend.domain.Syndicate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SyndicateRepository extends JpaRepository<Syndicate, Integer> {
    public Optional<Syndicate> findByName(String name);
}
