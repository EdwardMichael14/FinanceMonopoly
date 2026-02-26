package com.monopoly.dto.response;

import com.monopoly.data.model.DiceOutcome;
import com.monopoly.data.model.HousingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PlayerHistoryResponse {

    private Long playerId;
    private String playerName;
    private long creditScore;
    private long currentCashBalance;
    private long currentLoanBalance;
    private long currentNetWorth;
    private List<RoundSummary> rounds;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RoundSummary {
        private int roundNumber;
        private long salaryReceived;
        private HousingType housingType;
        private long housingCost;
        private long survivalCost;
        private long loanPayment;
        private long loanBalanceAfter;
        private int diceRoll;
        private DiceOutcome diceOutcome;
        private long diceEventAmount;
        private long investmentPayout;
        private long cashBalanceEnd;
        private long netWorth;
    }
}
