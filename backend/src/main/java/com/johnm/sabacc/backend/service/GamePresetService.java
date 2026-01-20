package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.game.GamePreset;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.GamePresetRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GamePresetService {
    private GamePresetRepository gamePresetRepository;

    public void createAll(List<GamePreset> presets) {
        gamePresetRepository.saveAll(presets);
    }

    public GamePresetService(GamePresetRepository gamePresetRepository) {
        this.gamePresetRepository = gamePresetRepository;
    }

    public List<GamePreset> getGamePresets() {
        return gamePresetRepository.findAll();
    }

    public GamePreset getGamePreset(int id) {
        return gamePresetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GamePreset with id " + id + " not found"));
    }
}
