package com.monopoly.dto.response;

import com.monopoly.data.model.GameStatus;
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
    private int currentRound;
    private int totalRounds;
    private int currentPlayerCount;
    private List<PlayerResponse> players;
}
