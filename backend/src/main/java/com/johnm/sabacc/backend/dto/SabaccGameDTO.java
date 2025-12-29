package com.johnm.sabacc.backend.dto;

import com.johnm.sabacc.backend.domain.SabaccGame;

import java.util.List;

public class SabaccGameDTO {
    private Integer id;
    private List<String> playerNames;
    private List<String> winnerNames;
    private int buyIn;
    private int chipsPerPlayer;
    private String rewards;

    public SabaccGameDTO(Integer id, List<String> playerNames, List<String> winnerNames, int buyIn, int chipsPerPlayer, String rewards) {
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

    public String getRewards() { return rewards; }
    public void setRewards(String rewards) { this.rewards = rewards; }

    public SabaccGame toEntity() {
        SabaccGame entity  = new SabaccGame();
        // entity.setPeopleToPlay();
        entity.setBuyIn(buyIn);
        entity.setChipsPerPlayer(chipsPerPlayer);
        entity.setRewards(rewards);
        // entity.setWinners();

        return entity;
    }
}
