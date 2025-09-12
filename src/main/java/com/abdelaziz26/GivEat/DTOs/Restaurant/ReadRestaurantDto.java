package com.abdelaziz26.GivEat.DTOs.Restaurant;

import com.abdelaziz26.GivEat.Core.Enums.FoodTypeEn;
import com.abdelaziz26.GivEat.DTOs.Dto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReadRestaurantDto extends Dto {

    private Long id;

    private String logoUrl;

    private String name;

    private String ContactNumber;

    private String description;

    List<String> foodTypes;

    List<String> dishes;

    String locations;
}
