package com.monopoly.dto.response;

import com.monopoly.data.model.HousingType;
import com.monopoly.data.model.PlayerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class PlayerResponse {
    private Long id;
    private String name;
    private PlayerStatus status;
    private HousingType housingType;
    private Integer turnOrder;
    private Long creditScore;
    private String cashBalance;
    private String loanBalance;
    private String netWorth;
}
