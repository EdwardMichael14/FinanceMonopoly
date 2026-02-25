package com.monopoly.data.repository;

import com.monopoly.data.model.Investment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByPlayerIdAndIsFullyPaidOut(Long playerId, Boolean isFullyPaidOut);
    List<Investment> findByGameId(Long gameId);
}
