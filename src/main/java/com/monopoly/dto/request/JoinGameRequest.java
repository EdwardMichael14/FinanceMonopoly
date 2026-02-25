package com.monopoly.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class JoinGameRequest {

    @NotBlank(message = "Player name is required")
    private String playerName;
}
