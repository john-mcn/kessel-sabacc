package com.johnm.sabacc.server.domain.player;

import com.johnm.sabacc.server.domain.game.components.Card;
import com.johnm.sabacc.server.domain.game.components.CardFamily;
import com.johnm.sabacc.server.domain.game.components.CardRank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerHandComparatorTest {

    private final PlayerHandComparator comparator = new PlayerHandComparator(CardRank.SYLOP);

    @Test
    void pureSabacc_shouldWin() {
        PlayerHand hand1 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.SYLOP),
                new Card(CardFamily.SAND, CardRank.SYLOP));
        PlayerHand hand2 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.SYLOP),
                new Card(CardFamily.SAND, CardRank.ONE));

        int comparison = comparator.compare(hand1, hand2);

        assertTrue(comparison < 0, "Pure Sabacc should always win (normally)");
    }

    @Test
    void pureSabacc_shouldBeatPrime() {
        PlayerHandComparator comparator2 = new PlayerHandComparator(CardRank.FOUR);

        PlayerHand hand1 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.SYLOP),
                new Card(CardFamily.SAND, CardRank.SYLOP));
        PlayerHand hand2 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.SYLOP),
                new Card(CardFamily.SAND, CardRank.FOUR));

        int comparison = comparator2.compare(hand1, hand2);

        assertTrue(comparison < 0, "Pure Sabacc should win against Prime Sabacc");
    }

    @Test
    void impureSylopSabacc_shouldEqualNormalSabacc() {
        PlayerHand hand1 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.SYLOP),
                new Card(CardFamily.SAND, CardRank.ONE));
        PlayerHand hand2 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.ONE),
                new Card(CardFamily.SAND, CardRank.ONE));

        int comparison = comparator.compare(hand1, hand2);

        assertEquals(0, comparison, "Sylop card should match rank");
    }

    @Test
    void lowerSabacc_shouldWin() {
        PlayerHand hand1 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.ONE),
                new Card(CardFamily.SAND, CardRank.ONE));
        PlayerHand hand2 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.TWO),
                new Card(CardFamily.SAND, CardRank.TWO));

        int comparison = comparator.compare(hand1, hand2);

        assertTrue(comparison < 0, "Lower Sabacc should win");
    }

    @Test
    void lowerSabaccVsSylop_shouldWin() {
        PlayerHand hand1 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.ONE),
                new Card(CardFamily.SAND, CardRank.ONE));
        PlayerHand hand2 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.TWO),
                new Card(CardFamily.SAND, CardRank.SYLOP));

        int comparison = comparator.compare(hand1, hand2);

        assertTrue(comparison < 0, "Lower Sabacc should win against a worse hand with ONE Sylop");
    }

    @Test
    void sabacc_shouldWin() {
        PlayerHand hand1 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.ONE),
                new Card(CardFamily.SAND, CardRank.ONE));
        PlayerHand hand2 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.ONE),
                new Card(CardFamily.SAND, CardRank.TWO));

        int comparison = comparator.compare(hand1, hand2);

        assertTrue(comparison < 0, "Sabacc should win (over non-Sabacc)");
    }

    @Test
    void lowerRankDifference_shouldWin() {
        PlayerHand hand1 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.ONE),
                new Card(CardFamily.SAND, CardRank.TWO));
        PlayerHand hand2 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.ONE),
                new Card(CardFamily.SAND, CardRank.THREE));

        int comparison = comparator.compare(hand1, hand2);

        assertTrue(comparison < 0, "Lower rank difference should win (over higher difference)");
    }

    @Test
    void lowerRankSum_shouldWin() {
        PlayerHand hand1 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.ONE),
                new Card(CardFamily.SAND, CardRank.TWO));
        PlayerHand hand2 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.THREE),
                new Card(CardFamily.SAND, CardRank.FOUR));

        int comparison = comparator.compare(hand1, hand2);
        System.out.println(comparison);

        assertTrue(comparison < 0, "Lower rank sum should win with same rank difference");
    }
    @Test
    void bestSabacc_shouldWin() {
        PlayerHandComparator comparator2 = new PlayerHandComparator(CardRank.FOUR);

        PlayerHand hand1 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.FOUR),
                new Card(CardFamily.SAND, CardRank.FOUR));
        PlayerHand hand2 = new PlayerHand(
                new Card(CardFamily.BLOOD, CardRank.SYLOP),
                new Card(CardFamily.SAND, CardRank.ONE));

        int comparison = comparator2.compare(hand1, hand2);
        System.out.println(comparison);

        assertTrue(comparison < 0, "Best Sabacc should win");
    }
}
