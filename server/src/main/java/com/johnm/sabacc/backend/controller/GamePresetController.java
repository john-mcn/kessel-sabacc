package com.johnm.sabacc.backend.controller;

import com.johnm.sabacc.backend.domain.game.GamePreset;
import com.johnm.sabacc.backend.service.GamePresetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/presets")
public class GamePresetController {
    private GamePresetService presetService;

    public GamePresetController(GamePresetService presetService) {
        this.presetService = presetService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<GamePreset>> getSyndicates() {
        return ResponseEntity.ok().body(presetService.getGamePresets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamePreset> getSyndicate(@PathVariable int id) {
        return ResponseEntity.ok().body(presetService.getGamePreset(id));
    }
}
