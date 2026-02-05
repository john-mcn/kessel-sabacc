package com.johnm.sabacc.server.domain.game.components;

import com.johnm.sabacc.server.util.EnumUtils;

public enum CardFamily {
    BLOOD,
    SAND;

    public static CardFamily fromString(String family) {
        return valueOf(EnumUtils.sanitiseString(family));
    }
}
