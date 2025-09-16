package com.abdelaziz26.GivEat.DTOs.FoodItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddFoodItemImgDto {
    private Long foodItemId;
    private MultipartFile image;
}
