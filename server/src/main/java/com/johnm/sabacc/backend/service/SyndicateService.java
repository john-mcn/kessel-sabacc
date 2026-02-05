package com.johnm.sabacc.backend.service;

import com.johnm.sabacc.backend.domain.Syndicate;
import com.johnm.sabacc.backend.exceptions.EntityNotFoundException;
import com.johnm.sabacc.backend.repository.SyndicateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SyndicateService {
    private SyndicateRepository syndicateRepository;

    public SyndicateService(SyndicateRepository syndicateRepository) {
        this.syndicateRepository = syndicateRepository;
    }

    public List<Syndicate> getSyndicates() {
        return syndicateRepository.findAll();
    }

    public Syndicate getSyndicateByName(String name){
        return syndicateRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No syndicate with name '" + name + "'"));
    }
}
