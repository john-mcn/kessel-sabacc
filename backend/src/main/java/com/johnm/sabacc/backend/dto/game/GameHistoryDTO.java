package com.johnm.sabacc.backend.dto.game;

import com.johnm.sabacc.backend.domain.game.GameHistory;

import java.util.List;

public class GameHistoryDTO {
    private Integer id;
    private List<String> playerNames;
    private String winnerName;
    private int buyIn;
    private int chipsPerPlayer;
    private List<String> rewards;

    public GameHistoryDTO(Integer id, List<String> playerNames, String winnerName, int buyIn, int chipsPerPlayer, List<String> rewards) {
        this.id = id;
        this.playerNames = playerNames;
        this.winnerName = winnerName;
        this.buyIn = buyIn;
        this.chipsPerPlayer = chipsPerPlayer;
        this.rewards = rewards;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public List<String> getPlayerNames() { return playerNames; }
    public void setPlayerNames(List<String> playerNames) { this.playerNames = playerNames; }

    public String getWinnerName() { return winnerName; }
    public void setWinnerName(String winnerName) { this.winnerName = winnerName; }

    public int getBuyIn() { return buyIn; }
    public void setBuyIn(int buyIn) { this.buyIn = buyIn; }

    public int getChipsPerPlayer() { return chipsPerPlayer; }
    public void setChipsPerPlayer(int chipsPerPlayer) { this.chipsPerPlayer = chipsPerPlayer; }

    public List<String> getRewards() { return rewards; }
    public void setRewards(List<String> rewards) { this.rewards = rewards; }

    public GameHistory toEntity() {
        GameHistory gameHistory  = new GameHistory(
                playerNames,
                winnerName,
                buyIn,
                chipsPerPlayer,
                rewards);

        return gameHistory;
    }
}
