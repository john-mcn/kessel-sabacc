package com.johnm.sabacc.backend.dto.game;

import com.johnm.sabacc.backend.domain.game.GameRound;
import com.johnm.sabacc.backend.domain.game.SabaccGame;
import com.johnm.sabacc.backend.domain.player.Person;
import com.johnm.sabacc.backend.domain.player.Player;
import com.johnm.sabacc.backend.dto.player.PlayerDTO;

import java.util.ArrayList;
import java.util.List;

public class GameStateDTO {
    //TODO change to private and make getters
    public int buyIn;
    public int chipsPerPlayer;
    public List<String> rewards; //TODO change
    public List<PlayerDTO> players;
    public PlayerDTO winner;
    public int roundNumber;
    // Attributes for round
    public int currPlayerIndex;
    public int turnNumber;
    public CardDTO bloodDiscardTop;
    public CardDTO sandDiscardTop;
    public List<String> inStand;
    public List<PlayerDTO> roundFinishedOrder;
    public List<PlayerDTO> roundWinners;
    public Integer imposterValue;
    public Integer bestSabacc;
    public List<Integer> numbersRolled;

    public GameStateDTO() {}

    public GameStateDTO(List<PlayerDTO> players, int chipsPerPlayer, int buyIn, CardDTO bloodDiscardTop, CardDTO sandDiscardTop, List<String> inStand) {
        this.players = players;
        this.chipsPerPlayer = chipsPerPlayer;
        this.buyIn = buyIn;
        currPlayerIndex = 0;
        turnNumber = 0;
        roundNumber = 0;
        this.bloodDiscardTop = bloodDiscardTop;
        this.sandDiscardTop = sandDiscardTop;
        this.inStand = inStand;
    }

    public GameStateDTO(List<PlayerDTO> players, int chipsPerPlayer, int buyIn, int currPlayerIndex, int turnNumber, int roundNumber, CardDTO bloodDiscardTop, CardDTO sandDiscardTop, List<String> inStand) {
        this.players = players;
        this.chipsPerPlayer = chipsPerPlayer;
        this.buyIn = buyIn;
        this.currPlayerIndex = currPlayerIndex;
        this.turnNumber = turnNumber;
        this.roundNumber = roundNumber;
        this.bloodDiscardTop = bloodDiscardTop;
        this.sandDiscardTop = sandDiscardTop;
        this.inStand = inStand;
    }

    public static GameStateDTO fromEntities(SabaccGame g, GameRound r) {
        GameStateDTO dto = new GameStateDTO();
        if (r != null){
            dto = new GameStateDTO(
                    g.getPlayers().stream().map(Player::toDTO).toList(),
                    g.getChipsPerPlayer(),
                    g.getBuyIn(),
                    r.getCurrPlayerIndex(),
                    r.getTurnNumber(),
                    g.getRoundNumber(),
                    (r.getBloodDiscard().isEmpty())? null : r.getTopBloodDiscard().toDTO(),
                    (r.getSandDiscard().isEmpty())? null : r.getTopSandDiscard().toDTO(),
                    r.getInStand().stream().map(Player::getName).toList()
            );
            if (r.getWinners() != null && !r.getWinners().isEmpty()) {
                dto.roundFinishedOrder = r.getFinalOrder().stream().map(Player::toDTO).toList();
                dto.roundWinners = r.getWinners().stream().map(Player::toDTO).toList();
            }
            if (r.getNumbersRolled() != null && !r.getNumbersRolled().isEmpty()){
                dto.numbersRolled = r.getNumbersRolled();
            }
        } else {
            dto = new GameStateDTO(
                    g.getPlayers().stream().map(Player::toDTO).toList(),
                    g.getChipsPerPlayer(),
                    g.getBuyIn(),
                    0,
                    0,
                    0,
                    null,
                    null,
                    new ArrayList<>()
            );
        }

        if (g.getWinner() != null) { dto.winner = g.getWinner().toDTO();}

        return dto;
    }

    public SabaccGame toEntity() {
        SabaccGame g = new SabaccGame();
        g.setBuyIn(buyIn);
        g.setChipsPerPlayer(chipsPerPlayer);
        g.setRewards(rewards);
        // private List<Person> winners;
        // private List<GameRound> rounds;
        // private List<Player> players;
        return g;
    }
}
