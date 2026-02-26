package com.monopoly.controller;

import com.monopoly.data.model.Game;
import com.monopoly.data.model.Player;
import com.monopoly.data.model.PlayerRound;
import com.monopoly.service.GameService;
import com.monopoly.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monopoly/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping
    public ResponseEntity<ApiResponse<Game>> createGame() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok("Game created successfully", gameService.createGame()));
    }

    @PostMapping("/{gameCode}/join")
    public ResponseEntity<ApiResponse<Player>> joinGame(@PathVariable String gameCode,
            @RequestParam String playerName) {
        return ResponseEntity
                .ok(ApiResponse.ok("Player joined successfully", gameService.joinGame(gameCode, playerName)));
    }

    @PostMapping("/{gameCode}/start")
    public ResponseEntity<ApiResponse<Game>> startGame(@PathVariable String gameCode) {
        return ResponseEntity.ok(ApiResponse.ok("Game started successfully", gameService.startGame(gameCode)));
    }

    @GetMapping("/{gameCode}")
    public ResponseEntity<ApiResponse<Game>> getGame(@PathVariable String gameCode) {
        return ResponseEntity.ok(ApiResponse.ok(gameService.getGame(gameCode)));
    }

    @PostMapping("/players/{playerId}/housing")
    public ResponseEntity<ApiResponse<Player>> pickHousing(@PathVariable Long playerId,
            @RequestParam com.monopoly.data.model.HousingType housingType) {
        return ResponseEntity
                .ok(ApiResponse.ok("Housing selected successfully",
                        gameService.pickHousing(playerId, housingType)));
    }

    @GetMapping("/{gameCode}/leaderboard/{roundNumber}")
    public ResponseEntity<ApiResponse<List<PlayerRound>>> getLeaderboard(@PathVariable String gameCode,
            @PathVariable int roundNumber) {
        return ResponseEntity.ok(ApiResponse.ok(gameService.getLeaderboard(gameCode, roundNumber)));
    }
}
