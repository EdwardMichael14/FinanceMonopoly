package com.monopoly.data.repository;

import com.monopoly.data.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoundRepository extends JpaRepository<Round, Long> {
    List<Round> findByGameIdOrderByRoundNumber(Long gameId);
    Optional<Round> findByGameIdAndRoundNumber(Long gameId, Integer roundNumber);
    Optional<Round> findByGameIdAndIsCompleted(Long gameId, Boolean isCompleted);
}
