package com.monopoly.dto.response;

import com.monopoly.data.model.DiceEventType;
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
    private Integer roundNumber;

    private Long salaryReceivedKobo;
    private HousingType housingType;
    private Long housingCostKobo;
    private Long survivalCostKobo;
    private Long loanPaymentKobo;
    private Long loanBalanceRemainingKobo;

    private Integer diceRoll;
    private DiceEventType eventType;
    private String eventDescription;
    private Long eventAmountKobo;

    private Long cashBalanceEndKobo;
    private Long netWorthKobo;





    public String getFormattedNetWorth() {
        return String.format("₦%,d", netWorthKobo / 100);
    }

    public String getFormattedCashBalance() {
        return String.format("₦%,d", cashBalanceEndKobo / 100);
    }
}
