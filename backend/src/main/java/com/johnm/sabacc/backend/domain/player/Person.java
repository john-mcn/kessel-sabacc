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

    public Person(String username, String password, String role, String name, int credits, List<ShiftToken> tokens) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.name = name;
        this.credits = credits;
        this.tokens = tokens;
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

    public PersonDTO toDto() {
        PersonDTO dto = new PersonDTO();
        dto.setName(name);
        dto.setCredits(credits);
        dto.setTokens(tokens == null? new ArrayList<>() : tokens.stream().map(ShiftToken::toString).toList());
        dto.setUsername(username);

        return dto;
    }

    @Override
    public String toString(){
        return name +": credits=" + credits +  ", tokens=" + tokens;
    }
}
