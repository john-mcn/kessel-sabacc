package com.johnm.sabacc.backend.domain.game;

import com.johnm.sabacc.backend.domain.game.components.ShiftToken;
import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SabaccGame {
    private List<Person> peopleToPlay;
    private int buyIn;
    private int chipsPerPlayer;
    private List<String> rewards; //TODO change
    private List<Person> winners;
    private List<GameRound> rounds;
    private List<Player> players;

    public SabaccGame() {}

    public SabaccGame(List<Person> peopleToPlay, int buyIn, int chipsPerPlayer, List<String> rewards) {
        this.peopleToPlay = peopleToPlay;
        rounds = null;
        this.buyIn = buyIn;
        this.chipsPerPlayer = chipsPerPlayer;
        this.rewards = rewards;
    }

    public List<Person> getPeopleToPlay() { return peopleToPlay; }
    public void setPeopleToPlay(List<Person> peopleToPlay) { this.peopleToPlay = peopleToPlay; }
    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }

    public List<GameRound> getRounds() { return rounds; }
    public void  setRounds(List<GameRound> rounds) { this.rounds = rounds; }

    public int getBuyIn() { return buyIn; }
    public void setBuyIn(int buyIn) { this.buyIn = buyIn; }

    public int getChipsPerPlayer() { return chipsPerPlayer; }
    public void setChipsPerPlayer(int chipsPerPlayer) {  this.chipsPerPlayer = chipsPerPlayer; }

    public List<String> getRewards() { return rewards; }
    public void setRewards(List<String> rewards) { this.rewards = rewards; }

    public List<Person> getWinners() { return winners; }
    public void setWinners(List<Person> winners) { this.winners = winners; }

    public void setup() {
        final int TOKEN_AMOUNT = 3;

        players = new ArrayList<>();
        for(Person p : peopleToPlay) {
            //TODO move validation?
            if (p.getCredits() < buyIn) {
                throw new IllegalActionException(p.getName() + " has insufficient credits to play.");
            }
            p.setCredits(p.getCredits() - buyIn);
            // Player player = new Player(p.getName(), p.getCredits(), p.getTokens(), null, new ShiftToken[3]);
            Player player = new Player(p.getName(), null, null);

            player.setSelectedTokens(p.getTokens());

            players.add(player);
        }

        for (Player player : players) {
            player.setStock(chipsPerPlayer);
        }
    }

    // public List<Person> runGame() {
    //     setup();
    //
    //     List<Player> winningPlayers = new ArrayList<>();
    //     while (players.stream().anyMatch(p -> p.getStock() > 0)) {
    //         GameRound newRound = new GameRound(this);
    //
    //         winningPlayers = newRound.runFullRound();
    //     }
    //
    //     winners = winningPlayers.stream().map(p -> (Person) p).toList();
    //
    //     System.out.println("!!!WINNERS=" + winners.toString() + "!!!");
    //     return winners;
    // }
}
