package com.monopoly.controller;

import com.monopoly.data.model.PlayerRound;
import com.monopoly.service.GameService;
import com.monopoly.service.RoundService;
import com.monopoly.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monopoly/rounds")
@RequiredArgsConstructor
public class RoundController {

    private final RoundService roundService;
    private final GameService gameService;

    @PostMapping("/players/{playerId}/play")
    public ResponseEntity<ApiResponse<PlayerRound>> playRound(@PathVariable Long playerId,
            @RequestParam long loanPayment) {
        return ResponseEntity
                .ok(ApiResponse.ok("Round played successfully", roundService.playRound(playerId, loanPayment)));
    }

    @GetMapping("/players/{playerId}/history")
    public ResponseEntity<ApiResponse<List<PlayerRound>>> getPlayerHistory(@PathVariable Long playerId) {
        return ResponseEntity.ok(ApiResponse.ok(gameService.getPlayerHistory(playerId)));
    }
}
