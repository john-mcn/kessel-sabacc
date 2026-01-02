package com.johnm.sabacc.backend.domain.game;

import com.johnm.sabacc.backend.domain.game.components.Card;
import com.johnm.sabacc.backend.domain.game.components.CardRank;
import com.johnm.sabacc.backend.domain.game.components.ShiftToken;
import com.johnm.sabacc.backend.domain.player.*;
import com.johnm.sabacc.backend.dto.game.ActionRequestDTO;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;
import com.johnm.sabacc.backend.util.GameUtils;

import java.util.*;

public class GameRound {
    private SabaccGame game;
    private List<Player> players;
    private List<ShiftToken> tokensActive;
    private List<Card> bloodDraw, sandDraw; // face down draw piles
    private List<Card> bloodDiscard, sandDiscard; // face up discard piles
    private CardRank bestSabacc;
    private Integer imposterValue;
    private int currPlayerIndex;
    private int turnNumber;
    private Set<Player> inStand;
    private List<Player> finalOrder;
    private List<Player> winners;
    // Temporary
    private List<Integer> numbersRolled;

    public GameRound() {}

    public GameRound(SabaccGame game) {
        this.game = game;
        players = game.getPlayers();
        tokensActive = new ArrayList<>();
        bloodDraw = new ArrayList<>();
        sandDraw = new ArrayList<>();
        bloodDiscard = new ArrayList<>();
        sandDiscard = new ArrayList<>();
        bestSabacc = CardRank.SYLOP;
        currPlayerIndex = 0;
        turnNumber = 0;
        inStand = new HashSet<>();
    }

    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }

    public List<ShiftToken> getTokensActive() { return tokensActive; }
    public void setTokensActive(List<ShiftToken> tokensActive) { this.tokensActive = tokensActive; }

    public List<Card> getBloodDraw() { return bloodDraw; }
    public void setBloodDraw(List<Card> bloodDraw) { this.bloodDraw = bloodDraw; }

    public List<Card> getSandDraw() { return sandDraw; }
    public void setSandDraw(List<Card> sandDraw) { this.sandDraw = sandDraw; }

    public List<Card> getBloodDiscard() { return bloodDiscard; }
    public Card getTopBloodDiscard() { return bloodDiscard.get(0); }
    public List<Card> getSandDiscard() { return sandDiscard; }
    public Card getTopSandDiscard() { return sandDiscard.get(0); }

    public int getTurnNumber() { return turnNumber; }

    public int getCurrPlayerIndex() { return currPlayerIndex; }

    public Set<Player> getInStand() { return inStand; }
    public void setInStand(Set<Player> inStand) { this.inStand = inStand; }

    public CardRank getBestSabacc() { return bestSabacc; }

    // End of round
    public List<Player> getFinalOrder() { return finalOrder; }
    public void setFinalOrder(List<Player> finalOrder) { this.finalOrder = finalOrder; }

    public List<Player> getWinners() { return winners; }
    public void setWinners(List<Player> winners) { this.winners = winners; }

    // Temporary info
    public List<Integer> getNumbersRolled() { return numbersRolled; }
    public void setNumbersRolled(List<Integer> numbersRolled) { this.numbersRolled = numbersRolled; }

    public void setup() {
        List<Card> fullDeck = GameUtils.fullDeck();
        Collections.shuffle(fullDeck);

        ArrayList<Card> bloodCards = new ArrayList<>(fullDeck.stream().filter(
                Card::isBlood).toList());
        ArrayList<Card> sandCards = new ArrayList<>(fullDeck.stream().filter(
                Card::isSand).toList());

        turnNumber = 1;

        // Give each player their starting hand, and remove it from the deck
        for (Player player : players) {
            Card bloodCard = bloodCards.remove(0);
            Card sandCard = sandCards.remove(1);
            player.setHand(new PlayerHand(bloodCard, sandCard));
        }

        bloodDraw = bloodCards;
        sandDraw = sandCards;

        System.out.println("Round " + game.getRoundNumber() +" set up");
    }

    //NOTE assume current player is doing the action
    public void performAction(ActionRequestDTO action) {
        Player currPlayer = players.get(currPlayerIndex);

        List<Card> discardPile;
        Integer valueChosen = action.getSelectedValue();
        switch (action.getActionEnum()) {
            case STAND:
                inStand.add(currPlayer);
                System.out.println(currPlayer.getName() + " stands");
                endTurn();
                break;
            //TODO enforce only drawing 1 card per turn
            case DRAW_BLOOD_DRAW:
                drawCard(currPlayer, bloodDraw);
                break;
            case DRAW_BLOOD_DISCARD:
                drawCard(currPlayer, bloodDiscard);
                break;
            case DRAW_SAND_DRAW:
                drawCard(currPlayer, sandDraw);
                break;
            case DRAW_SAND_DISCARD:
                drawCard(currPlayer, sandDiscard);
                break;
            case REPLACE_WITH_DRAWN:
                discardPile = (currPlayer.getDrawnCard().isBlood())? bloodDiscard: sandDiscard;
                replaceCard(currPlayer, discardPile, true);
                endTurn();
                break;
            case DISCARD_DRAWN:
                discardPile = (currPlayer.getDrawnCard().isBlood())? bloodDiscard: sandDiscard;
                replaceCard(currPlayer, discardPile, false);
                endTurn();
                break;
            //TODO enforce only playing 1 token per turn
            case PLAY_TOKEN:
                playToken(currPlayer, action.getTokenIndex(), action);
                break;
            case CHOOSE_IMPOSTER_VALUE:
                // Choose imposter value upon reveal
                break;
            //TODO useless
            case SET_PRIME_RANK:
                bestSabacc = CardRank.fromInt(valueChosen);
                break;
            default:
                throw new IllegalActionException("Invalid action '" + action + "'");
        }
    }

    public void endTurn() {
        if (currPlayerIndex == players.size() - 1) {
            turnNumber++;
            currPlayerIndex = 0;
        } else {
            currPlayerIndex++;
        }

        if (turnNumber == 4) {
            revealCards();
        }
    }

    private void drawCard (Player player, List<Card> drawPile) {
        if (drawPile.isEmpty()) {
            throw new IllegalActionException("Pile to draw card from is empty");
        }

        inStand.remove(player);
        if (tokensActive.contains(ShiftToken.FREE_DRAW)) {
            tokensActive.remove(ShiftToken.FREE_DRAW);
        } else {
            player.spendChip();
        }
        Card drawn = drawPile.remove(0);
        player.setDrawnCard(drawn);
        //TODO updated player sent back, detect card drawn, handle y/n replace frontend
        System.out.print(player.getName() + " drew " + drawn + "...");
    }

    private void replaceCard(Player player, List<Card> discardPile, boolean swapWithHand) {
        Card drawn = player.getDrawnCard();
        if (swapWithHand) {
            Card swappedWith = player.getHand().swapCard(player.getDrawnCard());
            discardPile.add(swappedWith);
            System.out.println("swapped");
        } else {
            discardPile.add(drawn);
            System.out.println("discarded");
        }
        player.setDrawnCard(null);
    }

    public void playToken(Player player, int index, ActionRequestDTO action) {
        // If player has no tokens
        if (player.getSelectedTokens().stream().allMatch(Objects::isNull)) {
            throw new IllegalActionException("No tokens to play");
        }

        if (index < 0 || index >= player.getSelectedTokens().size()) {
            throw new IllegalActionException("Invalid token index");
        }

        ShiftToken selected = player.getSelectedTokens().get(index);
        if (selected == null) {
            throw new IllegalActionException("Token already played");
        }

        player.getSelectedTokens().set(index, null);

        int amntToAdd;
        switch (selected) {
            case FREE_DRAW:
                // Avoid the draw fee this turn
                tokensActive.add(selected);
                break;
            case EMBEZZLEMENT:
                // Take 1 chip from each player's pot to your pot
                amntToAdd = 0;
                for (Player p : players) {
                    // Take 1 (or 0) from player's pot
                    amntToAdd += Math.min(1, p.getPot());
                    if (p.getPot() > 0) { p.setPot(p.getPot() - 1); }
                }
                player.setPot(player.getPot() + amntToAdd);
                break;
            case REFUND:
                // Retrieve 2 chips from your pot
                amntToAdd = Math.min(player.getPot(), 2);
                player.setStock(player.getStock() + amntToAdd);
                player.setPot(player.getPot() - amntToAdd);
                System.out.println("Retrieved " + amntToAdd);
                break;
            case EXTRA_REFUND: //TODO repeated code
                // Retrieve 3 chips from your pot
                amntToAdd = Math.min(player.getPot(), 3);
                player.setStock(player.getStock() + amntToAdd);
                player.setPot(player.getPot() - amntToAdd);
                System.out.println("Retrieved " + amntToAdd);
                break;
            case GENERAL_AUDIT:
                // Other players in stand are taxed 2 chips
                for (Player p : inStand) { p.tax(2); }
                break;
            case GENERAL_TARIFF:
                // Other players are taxed 1 chip
                for (Player p : players) { p.tax(1); }
                break;
            case PRIME_SABACC:
                // Roll 2 (d6) dice, pick one value as the new best Sabacc
                tokensActive.add(selected);
                bestSabacc = CardRank.fromInt(action.getSelectedValue());
                break;
            case MAJOR_FRAUD:
                // Set imposter value to 6 until next reveal
                tokensActive.add(selected);
                imposterValue = 6;
                break;
            default:
                System.err.println("INVALID TOKEN");
        }

        player.getSelectedTokens().remove(index);
        System.out.println(player.getName() + " played token " + selected.name());

        // TARGET_AUDIT("A player you choose in stand is taxed 3 chips"),
        // TARGET_TARIFF("A player you choose is taxed 2 chips"),
        //
        // MARKDOWN("Set Sylop value to 0 until next reveal"),
        // COOK_THE_BOOKS("Invert Sabacc ranks until next reveal"),
        //
        // IMMUNITY("Prevent shift token effects against you until next reveal"),
        // EMBARGO("Next player must stand"),
        // EXHAUSTION("A player you choose must discard and draw a new hand"),
        // DIRECT_TRANSACTION("Trade hands with a player you choose");
    }

    public List<Player> revealCards() {
        System.out.println("\n=== Reveal phase ===");

        for (Player player : players) {
            PlayerHand playerHand = player.getHand();

            // Determine Imposter values
            if (playerHand.getBloodCard().isImposter()) {
                if (imposterValue == null) {
                    int[] diceVals = GameUtils.roll2d6();
                    System.out.println("Imposter dice rolled: " + Arrays.toString(diceVals));
                    //TODO for now just select the lowest of the two
                    playerHand.setBloodCard(CardRank.fromInt(Math.min(diceVals[0], diceVals[1])));
                } else {
                    playerHand.setBloodCard(CardRank.fromInt(imposterValue));
                }
            }
            if (playerHand.getSandCard().isImposter()) {
                if (imposterValue == null) {
                    int[] diceVals = GameUtils.roll2d6();
                    System.out.println("Imposter d rolled: " + Arrays.toString(diceVals));
                    // TODO for now just select the lowest of the two
                    playerHand.setSandCard(CardRank.fromInt(Math.min(diceVals[0], diceVals[1])));
                } else {
                    playerHand.setSandCard(CardRank.fromInt(imposterValue));
                }
            }

            imposterValue = null;
            tokensActive = null;

            System.out.println("Player '" + player.getName() + "' hand=" + player.getHand());
        }

        //NOTE accounts for sylops?
        PlayerHand winningHand = findWinners().get(0).getHand();
        for  (Player player : players) {
            // Winners retrieve all invested chips
            if (player.getHand().equals(winningHand)) {
                player.setStock(player.getStock() + player.getPot());
            // Losers with Sabacc hand lose 1 chip
            } else if (player.getHand().isSabacc()) {
                if (player.getStock() > 0) { player.spendChip(); }
            // Losers without Sabacc hand lose chips equal to rank difference
            } else {
                int rankDiff = player.getHand().rankDifference();
                player.setStock(Math.max(player.getStock() - rankDiff, 0));
            }

            player.setPot(0);
        }

        game.setRoundNumber(game.getRoundNumber() + 1);
        finalOrder = sortPlayers();
        winners = findWinners();

        return winners;
    }

    //TODO account for token effects (tokensActive)
    public List<Player> sortPlayers() {
        ArrayList<Player> playerLst = new ArrayList<>(players);
        playerLst.sort(new PlayerComparator(bestSabacc));
        return playerLst;
    }

    public List<Player> findWinners() {
        PlayerComparator playerComparator = new PlayerComparator(bestSabacc);
        List<Player> sortedPlayers = sortPlayers();

        Player winner = sortedPlayers.get(0);
        List<Player> winners = new ArrayList<>(sortedPlayers.stream().filter(p -> playerComparator.compare(p, winner) == 0).toList());

        return winners;
    }

    //TODO expand
    @Override
    public String toString() {
        String sandDiscardStr = (!sandDiscard.isEmpty())? sandDiscard.get(0).toString() : "";
        String bloodDiscardStr = (!bloodDiscard.isEmpty())? bloodDiscard.get(0).toString() : "";
        return "Top of discard: sand=[" + sandDiscardStr + "], blood=[" + bloodDiscardStr + "] | In stand: " + inStand;
    }
}
