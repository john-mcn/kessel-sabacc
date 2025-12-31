package com.johnm.sabacc.backend.repository;

import com.johnm.sabacc.backend.domain.game.GameHistory;
import com.johnm.sabacc.backend.domain.game.SabaccGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SabaccGameRepository extends JpaRepository<GameHistory, Integer> {
    @Query("SELECT g FROM GameHistory g JOIN g.playerNames n WHERE n = :playerName")
    public List<SabaccGame> findByPlayerName(@Param("playerName") String playerName);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sabacc_game_players WHERE people_to_play_name = :name", nativeQuery = true)
    void deleteFromPeopleToPlayJoinTable(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sabacc_game_winners WHERE winners_name = :name", nativeQuery = true)
    void deleteFromWinnersJoinTable(@Param("name") String name);
}
