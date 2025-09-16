package com.abdelaziz26.GivEat.Core.Interfaces;

import com.abdelaziz26.GivEat.DTOs.FoodItem.ReadFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.Matching.FoodItemMatchedDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface MatchingService {

    @PreAuthorize("hasRole('CHARITY')")
    List<FoodItemMatchedDto> getMatchedItems(Long requestId);

    @PreAuthorize("hasRole('CHARITY')")
    void requestMatchedItem(Long itemId);

    @PreAuthorize("hasRole('RESTAURANT')")
    void acceptMatchRequest(Long requestId);

    @PreAuthorize("hasRole('RESTAURANT')")
    void rejectMatchRequest(Long requestId);

    @PreAuthorize("hasRole('CHARITY')")
    List<FoodItemMatchedDto> getMatchesByCharity();

    @PreAuthorize("hasRole('RESTAURANT')")
    List<FoodItemMatchedDto> getMatchesByRestaurant();
}
