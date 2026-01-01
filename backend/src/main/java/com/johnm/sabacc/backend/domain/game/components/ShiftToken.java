package com.johnm.sabacc.backend.domain.game.components;

import com.johnm.sabacc.backend.util.EnumUtils;

//ONE MISSING
public enum ShiftToken {
    FREE_DRAW("Avoid the draw fee this turn"),
    EMBEZZLEMENT("Take 1 chip from each player's pot to your pot"),
    REFUND("Retrieve 2 chips from your pot"),
    EXTRA_REFUND("Retrieve 3 chips from your pot"),
    TARGET_AUDIT("A player you choose in stand is taxed 3 chips"),
    GENERAL_AUDIT("Other players in stand are taxed 2 chips"),
    TARGET_TARIFF("A player you choose is taxed 2 chips"),
    GENERAL_TARIFF("Other players are taxed 1 chip"),

    PRIME_SABACC("Roll 2 (d6) dice, pick one value as the new best Sabacc"),
    MAJOR_FRAUD("Set imposter value to 6 until next reveal"),
    MARKDOWN("Set Sylop value to 0 until next reveal"),
    COOK_THE_BOOKS("Invert Sabacc ranks until next reveal"),

    IMMUNITY("Prevent shift token effects against you until next reveal"),
    EMBARGO("Next player must stand"),
    EXHAUSTION("A player you choose must discard and draw a new hand"),
    DIRECT_TRANSACTION("Trade hands with a player you choose");

    private String description;

    ShiftToken(String description) {
        this.description = description;
    }

    public String getDescription() { return description; }
    public String toString() { return EnumUtils.sanitiseStringFromEnum(this.name()); }

    public static ShiftToken fromString(String token) {
        return valueOf(EnumUtils.sanitiseString(token));
    }
}
