package com.johnm.sabacc.backend.domain.game.components;

import com.johnm.sabacc.backend.util.EnumUtils;

public enum CardFamily {
    BLOOD,
    SAND;

    public static CardFamily fromString(String family) {
        return valueOf(EnumUtils.sanitiseString(family));
    }
}
