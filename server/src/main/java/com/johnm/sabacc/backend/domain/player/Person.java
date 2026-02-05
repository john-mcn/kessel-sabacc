package com.johnm.sabacc.backend.domain.player;

import com.johnm.sabacc.backend.domain.game.components.ShiftToken;
import com.johnm.sabacc.backend.dto.player.PersonDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    @Id
    @Column(nullable = false, unique = true)
    protected String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String role;

    protected String name;
    protected int credits;
    protected List<ShiftToken> tokens;

    protected int huttRep;
    protected int pykeRep;
    protected int dawnRep;

    public Person() {}

    public Person(String name, int credits, List<ShiftToken> tokens) {
        this.name = name;
        this.credits = credits;
        this.tokens = tokens;
    }

    public Person(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Person(String username, String password, String role, String name,
                  int credits, List<ShiftToken> tokens,
                  int huttRep,  int pykeRep, int dawnRep) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.credits = credits;
        this.tokens = tokens;
        setHuttRep(huttRep);
        setPykeRep(pykeRep);
        setDawnRep(dawnRep);
    }

    // Signing in and security
    public String getUsername() { return username; }
    public void setUsername(String userName) { this.username = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Game and player
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public List<ShiftToken> getTokens() { return tokens; }
    public void setTokens(List<ShiftToken> tokens) { this.tokens = tokens; }

    // Reputation
    public int getHuttRep() { return huttRep; }
    public void setHuttRep(int huttRep) {
        if (huttRep >= 0) { this.huttRep = Math.min(huttRep, 100); }
        else { this.huttRep = 0; }
    }

    public int getPykeRep() { return pykeRep; }
    public void setPykeRep(int pykeRep) {
        if (pykeRep >= 0) { this.pykeRep = Math.min(pykeRep, 100); }
        else { this.pykeRep = 0; }
    }

    public int getDawnRep() { return dawnRep; }
    public void setDawnRep(int dawnRep) {
        if (dawnRep >= 0) { this.dawnRep = Math.min(dawnRep, 100); }
        else { this.dawnRep = 0; }
    }

    public PersonDTO toDto() {
        PersonDTO dto = new PersonDTO(
                name,
                credits,
                tokens == null? new ArrayList<>() : tokens.stream().map(ShiftToken::toString).toList()
        );
        dto.setUsername(username);
        dto.setHuttRep(huttRep);
        dto.setPykeRep(pykeRep);
        dto.setDawnRep(dawnRep);

        return dto;
    }

    @Override
    public String toString(){
        return name +": credits=" + credits +  ", tokens=" + tokens;
    }
}
