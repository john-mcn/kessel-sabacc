package com.johnm.sabacc.backend.domain.game;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Rewards {
    @Id
    @GeneratedValue
    private int id;

    private int winningDawnRepChange;
    private int winningHuttRepChange;
    private int winningPykeRepChange;
    private int losingDawnRepChange;
    private int losingHuttRepChange;
    private int losingPykeRepChange;

    public Rewards() {
        winningDawnRepChange = 0;
        winningHuttRepChange = 0;
        winningPykeRepChange = 0;
        losingDawnRepChange = 0;
        losingHuttRepChange = 0;
        losingPykeRepChange = 0;
    }

    public Rewards(int wdrc, int whrc, int wprc, int ldrc, int lhrc, int lprc) {
        winningDawnRepChange = wdrc;
        winningHuttRepChange = whrc;
        winningPykeRepChange = wprc;
        losingDawnRepChange = ldrc;
        losingHuttRepChange = lhrc;
        losingPykeRepChange = lprc;
    }

    public int getWinningDawnRepChange() { return winningDawnRepChange; }
    public void setWinningDawnRepChange(int winningDawnRepChange) { this.winningDawnRepChange = winningDawnRepChange; }

    public int getWinningHuttRepChange() { return winningHuttRepChange; }
    public void setWinningHuttRepChange(int winningHuttRepChange) { this.winningHuttRepChange = winningHuttRepChange; }

    public int getWinningPykeRepChange() { return winningPykeRepChange; }
    public void setWinningPykeRepChange(int winningPykeRepChange) { this.winningPykeRepChange = winningPykeRepChange; }

    public int getLosingDawnRepChange() { return losingDawnRepChange; }
    public void setLosingDawnRepChange(int losingDawnRepChange) { this.losingDawnRepChange = losingDawnRepChange; }

    public int getLosingHuttRepChange() { return losingHuttRepChange; }
    public void setLosingHuttRepChange(int losingHuttRepChange) { this.losingHuttRepChange = losingHuttRepChange; }

    public int getLosingPykeRepChange() { return losingPykeRepChange; }
    public void setLosingPykeRepChange(int losingPykeRepChange) { this.losingPykeRepChange = losingPykeRepChange; }
}
