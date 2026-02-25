package com.monopoly.data.repository;

import com.monopoly.data.model.DiceEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiceEventRepository extends JpaRepository<DiceEvent, Long> {
    Optional<DiceEvent> findByDiceValue(Integer diceValue);
}
