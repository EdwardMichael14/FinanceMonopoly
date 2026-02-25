package com.monopoly.dto.request;


import com.monopoly.data.model.HousingType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class PickHousingRequest {
    @NotNull(message = "Housing type is required")
    private HousingType housingType;
}
