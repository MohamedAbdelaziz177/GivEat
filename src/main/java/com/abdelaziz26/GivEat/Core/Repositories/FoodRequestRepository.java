package com.abdelaziz26.GivEat.Core.Repositories;

import com.abdelaziz26.GivEat.Core.Entities.Charity;
import com.abdelaziz26.GivEat.Core.Entities.FoodRequest;
import com.abdelaziz26.GivEat.Core.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRequestRepository extends JpaRepository<FoodRequest, Long> {
    List<FoodRequest> findByCharity(Charity charity);

    //List<FoodRequest> findByRestaurant(Restaurant restaurant);
}
