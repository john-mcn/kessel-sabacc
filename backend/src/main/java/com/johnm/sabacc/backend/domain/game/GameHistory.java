package com.johnm.sabacc.backend.domain.game;

import com.johnm.sabacc.backend.dto.game.GameHistoryDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity//(name = "sabacc_game")
public class GameHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ElementCollection
    private List<String> playerNames;
    private String winnerName;
    private int buyIn;
    private int chipsPerPlayer;
    private List<String> rewards; //TODO change

    public GameHistory() {}

    public GameHistory(List<String> playerNames, String winnerName, int buyIn, int chipsPerPlayer, List<String> rewards) {
        this.playerNames = playerNames;
        this.winnerName = winnerName;
        this.buyIn = buyIn;
        this.chipsPerPlayer = chipsPerPlayer;
        this.rewards = rewards;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public List<String> getPlayerNames() { return playerNames; }
    public void setPlayerNames(List<String> playerNames) { this.playerNames = playerNames; }

    public String getWinnerNames() { return winnerName; }
    public void setWinnerNames(String winnerName) { this.winnerName = winnerName; }

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
                winnerName,
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
