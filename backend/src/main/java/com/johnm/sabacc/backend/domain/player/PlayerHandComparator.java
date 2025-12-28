package com.johnm.sabacc.backend.domain.player;

import com.johnm.sabacc.backend.domain.components.Card;
import com.johnm.sabacc.backend.domain.components.CardRank;

import java.util.Comparator;

public class PlayerHandComparator implements Comparator<PlayerHand> {

    @Override
    public int compare(PlayerHand h1, PlayerHand h2) {
        Card card1b = h1.getBloodCard();
        Card card1s = h1.getSandCard();
        Card card2b = h2.getBloodCard();
        Card card2s = h2.getSandCard();

        // Pure Sabacc (two Sylops) will always be best
        boolean hand1Pure = card1b.getRank() == CardRank.SYLOP
                && card1s.getRank() == CardRank.SYLOP;
        boolean hand2Pure = card2b.getRank() == CardRank.SYLOP
                && card2s.getRank() == CardRank.SYLOP;
        if (hand1Pure && hand2Pure) { return 0; }
        else if (hand1Pure && !hand2Pure) { return -1; }
        else if (!hand1Pure && hand2Pure) { return 1; }

        // Sabacc (same rank) is second best
        boolean hand1Sabacc = card1b.getRank() == card1s.getRank()
                || (card1b.getRank().equals(CardRank.SYLOP) || card1s.getRank().equals(CardRank.SYLOP));
        boolean hand2Sabacc = card2b.getRank() == card2s.getRank()
                || (card2b.getRank().equals(CardRank.SYLOP) || card2s.getRank().equals(CardRank.SYLOP));
        if (hand1Sabacc && !hand2Sabacc) { return -1; }
        else if (!hand1Sabacc && hand2Sabacc) { return 1; }
        // If both (impure) Sabacc, lower rank is better
        else if (hand1Sabacc && hand2Sabacc) {
            // Get the non-sylop rank
            int hand1Rank = (card1b.getRank().equals(CardRank.SYLOP))? card1s.getRank().toInt() : card1b.getRank().toInt();
            int hand2Rank = (card2b.getRank().equals(CardRank.SYLOP))? card2s.getRank().toInt() : card2b.getRank().toInt();
            if (hand1Rank == hand2Rank) { return 0; }
            else if (hand1Rank < hand2Rank) { return -1; }
            else if (hand1Rank > hand2Rank) { return 1; }
        }

        // Lower rank difference is third best (can use toInt because no Sabacc <-> no Sylop)
        // - if hand diff is same, consider rank sum
        int hand1Diff = Math.abs(card1b.getRank().toInt() - card1s.getRank().toInt());
        int hand2Diff = Math.abs(card2b.getRank().toInt() - card2s.getRank().toInt());
        if (hand1Diff < hand2Diff) { return -1; }
        else if (hand1Diff > hand2Diff) { return 1; }


        // Lower rank sum should is fourth best
        int hand1Sum = card1b.getRank().toInt() + card1s.getRank().toInt();
        int hand2Sum = card2b.getRank().toInt() + card2s.getRank().toInt();
        if (hand1Sum == hand2Sum) { return 0; }
        else if (hand1Sum < hand2Sum) { return -1; }
        else if (hand1Sum > hand2Sum) { return 1; } //TODO redundant comparison but for clarity

        //TODO ignore imposter rank
        return 0;
    }
}
