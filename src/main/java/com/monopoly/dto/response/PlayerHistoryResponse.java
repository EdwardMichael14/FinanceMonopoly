package com.monopoly.dto.response;

import com.monopoly.data.model.DiceEventType;
import com.monopoly.data.model.HousingType;
import com.monopoly.data.model.PlayerStatus;
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
    private PlayerStatus status;
    private Long creditScore;
    private String currentCashBalance;
    private String currentLoanBalance;
    private String currentNetWorth;
    private List<RoundSummary> rounds;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RoundSummary {
        private Integer roundNumber;
        private String salaryReceived;
        private HousingType housingType;
        private String housingCost;
        private String survivalCost;
        private String loanPayment;
        private String loanBalanceAfter;
        private Integer diceRoll;
        private DiceEventType eventType;
        private String eventAmount;
        private String cashBalanceEnd;
        private String netWorth;
    }
}
