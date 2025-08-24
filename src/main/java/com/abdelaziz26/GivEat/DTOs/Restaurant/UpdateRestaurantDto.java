package com.abdelaziz26.GivEat.DTOs.Restaurant;

import com.abdelaziz26.GivEat.Core.Enums.FoodTypeEn;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateRestaurantDto {

    private MultipartFile logo;

    @Size(min = 4, max = 30)
    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 4, max = 12)
    private String ContactNumber;

    @NotBlank
    @Size(min = 4, max = 120)
    private String description;

    @Size(min = 1)
    @Enumerated(EnumType.STRING)
    List<FoodTypeEn> foodTypes;

    @Size(min = 1)
    List<String> dishes;

    @Size(min = 1)
    List<String> locations;
}
