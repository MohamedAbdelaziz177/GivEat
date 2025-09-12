package com.abdelaziz26.GivEat.DTOs.Matching;

import com.abdelaziz26.GivEat.DTOs.FoodItem.ReadFoodItemDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FoodItemMatchedDto {

    private Long matchId;
    private ReadFoodItemDto readFoodItemDto;
    private double score;

}
