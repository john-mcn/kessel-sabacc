package com.johnm.sabacc.backend.domain.components;

public enum CardRank {
    ONE, TWO, THREE, FOUR, FIVE, SIX,
    SYLOP, IMPOSTER;

    public int toInt() {
        return switch (this) {
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            case FIVE -> 5;
            case SIX -> 6;
            default -> throw new IllegalArgumentException("Rank could not be converted to int: '" + name() + "'");
        };
    }
}
