package com.monopoly.data.repository;

import com.monopoly.data.model.HousingOption;
import com.monopoly.data.model.HousingType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HousingOptionRepository extends JpaRepository<HousingOption, Long> {
    Optional<HousingOption> findByHousingType(HousingType housingType);
}
