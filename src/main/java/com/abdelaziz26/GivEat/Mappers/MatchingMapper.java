package com.abdelaziz26.GivEat.Mappers;

import com.abdelaziz26.GivEat.Core.Entities.Matching;
import com.abdelaziz26.GivEat.Core.Interfaces.Mapper;
import com.abdelaziz26.GivEat.DTOs.Matching.FoodItemMatchedDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchingMapper {

    private final FoodItemMapper foodItemMapper;

    public FoodItemMatchedDto mapToMatchedItemDto(Matching matching) {
        return FoodItemMatchedDto.builder()
                .matchId(matching.getId())
                .score(matching.getMatchingScore())
                .readFoodItemDto(foodItemMapper.toResponse(matching.getFoodItem()))
                .build();
    }
}
