package com.monopoly.data.repository;

import com.monopoly.data.model.Player;
import com.monopoly.data.model.PlayerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByGameIdOrderByTurnOrder(Long gameId);
    List<Player> findByGameIdAndStatus(Long gameId, PlayerStatus status);
    int countByGameId(Long gameId);
    boolean existSByGameIdAndToken(Long gameId, String token);
    boolean existsByGameIdAndName(Long gameId, String name);
}
