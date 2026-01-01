package com.johnm.sabacc.backend.domain.game.components;

import com.johnm.sabacc.backend.util.EnumUtils;

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

    public static CardRank fromString(String rank) {
        return switch (rank) {
            case "1" -> ONE;
            case "2" -> TWO;
            case "3" -> THREE;
            case "4" -> FOUR;
            case "5" -> FIVE;
            case "6" -> SIX;
            default -> valueOf(EnumUtils.sanitiseString(rank));
        };
    }

    //NOTE only ranks ONE to SIX (cannot convert to Sylop or Imposter)
    public static CardRank fromInt(int rank) {
        return switch (rank) {
            case 1 -> ONE;
            case 2 -> TWO;
            case 3 -> THREE;
            case 4 -> FOUR;
            case 5 -> FIVE;
            case 6 -> SIX;
            default -> throw new IllegalArgumentException("Rank could not be converted to int: '" + rank + "'");
        };
    }
}
