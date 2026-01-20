package com.johnm.sabacc.backend.repository;

import com.johnm.sabacc.backend.domain.game.GamePreset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePresetRepository extends JpaRepository<GamePreset, Integer> {
}
