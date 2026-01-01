package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.components.ShiftToken;
import com.johnm.sabacc.backend.domain.components.ShiftTokenEntity;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.ShiftTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ShiftTokenService {
    private ShiftTokenRepository shiftTokenRepository;

    public ShiftTokenService(ShiftTokenRepository shiftTokenRepository) {
        this.shiftTokenRepository = shiftTokenRepository;
    }

    public List<ShiftTokenEntity> getAll() {
        return shiftTokenRepository.findAll();
    }

    public ShiftTokenEntity getByName(String name) {
        ShiftTokenEntity token = shiftTokenRepository.findById(name)
                .orElseThrow(() -> new EntityNotFoundException("ShiftToken with name " + name + " not found"));
        return token;
    }

    public ShiftTokenEntity createShiftToken(ShiftTokenEntity token) {
        return shiftTokenRepository.save(token);
    }

    public ShiftTokenEntity createShiftTokenFromEnum(ShiftToken token) {
        return shiftTokenRepository.save(ShiftTokenEntity.createFromEnum(token));
    }
}
