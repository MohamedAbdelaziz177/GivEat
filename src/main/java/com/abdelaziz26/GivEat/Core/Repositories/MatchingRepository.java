package com.abdelaziz26.GivEat.Core.Repositories;

import com.abdelaziz26.GivEat.Core.Entities.Charity;
import com.abdelaziz26.GivEat.Core.Entities.Matching;
import com.abdelaziz26.GivEat.Core.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

    @Query("""
           SELECT m FROM Matching m 
           JOIN FETCH m.foodRequest fr
           JOIN FETCH fr.charity c
           WHERE c.id = :charityId
           """)
    List<Matching> findAllByCharityId(Long charityId);

    @Query("""
           SELECT m FROM Matching m
           JOIN FETCH m.foodItem fi
           JOIN FETCH fi.restaurant r
           WHERE r.id = :restaurantId
           """)
    List<Matching> findAllByRestaurantId(Long restaurantId);

}
