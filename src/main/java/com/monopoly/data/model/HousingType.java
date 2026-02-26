package com.monopoly.data.model;

import java.util.List;
import lombok.Getter;

@Getter
public enum HousingType {

    PARENT_HOUSE("Stay with Parent/Guardian", 150_000L, 0.0, true),
    SHARED_APARTMENT("Shared Apartment", 300_000L, 0.20, false),
    SINGLE_APARTMENT("Single Apartment", 900_000L, 0.15, false),
    LUXURY_APARTMENT("Luxury Apartment", 1_500_000L, 0.05, false);

    private final String displayName;
    private final long baseCost;
    private final double interestRate;
    private final boolean inflationBased;

    HousingType(String displayName, long baseCost, double interestRate, boolean inflationBased) {
        this.displayName = displayName;
        this.baseCost = baseCost;
        this.interestRate = interestRate;
        this.inflationBased = inflationBased;
    }

    public long calculateCost(int roundNumber, List<Integer> previousDiceRolls) {
        if (roundNumber == 1)
            return baseCost;

        if (inflationBased) {
            // Subject to inflation based on dice rolls of all previous rounds
            // outcome * 2% = inflation percentage
            double totalMultiplier = 1.0;
            for (int roll : previousDiceRolls) {
                double inflation = 0.02 * roll;
                totalMultiplier *= (1 + inflation);
            }
            return Math.round(baseCost * totalMultiplier);
        }

        // Fixed interest based on round number
        return Math.round(baseCost * Math.pow(1 + interestRate, roundNumber - 1));
    }
}
