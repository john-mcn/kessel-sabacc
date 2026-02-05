package com.johnm.sabacc.server.domain.player;

import com.johnm.sabacc.server.domain.game.components.CardRank;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    private CardRank bestSabacc;

    public PlayerComparator(CardRank bestSabacc) {
        this.bestSabacc = bestSabacc;
    }

    @Override
    public int compare(Player p1, Player p2) {
        PlayerHandComparator comparator = new PlayerHandComparator(bestSabacc);
        return comparator.compare(p1.getHand(), p2.getHand());
    }
}
