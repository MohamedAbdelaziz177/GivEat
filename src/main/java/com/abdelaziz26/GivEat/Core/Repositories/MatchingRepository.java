package com.abdelaziz26.GivEat.Core.Repositories;

import com.abdelaziz26.GivEat.Core.Entities.Charity;
import com.abdelaziz26.GivEat.Core.Entities.Matching;
import com.abdelaziz26.GivEat.Core.Entities.Restaurant;
import com.abdelaziz26.GivEat.Core.Enums.MatchingStatus;
import jakarta.validation.constraints.Null;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

    @Query("""
           SELECT m FROM Matching m
           JOIN FETCH m.foodItem fi
           JOIN FETCH fi.restaurant r
           WHERE r.id = :restaurantId
           AND (:matchingStatus IS NULL OR m.matchingStatus = :matchingStatus)
           """)
    List<Matching> findAllByRestaurantId(@Param("restaurantId") Long restaurantId, @Param("matchingStatus") MatchingStatus matchingStatus);

    @Query("""
           SELECT m FROM Matching m
           JOIN FETCH m.foodRequest fr
           JOIN FETCH fr.charity c
           WHERE c.id = :charityId
           AND (:matchingStatus IS NULL OR m.matchingStatus = :matchingStatus)
           """)
    List<Matching> findAllByCharityId(@Param("charityId") Long charityId, @Param("matchingStatus") MatchingStatus matchingStatus);

    @Modifying
    @Query("""
           UPDATE Matching m
           SET m.matchingStatus = 'REJECTED'
           WHERE m.id = :matchingId AND m.matchingStatus = 'IGNORED'
           """)
    void rejectOthersWhenAcceptance(@Param("matchingId") Long matchingId);

}
