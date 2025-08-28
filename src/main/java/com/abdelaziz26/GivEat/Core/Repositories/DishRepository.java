package com.abdelaziz26.GivEat.Core.Repositories;

import com.abdelaziz26.GivEat.Core.Entities.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishRepository extends JpaRepository<Dish, Long> {

    Optional<Dish> findByNameIgnoreCase(String name);
}
