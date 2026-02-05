package com.johnm.sabacc.server.repository;

import com.johnm.sabacc.server.domain.game.components.ShiftTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftTokenRepository extends JpaRepository<ShiftTokenEntity, String> {
}
