package com.johnm.sabacc.backend.domain.player;

import com.johnm.sabacc.backend.domain.game.components.Card;
import com.johnm.sabacc.backend.domain.game.components.CardRank;

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
    public int compare(PlayerHand h1, PlayerHand h2) {
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

        // If best Sabacc isn't Sylop, Sylop Sabacc wins over numerical Sabacc
        boolean hand1SylopSabacc = hand1Same && card1b.isSylop();
        boolean hand2SylopSabacc = hand2Same && card2b.isSylop();
        if (hand1SylopSabacc && hand2SylopSabacc) { return 0; }
        else if (hand1SylopSabacc && !hand2SylopSabacc) { return -1; }
        else if (!hand1SylopSabacc && hand2SylopSabacc) { return 1; }

        // Sabacc (same rank) is second best
        if (hand1Sabacc && !hand2Sabacc) { return -1; }
        else if (!hand1Sabacc && hand2Sabacc) { return 1; }
        // If both (impure) Sabacc, lower rank is better
        else if (hand1Sabacc && hand2Sabacc) {
            // Get the non-sylop rank
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
