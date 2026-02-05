package com.johnm.sabacc.server.domain.game;

import com.johnm.sabacc.server.domain.player.Person;
import com.johnm.sabacc.server.dto.game.GameHistoryDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity//(name = "sabacc_game")
public class GameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ElementCollection
    private List<String> playerNames;

    @ManyToOne
    private Person winner;

    private int buyIn;
    private int chipsPerPlayer;
    private List<String> rewards; //TODO change

    public GameHistory() {}

    public GameHistory(List<String> playerNames, Person winner, int buyIn, int chipsPerPlayer, List<String> rewards) {
        this.playerNames = playerNames;
        this.winner = winner;
        this.buyIn = buyIn;
        this.chipsPerPlayer = chipsPerPlayer;
        this.rewards = rewards;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<String> getPlayerNames() { return playerNames; }
    public void setPlayerNames(List<String> playerNames) { this.playerNames = playerNames; }

    public Person getWinnerNames() { return winner; }
    public void setWinnerNames(Person winner) { this.winner = winner; }

    public int getBuyIn() { return buyIn; }
    public void setBuyIn(int buyIn) { this.buyIn = buyIn; }

    public int getChipsPerPlayer() { return chipsPerPlayer; }
    public void setChipsPerPlayer(int chipsPerPlayer) { this.chipsPerPlayer = chipsPerPlayer; }

    public List<String> getRewards() { return rewards; }
    public void setRewards(List<String> rewards) { this.rewards = rewards; }

    public GameHistoryDTO toDTO() {
        GameHistoryDTO dto = new GameHistoryDTO(
                id,
                playerNames,
                (winner == null)? null : winner.toDto(),
                buyIn,
                chipsPerPlayer,
                rewards);
        return dto;
    }

    @Override
    public String toString() {
        return "Game " + getId();
    }
}
