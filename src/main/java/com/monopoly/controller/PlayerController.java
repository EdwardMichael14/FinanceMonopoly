package com.monopoly.controller;


import com.monopoly.dto.request.LoanPreviewRequest;
import com.monopoly.dto.response.ApiResponse;
import com.monopoly.dto.response.LoanPreviewResponse;
import com.monopoly.dto.response.PlayerHistoryResponse;
import com.monopoly.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monopoly/players")
@AllArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/{playerId}/history")
    public ResponseEntity<?> getPlayerHistory(@PathVariable("playerId") Long playerId) {
        PlayerHistoryResponse history = playerService.getPlayerHistory(playerId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok(history));
    }


    @PostMapping("/{playerId}/loan/preview")
    public ResponseEntity<?> previewLoanPayment(@PathVariable("playerId") Long playerId, @RequestBody LoanPreviewRequest request) {
        LoanPreviewResponse preview = playerService.previewLoanPayment(playerId, request.getProposedPaymentNaira());
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.ok("Loan payment preview for player " + playerId, preview));
    }
}
