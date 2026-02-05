package com.johnm.sabacc.server.service;

import com.johnm.sabacc.server.domain.game.components.ShiftToken;
import com.johnm.sabacc.server.domain.game.components.ShiftTokenEntity;
import com.johnm.sabacc.server.exceptions.EntityNotFoundException;
import com.johnm.sabacc.server.repository.ShiftTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ShiftTokenService {
    private final ShiftTokenRepository shiftTokenRepository;

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
