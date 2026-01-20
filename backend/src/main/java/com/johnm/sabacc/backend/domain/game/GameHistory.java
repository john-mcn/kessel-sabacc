package com.johnm.sabacc.backend.domain.game;

import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.dto.game.GameHistoryDTO;
import com.johnm.sabacc.backend.dto.player.PlayerDTO;
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

    @OneToOne
    private Rewards rewards;

    public GameHistory() {}

    public GameHistory(List<String> playerNames, Person winner, int buyIn, int chipsPerPlayer, Rewards rewards) {
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

    public Rewards getRewards() { return rewards; }
    public void setRewards(Rewards rewards) { this.rewards = rewards; }

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
