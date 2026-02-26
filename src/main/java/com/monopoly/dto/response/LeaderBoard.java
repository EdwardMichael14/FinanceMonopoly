package com.monopoly.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LeaderBoard {
    private int roundNumber;
    private List<PlayerStanding> standings;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PlayerStanding {
        private int rank;
        private Long playerId;
        private String playerName;
        private long netWorth;
        private long cashBalance;
        private long loanBalance;
    }
}
