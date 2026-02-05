package com.johnm.sabacc.server.domain.player;

import com.johnm.sabacc.server.domain.game.components.Card;
import com.johnm.sabacc.server.domain.game.components.CardRank;

import java.util.Comparator;

public class PlayerHandComparator implements Comparator<PlayerHand> {
    private CardRank bestSabacc;

    public PlayerHandComparator(CardRank bestSabacc) {
        this.bestSabacc = bestSabacc;
    }

    /* Winning order:
     Best Sabacc -> Sylop Sabacc -> Sabacc -> Lowest rank diff -> Lowest rank sum
    */
    @Override
    public int compare(PlayerHand hand1, PlayerHand hand2) {
        // Use a sanitised version that sets impure Sylop cards as the pairing number
        PlayerHand h1 = hand1;
        if (h1.getBloodCard().isSylop() && !h1.getSandCard().isSylop()) {
            h1.setBloodCard(h1.getSandCard().getRank());
        } else if (!h1.getBloodCard().isSylop() && h1.getSandCard().isSylop()) {
            h1.setSandCard(h1.getBloodCard().getRank());
        }
        PlayerHand h2 = hand2;
        if (h2.getBloodCard().isSylop() && !h2.getSandCard().isSylop()) {
            h2.setBloodCard(h2.getSandCard().getRank());
        } else if (!h2.getBloodCard().isSylop() && h2.getSandCard().isSylop()) {
            h2.setSandCard(h2.getBloodCard().getRank());
        }

        Card card1b = h1.getBloodCard();
        Card card1s = h1.getSandCard();
        Card card2b = h2.getBloodCard();
        Card card2s = h2.getSandCard();

        boolean hand1Same = card1b.getRank() == card1s.getRank();
        boolean hand2Same = card2b.getRank() == card2s.getRank();

        boolean hand1Sabacc = hand1Same || (card1b.isSylop() || card1s.isSylop());
        boolean hand2Sabacc = hand2Same || (card2b.isSylop() || card2s.isSylop());

        // Prime Sabacc is an unbeatable hand
        boolean hand1Prime = hand1Same && card1b.isSylop();
        boolean hand2Prime = hand2Same && card2b.isSylop();
        if (hand1Prime && hand2Prime) { return 0; }
        else if (hand1Prime && !hand2Prime) { return -1; }
        else if (!hand1Prime && hand2Prime) { return 1; }

        // Otherwise, best Sabacc will always win
        boolean hand1Best = (bestSabacc.equals(CardRank.SYLOP) && hand1Same && card1b.isSylop())
                || (!bestSabacc.equals(CardRank.SYLOP) && hand1Sabacc && card1b.getRank().equals(bestSabacc) || card1s.getRank().equals(bestSabacc));
        boolean hand2Best = (bestSabacc.equals(CardRank.SYLOP) && hand2Same && card2b.isSylop())
                || (!bestSabacc.equals(CardRank.SYLOP) && hand2Sabacc && card2b.getRank().equals(bestSabacc) || card2s.getRank().equals(bestSabacc));
        if (hand1Best && hand2Best) { return 0; }
        else if (hand1Best && !hand2Best) { return -1; }
        else if (!hand1Best && hand2Best) { return 1; }

        //NOTE Counts i.e. B=4 & S=4 equal to B=4 & S=Sabacc

        // Sabacc (same rank) is second best
        if (hand1Sabacc && !hand2Sabacc) { return -1; }
        else if (!hand1Sabacc && hand2Sabacc) { return 1; }
        // If both (impure) Sabacc, lower rank is better
        else if (hand1Sabacc && hand2Sabacc) {
            // Get the non-Sylop rank
            int hand1Rank = (card1b.isSylop())? card1s.getRank().toInt() : card1b.getRank().toInt();
            int hand2Rank = (card2b.isSylop())? card2s.getRank().toInt() : card2b.getRank().toInt();
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
