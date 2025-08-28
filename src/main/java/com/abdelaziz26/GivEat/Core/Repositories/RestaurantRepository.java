package com.abdelaziz26.GivEat.Core.Repositories;

import com.abdelaziz26.GivEat.Core.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByUser_Id(Long id);
}
