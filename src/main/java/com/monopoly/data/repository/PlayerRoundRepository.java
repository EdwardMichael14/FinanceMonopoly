package com.monopoly.data.repository;

import com.monopoly.data.model.PlayerRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRoundRepository extends JpaRepository<PlayerRound, Long> {
    List<PlayerRound> findByPlayerIdOrderByRoundNumber(Long playerId);

    boolean existsByPlayerIdAndRoundNumber(Long playerId, int roundNumber);

    List<PlayerRound> findByPlayerGameIdAndRoundNumberOrderByNetWorthDesc(Long gameId, int roundNumber);

    int countByPlayerGameIdAndRoundNumber(Long gameId, int roundNumber);
}
