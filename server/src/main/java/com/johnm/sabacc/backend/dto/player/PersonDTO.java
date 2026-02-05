package com.johnm.sabacc.backend.dto.player;

import com.johnm.sabacc.backend.domain.game.components.ShiftToken;
import com.johnm.sabacc.backend.domain.player.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {
    private String username;

    private String name;
    private int credits;
    private List<String> tokens;

    private int huttRep, pykeRep, dawnRep;

    public PersonDTO() {}

    public PersonDTO(String name, int credits, List<String> tokens) {
        this.name = name;
        this.credits = credits;
        this.tokens = tokens;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // Game and player
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public List<String> getTokens() { return tokens; }
    public void setTokens(List<String> tokens) { this.tokens = tokens; }

    // Reputation
    public int getHuttRep() { return huttRep; }
    public void setHuttRep(int huttRep) { this.huttRep = Math.min(huttRep, 100); }

    public int getPykeRep() { return pykeRep; }
    public void setPykeRep(int pykeRep) { this.pykeRep = Math.min(pykeRep, 100); }

    public int getDawnRep() { return dawnRep; }
    public void setDawnRep(int dawnRep) { this.dawnRep = Math.min(dawnRep, 100); }

    //NOTE does not set username, method used in GameHistoryDTO
    public Person toEntity() {
        Person person = new Person(
                name,
                credits,
                tokens.stream().map(ShiftToken::fromString).toList());
        person.setHuttRep(huttRep);
        person.setPykeRep(pykeRep);
        person.setDawnRep(dawnRep);
        return person;
    }
}
