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
}
