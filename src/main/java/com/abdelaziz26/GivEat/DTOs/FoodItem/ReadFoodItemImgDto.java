package com.abdelaziz26.GivEat.DTOs.FoodItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReadFoodItemImgDto {
    private Long id;
    private String imgUrl;
}
