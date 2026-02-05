package com.johnm.sabacc.backend.dto.game;

import com.johnm.sabacc.backend.domain.game.GameHistory;
import com.johnm.sabacc.backend.domain.game.Rewards;
import com.johnm.sabacc.backend.dto.player.PersonDTO;
import com.johnm.sabacc.backend.dto.player.PlayerDTO;

import java.util.List;

public class GameHistoryDTO {
    private Integer id;
    private List<String> playerNames;
    private PersonDTO winner;
    private int buyIn;
    private int chipsPerPlayer;
    private Rewards rewards;

    public GameHistoryDTO(Integer id, List<String> playerNames, PersonDTO winner, int buyIn, int chipsPerPlayer, Rewards rewards) {
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

    public Rewards getRewards() { return rewards; }
    public void setRewards(Rewards rewards) { this.rewards = rewards; }

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
