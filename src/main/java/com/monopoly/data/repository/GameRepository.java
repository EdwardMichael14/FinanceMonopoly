package com.monopoly.data.repository;

import com.monopoly.data.model.Game;
import com.monopoly.data.model.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByGameCode(String gameCode);
    List<Game> findByStatus(GameStatus status);
    boolean existsByGameCode(String gameCode);
}
