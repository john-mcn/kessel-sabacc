package com.johnm.sabacc.backend.domain;

import com.johnm.sabacc.backend.domain.components.Card;
import com.johnm.sabacc.backend.domain.components.CardFamily;
import com.johnm.sabacc.backend.domain.components.CardRank;
import com.johnm.sabacc.backend.domain.components.ShiftToken;
import com.johnm.sabacc.backend.domain.player.*;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;
import com.johnm.sabacc.backend.util.GameUtils;

import java.util.*;

public class GameRound {
    private SabaccGame game;
    private Player[] players;
    private List<ShiftToken> tokensActive;
    private List<Card> bloodDraw, sandDraw; // face down draw piles
    private List<Card> bloodDiscard, sandDiscard; // face up discard piles
    private CardRank bestSabacc;
    private int currPlayerIndex;
    private int turnNumber;
    public Set<Player> inStand;


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

    public Player[] getPlayers() { return players; }
    public void setPlayers(Player[] players) { this.players = players; }

    public List<ShiftToken> getTokensActive() { return tokensActive; }
    public void setTokensActive(List<ShiftToken> tokensActive) { this.tokensActive = tokensActive; }

    public List<Card> getBloodDraw() { return bloodDraw; }
    public void setBloodDraw(List<Card> bloodDraw) { this.bloodDraw = bloodDraw; }

    public List<Card> getSandDraw() { return sandDraw; }
    public void setSandDraw(List<Card> sandDraw) { this.sandDraw = sandDraw; }

    public List<Player> runFullgame() {
        setup();

        turnNumber = 1;
        System.out.println("=== Turn " + turnNumber + " ===");

        // Perform 3 turns, or until all players stand
        while (turnNumber < 4 && inStand.size() < players.length) {
            System.out.println();
            System.out.println("Players in stand = " + inStand.stream().map(Person::getName).toList());
            System.out.println("Discard piles: "
                    + "blood=" + (bloodDiscard.isEmpty() ? "[]" : "[" + bloodDiscard.get(0)) + "]]]"
                    + " sand=" + (sandDiscard.isEmpty() ? "[]" : "[" + sandDiscard.get(0)) + "]]]");
            System.out.println("Current player: " + players[currPlayerIndex]);

            performTurn(players[currPlayerIndex]);
            // If every player had a go, enter new turn, otherwise go to next player
            if (currPlayerIndex == players.length - 1) {
                System.out.println("== Turn " + turnNumber + " ===");
                turnNumber++;
                currPlayerIndex = 0;
            } else {
                currPlayerIndex++;
            }
        }

        List<Player> winners = revealCards();
        System.out.println("Winners=" + winners);
        return winners;
    }

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

    public void performTurn(Player player) {
        Scanner reader = new Scanner(System.in);

        System.out.print("1=Stand, Draw blood..(2=..from draw, 3=..from discard), Draw sand..(4=..from draw, 5=..from discard), 6=Play shift token");
        String move = reader.nextLine();

        try {
            switch (move) {
                case "1":
                    inStand.add(player);
                    // System.out.println("MOVING TO NEXT PLAYER");
                    break;
                case "2":
                    inStand.remove(player);
                    drawCard(player, bloodDraw, bloodDiscard, reader);
                    break;
                case "3":
                    inStand.remove(player);
                    drawCard(player, bloodDiscard, bloodDiscard, reader);
                    break;
                case "4":
                    inStand.remove(player);
                    drawCard(player, sandDraw, sandDiscard, reader);
                    break;
                case "5":
                    inStand.remove(player);
                    drawCard(player, sandDiscard, sandDiscard, reader);
                    break;
                case "6":
                    inStand.remove(player);
                    playToken(player, reader);
                    break;
                default:
                    System.err.println("INVALID");
                    break;
            }
        } catch (IllegalActionException e) {
            System.err.println(e.getMessage());
            performTurn(player);
        }
    }

    private void drawCard (Player player, List<Card> drawPile, List<Card> discardPile, Scanner reader) {
        //NOTE validation moved to new player method
        // if (player.getStock() < 1) {
        //     throw new IllegalActionException("INSUFFICIENT CHIPS TO DRAW");
        // }
        if (drawPile.isEmpty()) {
            throw new IllegalActionException("Draw pile is empty");
        }

        player.spendChip();
        Card drawn = drawPile.get(0);
        System.out.print("Drawn card: |" + drawn + "|, replace? (y/n) ");
        if (reader.nextLine().equals("y")) {
            Card swappedWith = player.getHand().swapCard(drawn);
            drawPile.remove(0);
            discardPile.add(swappedWith);
        } else {
            discardPile.add(drawn);
        }
    }

    public void playToken(Player player, Scanner reader) {
        if (Arrays.stream(player.getSelectedTokens()).allMatch(Objects::isNull)) {
            throw new IllegalActionException("No tokens to play");
        }

        System.out.print("Available tokens:" + Arrays.toString(player.getSelectedTokens()) + " ");
        String indexStr = reader.nextLine();
        int index = Integer.parseInt(indexStr);
        if (index < 0 || index >= players.length) {
            throw new IllegalActionException("Invalid token index");
        }

        ShiftToken selected = player.getSelectedTokens()[index];
        if (selected == null) {
            throw new IllegalActionException("Token already played");
        } else {
            player.getSelectedTokens()[index] = null;
        }

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
        ArrayList<Player> playerLst = new ArrayList<>(Arrays.asList(players));
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
