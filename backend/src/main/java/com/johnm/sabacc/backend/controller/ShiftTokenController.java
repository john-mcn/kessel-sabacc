package com.johnm.sabacc.backend.controller;

import com.johnm.sabacc.backend.domain.game.components.ShiftTokenEntity;
import com.johnm.sabacc.backend.service.ShiftTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tokens")
public class ShiftTokenController {
    private ShiftTokenService shiftTokenService;

    public ShiftTokenController(ShiftTokenService shiftTokenService) {
        this.shiftTokenService = shiftTokenService;
    }

    @GetMapping({"","/"})
    public ResponseEntity<List<ShiftTokenEntity>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(shiftTokenService.getAll());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ShiftTokenEntity> getByName(@PathVariable String name) {
        return ResponseEntity.status(HttpStatus.OK).body(shiftTokenService.getByName(name));
    }

    @PostMapping
    public ShiftTokenEntity createShiftToken(@RequestBody ShiftTokenEntity shiftTokenEntity) {
        return shiftTokenService.createShiftToken(shiftTokenEntity);
    }
}
