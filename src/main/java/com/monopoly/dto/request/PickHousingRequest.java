package com.monopoly.dto.request;

import com.monopoly.data.model.HousingType;
import lombok.Data;

@Data
public class PickHousingRequest {
    private HousingType housingType;
}
