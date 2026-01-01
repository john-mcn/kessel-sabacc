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
    private int currPlayerIndex;
    private int turnNumber;
    public Set<Player> inStand;

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

    public void setup() {
        List<Card> fullDeck = GameUtils.fullDeck();
        Collections.shuffle(fullDeck);

        ArrayList<Card> bloodCards = new ArrayList<>(fullDeck.stream().filter(
                Card::isBlood).toList());
        ArrayList<Card> sandCards = new ArrayList<>(fullDeck.stream().filter(
                Card::isSand).toList());

        // Give each player their starting hand, and remove it from the deck
        for (Player player : players) {
            Card bloodCard = bloodCards.remove(0);
            Card sandCard = sandCards.remove(1);
            player.setHand(new PlayerHand(bloodCard, sandCard));
        }

        bloodDraw = bloodCards;
        sandDraw = sandCards;
    }

    //NOTE assume current player is doing the action
    public void performAction(ActionRequestDTO action) {
        Player currPlayer = players.get(currPlayerIndex);

        List<Card> discardPile;
        switch (action.getActionEnum()) {
            case STAND:
                inStand.add(currPlayer);
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
                playToken(currPlayer, action.getTokenIndex());
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
    }

    private void drawCard (Player player, List<Card> drawPile) {
        if (drawPile.isEmpty()) {
            throw new IllegalActionException("Pile to draw card from is empty");
        }

        inStand.remove(player);
        player.spendChip(); //NOTE has validation
        Card drawn = drawPile.remove(0);
        player.setDrawnCard(drawn);
        //TODO updated player sent back, detect card drawn, handle y/n replace frontend
        System.out.print("Drawn card: |" + drawn);
    }

    private void replaceCard(Player player, List<Card> discardPile, boolean swapWithHand) {
        Card drawn = player.getDrawnCard();
        if (swapWithHand) {
            Card swappedWith = player.getHand().swapCard(player.getDrawnCard());
            discardPile.add(swappedWith);
        } else {
            discardPile.add(drawn);
        }
        player.setDrawnCard(null);
    }

    public void playToken(Player player, int index) {
        // If player has no tokens
        if (player.getSelectedTokens().stream().allMatch(Objects::isNull)) {
            throw new IllegalActionException("No tokens to play");
        }

        System.out.print("Available tokens:" + player.getSelectedTokens().toString() + " ");
        if (index < 0 || index >= players.size()) {
            throw new IllegalActionException("Invalid token index");
        }

        ShiftToken selected = player.getSelectedTokens().get(index);
        if (selected == null) {
            throw new IllegalActionException("Token already played");
        }

        player.getSelectedTokens().set(index, null);

        int amntToRefund;
        switch (selected) {
            case REFUND:
                amntToRefund = Math.min(player.getPot(), 2);
                player.setStock(player.getStock() + amntToRefund);
                player.setPot(player.getPot() - amntToRefund);
                System.out.println("Retrieved " + amntToRefund);
                break;
            case EXTRA_REFUND: //TODO repeated code
                amntToRefund = Math.min(player.getPot(), 3);
                player.setStock(player.getStock() + amntToRefund);
                player.setPot(player.getPot() - amntToRefund);
                System.out.println("Retrieved " + amntToRefund);
                break;
            default:
                System.err.println("INVALID TOKEN");
        }
    }

    public List<Player> revealCards() {
        System.out.println("\n=== Reveal phase ===");

        //NOTE for now, only need to set imposter value
        for (Player player : players) {
            PlayerHand playerHand = player.getHand();

            // Determine Imposter values
            //TODO shift token for imposter val = 6
            if (playerHand.getBloodCard().isImposter()) {
                int[] diceVals = GameUtils.rollImposterDice();
                System.out.println("Dice rolled: " + Arrays.toString(diceVals));
                //TODO for now just select the lowest of the two
                playerHand.setBloodCard(CardRank.fromInt(Math.min(diceVals[0], diceVals[1])));
            }
            if (playerHand.getSandCard().isImposter()) {
                int[] diceVals = GameUtils.rollImposterDice();
                System.out.println("Dice rolled: " + Arrays.toString(diceVals));
                //TODO for now just select the lowest of the two
                playerHand.setSandCard(CardRank.fromInt(Math.min(diceVals[0], diceVals[1])));
            }

            System.out.println("Player '" + player.getName() + "' hand=" + player.getHand());
        }

        return findWinners();
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
