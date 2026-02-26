package com.monopoly.service;

import com.monopoly.data.model.*;
import com.monopoly.data.repository.GameRepository;
import com.monopoly.data.repository.PlayerRepository;
import com.monopoly.data.repository.PlayerRoundRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final PlayerRoundRepository playerRoundRepository;

    @Transactional
    public Game createGame() {
        Game game = new Game();
        game.setGameCode(generateGameCode());
        return gameRepository.save(game);
    }

    @Transactional
    public Player joinGame(String gameCode, String playerName) {
        Game game = findGameByCode(gameCode);
        if (game.getStatus() != GameStatus.WAITING_FOR_PLAYERS) {
            throw new IllegalArgumentException("Game has already started.");
        }
        if (playerRepository.existsByGameIdAndName(game.getId(), playerName)) {
            throw new IllegalArgumentException("Player already exists");
        }
        int currentCount = playerRepository.countByGameId(game.getId());
        if (currentCount >= 3) {
            throw new IllegalArgumentException("Maximum of 3 players allowed.");
        }
        Player player = new Player();
        player.setName(playerName);
        player.setGame(game);
        player.setTurnOrder(currentCount + 1);
        Player savedPlayer = playerRepository.save(player);

        // Auto-start game when 3 players have joined
        if (currentCount + 1 >= 3) {
            startGame(gameCode);
        }

        return savedPlayer;
    }

    @Transactional
    public Game startGame(String gameCode) {
        Game game = findGameByCode(gameCode);
        if (game.getStatus() != GameStatus.WAITING_FOR_PLAYERS) {
            return game; // Already started
        }
        int playerCount = playerRepository.countByGameId(game.getId());
        if (playerCount < 1) {
            throw new IllegalArgumentException("Need at least one player");
        }
        game.setStatus(GameStatus.IN_PROGRESS);
        game.setStartedAt(LocalDateTime.now());
        game.setCurrentRound(1);
        return gameRepository.save(game);
    }

    @Transactional
    public Player pickHousing(Long playerId, HousingType housingType) {
        Player player = findPlayer(playerId);
        if (player.getGame().getStatus() != GameStatus.IN_PROGRESS) {
            throw new IllegalArgumentException("Game not in progress");
        }
        player.setHousingType(housingType);
        return playerRepository.save(player);
    }

    @Transactional
    public Game getGame(String gameCode) {
        return findGameByCode(gameCode);
    }

    @Transactional
    public Game findGameByCode(String gameCode) {
        return gameRepository.findByGameCode(gameCode)
                .orElseThrow(() -> new NoSuchElementException("Game not found: " + gameCode));
    }

    public Player findPlayer(Long playerId) {
        return playerRepository.findById(playerId)
                .orElseThrow(() -> new NoSuchElementException("Player not found: " + playerId));
    }

    private String generateGameCode() {
        return "GAME-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    /* previously in PlayerService */
    public List<PlayerRound> getPlayerHistory(Long playerId) {
        if (!playerRepository.existsById(playerId)) {
            throw new NoSuchElementException("Player not found: " + playerId);
        }
        return playerRoundRepository.findByPlayerIdOrderByRoundNumber(playerId);
    }

    public List<PlayerRound> getLeaderboard(String gameCode, int roundNumber) {
        Game game = findGameByCode(gameCode);
        return playerRoundRepository.findByPlayerGameIdAndRoundNumberOrderByNetWorthDesc(game.getId(), roundNumber);
    }
}
