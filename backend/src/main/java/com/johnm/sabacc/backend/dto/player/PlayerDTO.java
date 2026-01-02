package com.johnm.sabacc.backend.dto.player;

import com.johnm.sabacc.backend.domain.game.components.CardRank;
import com.johnm.sabacc.backend.domain.game.components.ShiftToken;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.domain.player.PlayerHand;
import com.johnm.sabacc.backend.dto.game.CardDTO;

import java.util.ArrayList;
import java.util.List;

public class PlayerDTO {
    private String name;
    private List<CardDTO> hand;
    private CardDTO drawnCard;
    private List<String> tokens;
    private int stock, pot;
    private Integer chipDifference;

    public PlayerDTO() {}

    //NOTE this constructor is used by frontend (e.g. "/play/start-game")
    public PlayerDTO(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.tokens = new ArrayList<>(); // NOTE set these after getting player later
        this.stock = 0;
        this.pot = 0;
    }

    public PlayerDTO(String name, List<String> tokens, int stock, int pot) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.tokens = tokens;
        this.stock = stock;
        this.pot = pot;
        chipDifference = 0;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<CardDTO> getHand() { return hand; }
    public void setHand(List<CardDTO> hand) { this.hand = hand; }

    public int getPot() { return pot; }
    public void setPot(int pot) { this.pot = pot; }

    public CardDTO getDrawnCard() { return drawnCard; }
    public void setDrawnCard(CardDTO drawnCard) { this.drawnCard = drawnCard; }

    public List<String> getTokens() { return tokens; }
    public void setTokens(List<String> tokens) { this.tokens = tokens; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public int getChipDifference() { return chipDifference; }
    public void setChipDifference(int chipDifference) { this.chipDifference = chipDifference; }

    public Player toEntity() {
        System.err.println("DTO: " + tokens);
        PlayerHand handEntity = new PlayerHand(
                CardRank.fromString(hand.get(0).getRank()),
                CardRank.fromString(hand.get(1).getRank()));
        Player player = new Player(
                name,
                handEntity,
                tokens.stream().map(ShiftToken::fromString).toList(),
                stock,
                pot);
        player.setChipDifference(chipDifference);
        return player;
    }
}
