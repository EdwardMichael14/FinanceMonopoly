package com.monopoly.data.repository;

import com.monopoly.data.model.Game;
import com.monopoly.data.model.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    @org.springframework.data.jpa.repository.EntityGraph(attributePaths = { "players" })
    Optional<Game> findByGameCode(String gameCode);

    List<Game> findByStatus(GameStatus status);

    boolean existsByGameCode(String gameCode);
}
