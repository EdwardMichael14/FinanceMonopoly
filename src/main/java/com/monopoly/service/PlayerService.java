package com.monopoly.service;

import com.monopoly.data.model.Player;
import com.monopoly.data.model.PlayerRound;
import com.monopoly.data.repository.PlayerRepository;
import com.monopoly.data.repository.PlayerRoundRepository;
import com.monopoly.dto.response.LeaderBoard;
import com.monopoly.dto.response.PlayerHistoryResponse;
import com.monopoly.exception.PlayerNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerRoundRepository playerRoundRepository;

    public PlayerHistoryResponse getPlayerHistory(Long playerId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new PlayerNotFoundException("Player not found: " + playerId));

        List<PlayerRound> rounds = playerRoundRepository.findByPlayerIdOrderByRoundNumber(playerId);

        PlayerHistoryResponse response = new PlayerHistoryResponse();
        response.setPlayerId(player.getId());
        response.setPlayerName(player.getName());
        response.setCreditScore(player.getCreditScore());
        response.setCurrentCashBalance(player.getCashBalance());
        response.setCurrentLoanBalance(player.getLoanBalance());
        response.setCurrentNetWorth(player.getCashBalance() - player.getLoanBalance());

        List<PlayerHistoryResponse.RoundSummary> summaries = rounds.stream().map(pr -> {
            PlayerHistoryResponse.RoundSummary s = new PlayerHistoryResponse.RoundSummary();
            s.setRoundNumber(pr.getRoundNumber());
            s.setSalaryReceived(pr.getSalaryReceived());
            s.setHousingType(pr.getHousingType());
            s.setHousingCost(pr.getHousingCost());
            s.setSurvivalCost(pr.getSurvivalCost());
            s.setLoanPayment(pr.getLoanPayment());
            s.setLoanBalanceAfter(pr.getLoanBalanceAfter());
            s.setDiceRoll(pr.getDiceRoll());
            s.setDiceOutcome(pr.getDiceOutcome());
            s.setDiceEventAmount(pr.getDiceEventAmount());
            s.setInvestmentPayout(pr.getInvestmentPayout());
            s.setCashBalanceEnd(pr.getCashBalanceEnd());
            s.setNetWorth(pr.getNetWorth());
            return s;
        }).toList();

        response.setRounds(summaries);
        return response;
    }

    public LeaderBoard getLeaderboard(Long gameId, int roundNumber) {
        List<PlayerRound> roundResults = playerRoundRepository
                .findByPlayerGameIdAndRoundNumberOrderByNetWorthDesc(gameId, roundNumber);

        List<LeaderBoard.PlayerStanding> standings = new ArrayList<>();
        int rank = 1;
        for (PlayerRound pr : roundResults) {
            LeaderBoard.PlayerStanding standing = new LeaderBoard.PlayerStanding();
            standing.setRank(rank++);
            standing.setPlayerId(pr.getPlayer().getId());
            standing.setPlayerName(pr.getPlayer().getName());
            standing.setNetWorth(pr.getNetWorth());
            standing.setCashBalance(pr.getCashBalanceEnd());
            standing.setLoanBalance(pr.getLoanBalanceAfter());
            standings.add(standing);
        }

        LeaderBoard leaderboard = new LeaderBoard();
        leaderboard.setRoundNumber(roundNumber);
        leaderboard.setStandings(standings);
        return leaderboard;
    }
}
