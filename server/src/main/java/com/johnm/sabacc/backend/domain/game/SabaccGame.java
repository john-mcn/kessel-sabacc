package com.johnm.sabacc.backend.domain.game;

import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.exceptions.IllegalActionException;

import java.util.ArrayList;
import java.util.List;

public class SabaccGame {
    private List<Person> peopleToPlay;
    private int buyIn;
    private int chipsPerPlayer;
    private Rewards rewards;
    private Player winner;
    private List<GameRound> rounds;
    private List<Player> players;
    private int roundNumber;
    private GamePreset preset;


    public SabaccGame() {}

    public SabaccGame(List<Person> peopleToPlay, int buyIn, int chipsPerPlayer, Rewards rewards) {
        this.peopleToPlay = peopleToPlay;
        // rounds = null;
        this.buyIn = buyIn;
        this.chipsPerPlayer = chipsPerPlayer;
        this.rewards = rewards;
        roundNumber = 0;
    }

    public SabaccGame(GamePreset preset, List<Person> peopleToPlay) {
        List<Person> peopleInvalidRep = peopleToPlay.stream().filter(p ->
                p.getDawnRep() < preset.getDawnRepReq()
                        || p.getHuttRep() < preset.getHuttRepReq()
                        || p.getPykeRep() < preset.getPykeRepReq()).toList();
        if (!peopleInvalidRep.isEmpty()) {
            throw new IllegalActionException(
                    "Players with insufficient reputation: " + peopleInvalidRep);
        }

        this.preset = preset;
        buyIn = preset.getBuyIn();
        chipsPerPlayer = preset.getChipsPerPlayer();
        rewards = preset.getRewards();
        roundNumber = 0;

        this.peopleToPlay = peopleToPlay;
    }

    public GamePreset getPreset() { return preset; }
    public void setPreset(GamePreset preset) { this.preset = preset; }

    public List<Person> getPeopleToPlay() { return peopleToPlay; }
    public void setPeopleToPlay(List<Person> peopleToPlay) { this.peopleToPlay = peopleToPlay; }
    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }

    public List<GameRound> getRounds() { return rounds; }
    public void  setRounds(List<GameRound> rounds) { this.rounds = rounds; }

    public int getRoundNumber() { return roundNumber; }
    public void setRoundNumber(int roundNumber) { this.roundNumber = roundNumber; }

    public int getBuyIn() { return buyIn; }
    public void setBuyIn(int buyIn) { this.buyIn = buyIn; }

    public int getChipsPerPlayer() { return chipsPerPlayer; }
    public void setChipsPerPlayer(int chipsPerPlayer) {  this.chipsPerPlayer = chipsPerPlayer; }

    public Rewards getRewards() { return rewards; }
    public void setRewards(Rewards rewards) { this.rewards = rewards; }

    public Player getWinner() { return winner; }
    public void setWinner(Player winner) { this.winner = winner; }

    public List<Player> playersInGame() {
        return players.stream().filter(p -> p.getStock() > 0).toList();
    }

    public void setup() {
        final int TOKEN_AMOUNT = 3;

        players = new ArrayList<>();
        for(Person p : peopleToPlay) {
            //TODO move validation?
            if (p.getCredits() < buyIn) {
                throw new IllegalActionException(p.getName() + " has insufficient credits to play.");
            }
            p.setCredits(p.getCredits() - buyIn);
            Player newPlayer = new Player(p.getName(), null, null);
            newPlayer.setUsername(p.getUsername());
            newPlayer.setCredits(p.getCredits());

            newPlayer.setSelectedTokens(p.getTokens());
            players.add(newPlayer);
        }

        for (Player player : players) {
            player.setStock(chipsPerPlayer);
        }

        roundNumber = 1;
    }

    public void endGame() {
        winner = players.stream().filter(p -> p.getStock() > 0).toList().get(0);
        winner.setCredits(winner.getCredits() + (buyIn * players.size()));
        // Handle winner & loser reputation change
        if (rewards != null) {
            winner.setDawnRep(winner.getDawnRep() + rewards.getWinningDawnRepChange());
            winner.setHuttRep(winner.getHuttRep() + rewards.getWinningHuttRepChange());
            winner.setPykeRep(winner.getPykeRep() + rewards.getWinningPykeRepChange());

            for (Player loser: players.stream().filter(p -> p.getStock() <= 0).toList()) {
                loser.setDawnRep(loser.getDawnRep() + rewards.getLosingDawnRepChange());
                loser.setHuttRep(loser.getHuttRep() + rewards.getLosingHuttRepChange());
                loser.setPykeRep(loser.getPykeRep() + rewards.getLosingPykeRepChange());
            }
        }
    }

    public GameHistory toGameHistory() {
        GameHistory gameHistory = new GameHistory(
                players.stream().map(Player::getName).toList(),
                winner,
                buyIn,
                chipsPerPlayer,
                rewards
        );

        return gameHistory;
    }
}
