package com.johnm.sabacc.backend.domain.game;

import com.johnm.sabacc.backend.util.EnumUtils;

public enum GameAction {
    STAND,
    DRAW_BLOOD_DRAW, DRAW_BLOOD_DISCARD, DRAW_SAND_DRAW, DRAW_SAND_DISCARD,
        REPLACE_WITH_DRAWN, DISCARD_DRAWN,
    PLAY_TOKEN;

    public static GameAction fromString(String family) {
        return valueOf(EnumUtils.sanitiseString(family));
    }
}
