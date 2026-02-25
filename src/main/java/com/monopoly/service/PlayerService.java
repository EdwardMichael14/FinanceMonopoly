package com.monopoly.service;

import com.monopoly.data.model.Player;
import com.monopoly.data.model.PlayerRound;
import com.monopoly.data.repository.PlayerRepository;
import com.monopoly.data.repository.PlayerRoundRepository;
import com.monopoly.dto.response.LoanPreviewResponse;
import com.monopoly.dto.response.PlayerHistoryResponse;
import com.monopoly.exception.PlayerNotFoundException;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.monopoly.util.Mapper.*;


@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PlayerRoundRepository playerRoundRepository;


    public PlayerHistoryResponse getPlayerHistory(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new PlayerNotFoundException("Player not found: " + playerId));

        List<PlayerRound> rounds = playerRoundRepository.findByPlayerIdOrderByRoundRoundNumber(playerId);

        PlayerHistoryResponse response = new PlayerHistoryResponse();
        mapToPlayerResponse(response, player);

        List<PlayerHistoryResponse.RoundSummary> summaries = rounds.stream().map(pr -> {
            PlayerHistoryResponse.RoundSummary summary = new PlayerHistoryResponse.RoundSummary();
            return getRoundSummary(pr, summary);
        }).toList();

        response.setRounds(summaries);
        return response;
    }


    public LoanPreviewResponse previewLoanPayment(Long playerId, long proposedPaymentNaira) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found: " + playerId));

        return getLoanPreviewResponse(proposedPaymentNaira, player);
    }


}
