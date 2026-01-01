package com.johnm.sabacc.backend.dto.game;

import com.johnm.sabacc.backend.domain.game.GameHistory;

import java.util.List;

public class GameHistoryDTO {
    private Integer id;
    private List<String> playerNames;
    private List<String> winnerNames;
    private int buyIn;
    private int chipsPerPlayer;
    private List<String> rewards;

    public GameHistoryDTO(Integer id, List<String> playerNames, List<String> winnerNames, int buyIn, int chipsPerPlayer, List<String> rewards) {
        this.id = id;
        this.playerNames = playerNames;
        this.winnerNames = winnerNames;
        this.buyIn = buyIn;
        this.chipsPerPlayer = chipsPerPlayer;
        this.rewards = rewards;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

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

    public GameHistory toEntity() {
        GameHistory gameHistory  = new GameHistory(
                playerNames,
                winnerNames,
                buyIn,
                chipsPerPlayer,
                rewards);

        return gameHistory;
    }
}
