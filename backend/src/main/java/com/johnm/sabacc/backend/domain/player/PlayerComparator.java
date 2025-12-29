package com.johnm.sabacc.backend.domain.player;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    @Override
    public int compare(Player p1, Player p2) {
        PlayerHandComparator comparator = new PlayerHandComparator();
        return comparator.compare(p1.getHand(), p2.getHand());
    }
}
