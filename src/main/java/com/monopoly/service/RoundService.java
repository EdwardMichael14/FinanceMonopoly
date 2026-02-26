package com.monopoly.service;

import com.monopoly.data.model.*;
import com.monopoly.data.repository.GameRepository;
import com.monopoly.data.repository.PlayerRepository;
import com.monopoly.data.repository.PlayerRoundRepository;
import com.monopoly.data.model.PlayerRound;
import java.util.List;
import java.util.NoSuchElementException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final PlayerRoundRepository playerRoundRepository;
    private final Random random = new Random();

    private static final long SALARY_PER_ROUND = 2_400_000L;
    private static final long SURVIVAL_COST = 700_000L;
    private static final double LOAN_INTEREST_RATE = 0.10;

    @Transactional
    public PlayerRound playRound(Long playerId, long loanPayment) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new NoSuchElementException("Player not found: " + playerId));
        Game game = player.getGame();
        validateTurn(player, game);

        PlayerRound pr = new PlayerRound();
        pr.setPlayer(player);
        pr.setRoundNumber(game.getCurrentRound());
        pr.setHousingType(player.getHousingType());

        // Step 1: Financial Phase (Salary, Investments, Survival)
        processSalary(player, pr);
        processInvestments(player, pr);
        processSurvival(player, pr);

        // Step 2 & 3: Decision & Payment Phase (Housing, Loan Repayment)
        processHousingAndLoanPayment(player, game, loanPayment, pr);

        // Step 4: Uncertainty Phase (Roll Dice)
        processDiceEventAndUncertainty(player, game, pr);

        // Step 5: Finalization Phase (Interest, Net Worth)
        finalizeRound(player, game, pr);

        playerRepository.save(player);
        playerRoundRepository.save(pr);
        advanceRoundIfComplete(game);
        return pr;
    }

    private void validateTurn(Player player, Game game) {
        if (game.getStatus() != GameStatus.IN_PROGRESS)
            throw new IllegalArgumentException("Game not in progress");
        if (player.getHousingType() == null)
            throw new IllegalArgumentException("Housing not selected");
        if (playerRoundRepository.existsByPlayerIdAndRoundNumber(player.getId(), game.getCurrentRound())) {
            throw new IllegalArgumentException("Already played this round");
        }
    }

    private void processSalary(Player player, PlayerRound pr) {
        if (!player.isMissNextSalary()) {
            player.setCashBalance(player.getCashBalance() + SALARY_PER_ROUND);
            pr.setSalaryReceived(SALARY_PER_ROUND);
        } else {
            player.setMissNextSalary(false);
            pr.setSalaryReceived(0L);
        }
    }

    private void processInvestments(Player player, PlayerRound pr) {
        long inv = player.getPendingInvestmentReturn() + player.getInvestmentBPerRoundReturn();
        if (inv > 0) {
            player.setCashBalance(player.getCashBalance() + inv);
            pr.setInvestmentPayout(inv);
            player.setPendingInvestmentReturn(0);
        }
    }

    private void processSurvival(Player player, PlayerRound pr) {
        player.setCashBalance(player.getCashBalance() - SURVIVAL_COST);
        pr.setSurvivalCost(SURVIVAL_COST);
    }

    private void processHousingAndLoanPayment(Player player, Game game, long loanPayment, PlayerRound pr) {
        // Step 1: Handle Inflation Roll (Only from Round 2 onwards)
        if (game.getCurrentRound() > 1) {
            int inflationRoll = random.nextInt(6) + 1;
            pr.setInflationDiceRoll(inflationRoll);
        }

        // Step 2: Rent Calculation (Based on cumulative inflation rolls)
        List<Integer> previousInflationRolls = playerRoundRepository.findByPlayerIdOrderByRoundNumber(player.getId())
                .stream()
                .map(pRound -> pRound.getInflationDiceRoll())
                .filter(java.util.Objects::nonNull)
                .collect(java.util.ArrayList::new, java.util.ArrayList::add, java.util.ArrayList::addAll);

        // Add current round's roll to the list for calculation
        if (pr.getInflationDiceRoll() != null) {
            previousInflationRolls.add(pr.getInflationDiceRoll());
        }

        long housingCost = player.getHousingType().calculateCost(game.getCurrentRound(), previousInflationRolls);
        player.setCashBalance(player.getCashBalance() - housingCost);
        pr.setHousingCost(housingCost);

        // Step 3: Loan Repayment
        validateLoanPayment(player, loanPayment);
        player.setCashBalance(player.getCashBalance() - loanPayment);
        player.setLoanBalance(player.getLoanBalance() - loanPayment);
        pr.setLoanPayment(loanPayment);
    }

    private void processDiceEventAndUncertainty(Player player, Game game, PlayerRound pr) {
        int diceRoll = random.nextInt(6) + 1;
        player.setLastDiceRoll(diceRoll); // Save for next round's rent inflation

        DiceOutcome outcome = DiceOutcome.fromRoll(diceRoll);
        long eventAmount = 0;

        switch (outcome) {
            case JOB_LOSS -> player.setMissNextSalary(true);
            case MEDICAL_EMERGENCY, FAMILY_EMERGENCY, FAMILY_SUPPORT -> {
                player.setCashBalance(player.getCashBalance() - outcome.getAmount());
                eventAmount = -outcome.getAmount();
            }
            case INVESTMENT_A -> {
                player.setCashBalance(player.getCashBalance() - outcome.getAmount());
                player.setPendingInvestmentReturn(900_000L);
                eventAmount = -outcome.getAmount();
            }
            case INVESTMENT_B -> {
                player.setCashBalance(player.getCashBalance() - outcome.getAmount());
                player.setInvestmentBPerRoundReturn(player.getInvestmentBPerRoundReturn() + 340_000L);
                eventAmount = -outcome.getAmount();
            }
        }

        pr.setDiceRoll(diceRoll);
        pr.setDiceOutcome(outcome);
        pr.setDiceEventAmount(eventAmount);
    }

    private void finalizeRound(Player player, Game game, PlayerRound pr) {
        // Apply 10% Interest on remaining balance after repayment
        if (player.getLoanBalance() > 0) {
            long interest = Math.round(player.getLoanBalance() * LOAN_INTEREST_RATE);
            player.setLoanBalance(player.getLoanBalance() + interest);
        }

        pr.setLoanBalanceAfter(player.getLoanBalance());
        pr.setCashBalanceEnd(player.getCashBalance());
        pr.setNetWorth(player.getCashBalance() - player.getLoanBalance());
    }

    private void validateLoanPayment(Player player, long payment) {
        if (payment < 0) {
            throw new IllegalArgumentException("Loan payment cannot be negative.");
        }
        if (payment > player.getLoanBalance()) {
            throw new IllegalArgumentException("Cannot pay more than remaining loan: " + player.getLoanBalance());
        }
        if (payment > player.getCashBalance()) {
            throw new IllegalArgumentException("Not enough cash after expenses: " + player.getCashBalance());
        }
    }

    private void advanceRoundIfComplete(Game game) {
        int totalPlayers = playerRepository.countByGameId(game.getId());
        int completed = playerRoundRepository.countByPlayerGameIdAndRoundNumber(game.getId(),
                game.getCurrentRound());
        if (completed < totalPlayers)
            return;
        if (game.getCurrentRound() >= game.getTotalRounds()) {
            game.setStatus(GameStatus.ENDED);
            game.setEndedAt(LocalDateTime.now());
        } else {
            game.setCurrentRound(game.getCurrentRound() + 1);
        }
        gameRepository.save(game);
    }
}
