package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.SabaccGame;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.SabaccGameRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SabaccGameService {
    private SabaccGameRepository sabaccGameRepository;

    public SabaccGameService(SabaccGameRepository sabaccGameRepository) {
        this.sabaccGameRepository = sabaccGameRepository;
    }

    public List<SabaccGame> getAll() {
        return sabaccGameRepository.findAll();
    }

    public SabaccGame getById(Integer id) {
        return sabaccGameRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("No Sabacc Game with id " + id + " found"));
    }

    public SabaccGame createGame(SabaccGame sabaccGame) {
        return sabaccGameRepository.save(sabaccGame);
    }
}
