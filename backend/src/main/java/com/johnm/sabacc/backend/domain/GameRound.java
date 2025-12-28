package com.johnm.sabacc.backend.domain;

import com.johnm.sabacc.backend.domain.components.Card;
import com.johnm.sabacc.backend.domain.components.ShiftToken;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.domain.player.PlayerHand;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;
import com.johnm.sabacc.backend.util.GameUtils;

import java.util.*;

public class GameRound {
    private Player[] players;
    private List<ShiftToken> tokensActive;
    private List<Card> bloodDraw, sandDraw; // face down draw piles
    private List<Card> bloodDiscard, sandDiscard; // face up discard piles
    private int currPlayerIndex;
    private SabaccGame game;

    public GameRound(SabaccGame game) {
        this.game = game;
        players = game.getPlayers();
        tokensActive = new ArrayList<>();
        bloodDraw = new ArrayList<>();
        sandDraw = new ArrayList<>();
        bloodDiscard = new ArrayList<>();
        sandDiscard = new ArrayList<>();
        currPlayerIndex = 0;

    }

    public Player[] getPlayers() { return players; }
    public void setPlayers(Player[] players) { this.players = players; }

    public List<ShiftToken> getTokensActive() { return tokensActive; }
    public void setTokensActive(List<ShiftToken> tokensActive) { this.tokensActive = tokensActive; }

    public List<Card> getBloodDraw() { return bloodDraw; }
    public void setBloodDraw(List<Card> bloodDraw) { this.bloodDraw = bloodDraw; }

    public List<Card> getSandDraw() { return sandDraw; }
    public void setSandDraw(List<Card> sandDraw) { this.sandDraw = sandDraw; }

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

        System.out.println(player);
        System.out.println("1=Stand, 2=Draw blood, 3=Draw sand");
        String move = reader.nextLine();

        Card drawn;
        try {
            switch (move) {
                case "1":
                    System.out.println("MOVING TO NEXT PLAYER");
                    break;
                case "2":
                    drawCard(player, bloodDraw, bloodDiscard, reader);
                    break;
                case "3":
                    drawCard(player, sandDraw, sandDiscard, reader);
                    break;
                default:
                    System.err.println("INVALID");
                    break;
            }
        } catch (IllegalActionException e) {
            System.err.println(e.getMessage());
            performTurn(player);
        }

        currPlayerIndex++;
    }

    private void drawCard (Player player, List<Card> drawPile, List<Card> discardPile, Scanner reader) {
        //NOTE validation moved to new player method
        // if (player.getStock() < 1) {
        //     throw new IllegalActionException("INSUFFICIENT CHIPS TO DRAW");
        // }

        player.spendChip();
        Card drawn = drawPile.get(0);
        System.out.println("Drawn card: |" + drawn + "|, replace? (y/n)");
        if (reader.nextLine().equals("y")) {
            Card swappedWith = player.getHand().swapCard(drawn);
            drawPile.remove(0);
            discardPile.add(swappedWith);
        } else {
            discardPile.add(drawn);
        }
    }

    //TODO account for token effects (tokensActive)
    public List<PlayerHand> sortHands() {
        List<PlayerHand> hands = Arrays.stream(players).map(Player::getHand).toList();

        // hands.stream().s

        System.out.println(hands);

        return hands;
    }

    //TODO expand
    @Override
    public String toString() {
        String sandDiscardStr = (!sandDiscard.isEmpty())? sandDiscard.get(0).toString() : "";
        String bloodDiscardStr = (!bloodDiscard.isEmpty())? bloodDiscard.get(0).toString() : "";
        return "Top of discard: sand=[" + sandDiscardStr + "], blood=[" + bloodDiscardStr + "]";
    }
}
