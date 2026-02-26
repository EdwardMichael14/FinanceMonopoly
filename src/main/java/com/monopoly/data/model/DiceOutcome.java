package com.monopoly.data.model;

import lombok.Getter;

@Getter
@com.fasterxml.jackson.annotation.JsonFormat(shape = com.fasterxml.jackson.annotation.JsonFormat.Shape.OBJECT)
public enum DiceOutcome {

    JOB_LOSS(1, "Job Loss", "You lost your job. You will not receive salary next round.", 0L),
    MEDICAL_EMERGENCY(2, "Medical Emergency", "A medical emergency costs you ₦400,000.", 400_000L),
    FAMILY_EMERGENCY(3, "Family Emergency", "A family emergency costs you ₦300,000.", 300_000L),
    FAMILY_SUPPORT(4, "Family Support", "Family support costs you ₦200,000.", 200_000L),
    INVESTMENT_A(5, "Investment Opportunity A", "Invest ₦500,000 now to receive ₦900,000 next round.", 500_000L),
    INVESTMENT_B(6, "Investment Opportunity B", "Invest ₦600,000 now to receive ₦340,000 in all remaining rounds.",
            600_000L);

    private final int diceValue;
    private final String title;
    private final String description;
    private final long amount;

    DiceOutcome(int diceValue, String title, String description, long amount) {
        this.diceValue = diceValue;
        this.title = title;
        this.description = description;
        this.amount = amount;
    }

    public static DiceOutcome fromRoll(int roll) {
        for (DiceOutcome outcome : values()) {
            if (outcome.diceValue == roll)
                return outcome;
        }
        throw new IllegalArgumentException("Invalid dice roll: " + roll);
    }
}
