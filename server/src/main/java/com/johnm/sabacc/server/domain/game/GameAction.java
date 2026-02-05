package com.johnm.sabacc.server.domain.game;

import com.johnm.sabacc.server.util.EnumUtils;

public enum GameAction {
    STAND,
    DRAW_BLOOD_DRAW, DRAW_BLOOD_DISCARD, DRAW_SAND_DRAW, DRAW_SAND_DISCARD,
        REPLACE_WITH_DRAWN, DISCARD_DRAWN,
    PLAY_TOKEN,
    CHOOSE_IMPOSTER_VALUE, SET_PRIME_RANK;

    public static GameAction fromString(String family) {
        return valueOf(EnumUtils.sanitiseString(family));
    }
}
