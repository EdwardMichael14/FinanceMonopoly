package com.monopoly.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "players")
@Getter
@Setter
@NoArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Game game;

    private long cashBalance = 2_400_000L;
    private long loanBalance = 1_500_000L;
    private long creditScore = 500L;

    @Enumerated(EnumType.STRING)
    private HousingType housingType;

    private boolean missNextSalary = false;
    private long pendingInvestmentReturn = 0L;
    private long investmentBPerRoundReturn = 0L;

    private int turnOrder;
    private int lastDiceRoll = 1;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private List<PlayerRound> playerRounds = new ArrayList<>();
}
