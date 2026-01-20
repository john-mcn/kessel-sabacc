package com.johnm.sabacc.backend.controller;

import com.johnm.sabacc.backend.domain.Syndicate;
import com.johnm.sabacc.backend.service.SyndicateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/syndicates")
public class SyndicateController {
    private SyndicateService syndicateService;

    public SyndicateController(SyndicateService syndicateService) {
        this.syndicateService = syndicateService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<List<Syndicate>> getSyndicates() {
        return ResponseEntity.ok().body(syndicateService.getSyndicates());
    }

    @GetMapping("/{name}")
    public ResponseEntity<Syndicate> getSyndicate(@PathVariable String name) {
        return ResponseEntity.ok().body(syndicateService.getSyndicateByName(name));
    }
}
