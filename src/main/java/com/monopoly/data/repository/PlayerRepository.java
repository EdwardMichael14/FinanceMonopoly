package com.monopoly.data.repository;

import com.monopoly.data.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
        List<Player> findByGameIdOrderByTurnOrder(Long gameId);

        int countByGameId(Long gameId);

        boolean existsByGameIdAndName(Long gameId, String name);
}
