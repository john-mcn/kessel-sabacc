package com.johnm.sabacc.backend.repository;

import com.johnm.sabacc.backend.domain.components.ShiftTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftTokenRepository extends JpaRepository<ShiftTokenEntity, String> {
}
