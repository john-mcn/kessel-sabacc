package com.johnm.sabacc.backend.dto.game;

import com.johnm.sabacc.backend.domain.game.components.CardRank;

import java.util.List;

public class ResolveImposterDTO {
    private String playerName;
    private List<CardDTO> cards;

    public ResolveImposterDTO() {}

    public ResolveImposterDTO(String playerName, String bloodCardFamily, String bloodCardRank, String sandCardFamily, String sandCardRank) {
        this.playerName = playerName;
        this.cards = List.of(new CardDTO(bloodCardFamily, bloodCardRank), new CardDTO(sandCardFamily, sandCardRank));
    }

    public ResolveImposterDTO(String playerName, String bloodCardFamily, Integer bloodCardRank, String sandCardFamily, Integer sandCardRank) {
        this.playerName = playerName;
        this.cards = List.of(new CardDTO(bloodCardFamily, CardRank.fromInt(bloodCardRank).toString()), new CardDTO(sandCardFamily, CardRank.fromInt(sandCardRank).toString()));
    }

    public String getPlayerName() { return playerName; }
    public void setPlayerName(String playerName) { this.playerName = playerName; }

    public List<CardDTO> getCards() { return cards; }
    public void setCards(List<CardDTO> cards) { this.cards = cards; }
}
