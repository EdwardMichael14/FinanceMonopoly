package com.monopoly.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class LeaderBoard {
    private Integer roundNumber;
    private List<PlayerStanding> standings;



    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlayerStanding {
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
}
