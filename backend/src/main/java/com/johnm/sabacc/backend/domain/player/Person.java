package com.johnm.sabacc.backend.domain.player;

import com.johnm.sabacc.backend.domain.components.ShiftToken;

import java.util.List;

public class Person {
    protected String name;
    protected int credits;
    protected List<ShiftToken> tokens;

    public Person(String name, int credits, List<ShiftToken> tokens) {
        this.name = name;
        this.credits = credits;
        this.tokens = tokens;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public List<ShiftToken> getTokens() { return tokens; }
    public void setTokens(List<ShiftToken> tokens) { this.tokens = tokens; }
}
