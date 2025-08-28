package com.abdelaziz26.GivEat.Core.Repositories;

import com.abdelaziz26.GivEat.Core.Entities.FoodType;
import com.abdelaziz26.GivEat.Core.Enums.FoodTypeEn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodTypeRepository extends JpaRepository<FoodType, Long> {

    Optional<FoodType> findByName(FoodTypeEn name);
}
