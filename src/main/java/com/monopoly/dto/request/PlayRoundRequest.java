package com.monopoly.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class PlayRoundRequest {
    @NotNull(message = "Loan payment amount is required")
    @Min(value = 0, message = "Loan payment cannot be negative")
    private Long loanPaymentNaira;
}
