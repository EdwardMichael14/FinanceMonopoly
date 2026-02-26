package com.monopoly.data.model;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HousingTypeTest {

    @Test
    public void testParentHouseCumulativeInflation() {
        HousingType housingType = HousingType.PARENT_HOUSE;
        long baseCost = housingType.getBaseCost(); // 150,000

        // Round 1: No inflation
        assertEquals(150000L, housingType.calculateCost(1, List.of()));

        // Round 2: Inflation after one dice roll of 3 (6% inflation)
        // 150,000 * 1.06 = 159,000
        assertEquals(159000L, housingType.calculateCost(2, List.of(3)));

        // Round 3: Inflation after dice rolls of 3 and 2
        // Roll 3 -> 6% inflation
        // Roll 2 -> 4% inflation
        // 150,000 * 1.06 * 1.04 = 150,000 * 1.1024 = 165,360
        assertEquals(165360L, housingType.calculateCost(3, List.of(3, 2)));
    }

    @Test
    public void testFixedInterestHousing() {
        HousingType housingType = HousingType.SHARED_APARTMENT; // 300,000, 20% interest

        // Round 1
        assertEquals(300000L, housingType.calculateCost(1, List.of()));

        // Round 2
        // 300,000 * 1.2 = 360,000
        assertEquals(360000L, housingType.calculateCost(2, List.of(1, 2, 3))); // Dice rolls shouldn't affect it
    }
}
