package com.monopoly.dto.response;

import com.monopoly.data.model.HousingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PlayerResponse {
    private Long id;
    private String name;
    private HousingType housingType;
    private int turnOrder;
    private long creditScore;
    private long cashBalance;
    private long loanBalance;
    private long netWorth;
}
