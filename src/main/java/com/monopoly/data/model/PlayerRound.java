package com.monopoly.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "round_id")
    private Round round;
    private Long salaryReceivedKobo;
    @Enumerated(EnumType.STRING)
    private HousingType housingType;
    private Long housingCostPaidKobo;
    private Long survivalCostKobo = 70_000_000L;
    private Long loanPaymentKobo;
    private Long loanBalanceAfterKobo;
    private Integer diceRoll;
    @Enumerated(EnumType.STRING)
    private DiceEventType eventType;
    private Long eventAmountKobo;
    private Long cashBalanceEndKobo;
    private Long netWorthKobo;
    private Boolean isCompleted = false;
    private LocalDateTime completedAt;
}