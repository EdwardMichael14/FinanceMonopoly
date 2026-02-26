package com.monopoly.dto.response;

import com.monopoly.data.model.DiceOutcome;
import com.monopoly.data.model.HousingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoundResultResponse {
    private Long playerId;
    private String playerName;
    private int roundNumber;

    private long salaryReceived;
    private HousingType housingType;
    private long housingCost;
    private long survivalCost;
    private long loanPayment;
    private long loanBalanceRemaining;

    private int diceRoll;
    private DiceOutcome diceOutcome;
    private String diceEventDescription;
    private long diceEventAmount;

    private long investmentPayout;
    private long cashBalanceEnd;
    private long netWorth;
}
