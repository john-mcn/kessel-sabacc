package com.johnm.sabacc.backend.domain.game;

import com.johnm.sabacc.backend.dto.GameHistoryDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "sabacc_game")
public class GameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private List<String> playerNames;
    private List<String> winnerNames;
    private int buyIn;
    private int chipsPerPlayer;
    private List<String> rewards; //TODO change

    public GameHistory() {}

    public GameHistory(List<String> playerNames, List<String> winnerNames, int buyIn, int chipsPerPlayer, List<String> rewards) {
        this.playerNames = playerNames;
        this.winnerNames = winnerNames;
        this.buyIn = buyIn;
        this.chipsPerPlayer = chipsPerPlayer;
        this.rewards = rewards;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<String> getPlayerNames() { return playerNames; }
    public void setPlayerNames(List<String> playerNames) { this.playerNames = playerNames; }

    public List<String> getWinnerNames() { return winnerNames; }
    public void setWinnerNames(List<String> winnerNames) { this.winnerNames = winnerNames; }

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
                winnerNames,
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
