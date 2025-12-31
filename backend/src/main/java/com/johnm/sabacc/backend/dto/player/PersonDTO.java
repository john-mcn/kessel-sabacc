package com.johnm.sabacc.backend.dto.player;

import com.johnm.sabacc.backend.domain.components.ShiftToken;
import com.johnm.sabacc.backend.domain.player.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    private String name;
    private int credits;
    private List<String> tokens;

    public PersonDTO() {}

    public PersonDTO(String name, int credits, List<String> tokens) {
        this.name = name;
        this.credits = credits;
        this.tokens = tokens;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public List<String> getTokens() { return tokens; }
    public void setTokens(List<String> tokens) { this.tokens = tokens; }

    public Person toEntity() {
        Person entity = new Person();
        entity.setName(name);
        entity.setCredits(credits);
        entity.setTokens(tokens == null? new ArrayList<>() : tokens.stream().map(ShiftToken::fromString).toList());

        return entity;
    }
}
