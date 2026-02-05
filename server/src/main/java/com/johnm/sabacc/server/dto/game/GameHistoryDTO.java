package com.johnm.sabacc.server.dto.game;

import com.johnm.sabacc.server.domain.game.GameHistory;
import com.johnm.sabacc.server.dto.player.PersonDTO;

import java.util.List;

public class GameHistoryDTO {
    private Integer id;
    private List<String> playerNames;
    private PersonDTO winner;
    private int buyIn;
    private int chipsPerPlayer;
    private List<String> rewards;

    public GameHistoryDTO(Integer id, List<String> playerNames, PersonDTO winner, int buyIn, int chipsPerPlayer, List<String> rewards) {
        this.id = id;
        this.playerNames = playerNames;
        this.winner = winner;
        this.buyIn = buyIn;
        this.chipsPerPlayer = chipsPerPlayer;
        this.rewards = rewards;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public List<String> getPlayerNames() { return playerNames; }
    public void setPlayerNames(List<String> playerNames) { this.playerNames = playerNames; }

    public PersonDTO getWinner() { return winner; }
    public void setWinner(PersonDTO winner) { this.winner = winner; }

    public int getBuyIn() { return buyIn; }
    public void setBuyIn(int buyIn) { this.buyIn = buyIn; }

    public int getChipsPerPlayer() { return chipsPerPlayer; }
    public void setChipsPerPlayer(int chipsPerPlayer) { this.chipsPerPlayer = chipsPerPlayer; }

    public List<String> getRewards() { return rewards; }
    public void setRewards(List<String> rewards) { this.rewards = rewards; }

    public GameHistory toEntity() {
        GameHistory gameHistory  = new GameHistory(
                playerNames,
                winner.toEntity(),
                buyIn,
                chipsPerPlayer,
                rewards);

        return gameHistory;
    }
}
