package com.johnm.sabacc.backend.domain.game;

import com.johnm.sabacc.backend.dto.player.PersonDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class GamePreset {
    @Id
    private int id;

    private int buyIn;
    private int chipsPerPlayer;
    // Reputation requirements to participate
    private int dawnRepReq, huttRepReq, pykeRepReq;

    @OneToOne (cascade = CascadeType.ALL)
    private Rewards rewards;

    public GamePreset() {
        dawnRepReq = 0;
        huttRepReq = 0;
        chipsPerPlayer = 0;
    }

    public GamePreset(int id, int buyIn, int chipsPerPlayer, int dawnRepReq, int huttRepReq, int pykeRepReq, Rewards rewards) {
        this.id = id;
        this.buyIn = buyIn;
        this.chipsPerPlayer = chipsPerPlayer;
        this.dawnRepReq = dawnRepReq;
        this.huttRepReq = huttRepReq;
        this.pykeRepReq = pykeRepReq;
        this.rewards = rewards;
    }

    public int getBuyIn() { return buyIn; }
    public void setBuyIn(int buyIn) { this.buyIn = buyIn; }

    public int getChipsPerPlayer() { return chipsPerPlayer; }
    public void setChipsPerPlayer(int chipsPerPlayer) { this.chipsPerPlayer = chipsPerPlayer; }

    public int getDawnRepReq() { return dawnRepReq; }
    public void setDawnRepReq(int dawnRepReq) { this.dawnRepReq = dawnRepReq; }

    public int getHuttRepReq() { return huttRepReq; }
    public void setHuttRepReq(int huttRepReq) { this.huttRepReq = huttRepReq; }

    public int getPykeRepReq() { return pykeRepReq; }
    public void setPykeRepReq(int pykeRepReq) { this.pykeRepReq = pykeRepReq; }

    public Rewards getRewards() { return rewards; }
    public void setRewards(Rewards rewards) { this.rewards = rewards; }
}
