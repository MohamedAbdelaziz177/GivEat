package com.abdelaziz26.GivEat.DTOs.FoodItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReadFoodItemDto {

    private Long id;

    private String name;

    private String description;

    private double quantity;

    private String quantityUnit;

    private String foodCondition;

    private LocalDateTime expiryDate;

    private List<String> imageUrls;
}
