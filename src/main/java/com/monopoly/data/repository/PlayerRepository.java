package com.monopoly.data.repository;

import com.monopoly.data.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
        List<Player> findByGameIdOrderByTurnOrder(Long gameId);
        int countByGameId(Long gameId);
        boolean existsByGameIdAndName(Long gameId, String name);
}
