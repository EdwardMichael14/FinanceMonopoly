package com.monopoly.dto.response;


import com.monopoly.data.model.GameStatus;
import com.monopoly.data.model.RoundPhase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class GameResponse {
    private Long id;
    private String gameCode;
    private GameStatus status;
    private Integer currentRound;
    private Integer totalRounds;
    private RoundPhase currentPhase;
    private Integer maxPlayers;
    private Integer currentPlayerCount;
    private List<PlayerResponse> players;
}
