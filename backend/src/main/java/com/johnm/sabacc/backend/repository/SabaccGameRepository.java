package com.johnm.sabacc.backend.repository;

import com.johnm.sabacc.backend.domain.SabaccGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SabaccGameRepository extends JpaRepository<SabaccGame, Integer> {
}
