package com.monopoly.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "player_rounds")
@Getter
@Setter
@NoArgsConstructor
public class PlayerRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Player player;

    private int roundNumber;

    private long salaryReceived;

    @Enumerated(EnumType.STRING)
    private HousingType housingType;
    private long housingCost;

    private long survivalCost;
    private long loanPayment;
    private long loanBalanceAfter;

    private int diceRoll;
    private Integer inflationDiceRoll;
    @Enumerated(EnumType.STRING)
    private DiceOutcome diceOutcome;
    private long diceEventAmount;

    private long investmentPayout;
    private long cashBalanceEnd;
    private long netWorth;
}