package com.monopoly.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "housing_options")
@Getter
@Setter
@NoArgsConstructor
public class HousingOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private HousingType housingType;

    @Column(unique = true)
    private String name;
    @Column
    private Long baseCost;
    @Column
    private Boolean isFixedRate;
    @Column
    private Double fixedRatePercent;
    @Column
    private Double inflationRatePerDicePip;
}
