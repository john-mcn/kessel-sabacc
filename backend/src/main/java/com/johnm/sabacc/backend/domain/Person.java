package com.johnm.sabacc.backend.domain;

import com.johnm.sabacc.backend.domain.components.ShiftToken;

import java.util.List;

public class Person {
    private String name;
    private int credits;
    private List<ShiftToken> tokens;

    public Person(String name, int credits, List<ShiftToken> tokens) {
        this.name = name;
        this.credits = credits;
        this.tokens = tokens;
    }
}
