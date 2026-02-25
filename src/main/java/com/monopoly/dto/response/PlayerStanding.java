package com.monopoly.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlayerStanding {
    private Integer rank;
    private Long playerId;
    private String playerName;
    private Long netWorthKobo;
    private Long cashBalanceKobo;
    private Long loanBalanceKobo;



    public String getFormattedNetWorth() {
        return String.format("₦%,d", netWorthKobo / 100);
    }
}
