package com.johnm.sabacc.backend.domain;

import com.johnm.sabacc.backend.domain.player.Player;

import java.util.List;

public class SabaccGame {
    private Player[] players;
    private List<GameRound> rounds;
    private final int buyIn;
    private final int chipsPerPlayer;

    public SabaccGame(Player[] players, int buyIn, int chipsPerPlayer) {
        this.players = players;
        rounds = null;
        this.buyIn = buyIn;
        this.chipsPerPlayer = chipsPerPlayer;
    }

    public void setup() {
        for (Player player : players) {
            player.setStock(chipsPerPlayer);
        }
    }

    public Player[] getPlayers() { return players; }

    public List<GameRound> getRounds() { return rounds; }

    public int getChipsPerPlayer() { return chipsPerPlayer; }

    @Override
    public String toString() {
        return "";
    }
}
