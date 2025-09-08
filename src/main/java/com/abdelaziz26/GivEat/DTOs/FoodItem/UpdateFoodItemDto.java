package com.abdelaziz26.GivEat.DTOs.FoodItem;

import com.abdelaziz26.GivEat.DTOs.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateFoodItemDto extends Dto{

    @Size(min = 10, max = 200)
    private String description;

    @NotNull
    private double quantity;

    @NotBlank
    private String unit;

    @NotBlank
    private String condition;

    @NotBlank
    private String foodCategory;

    private String itemStatus;

    private boolean halalCertified = true;

    private boolean kosherCertified;

    private boolean vegetarianFriendly;

    private LocalDateTime expiryDate;

    @Size(min = 1, max = 5)
    List<MultipartFile> images = new ArrayList<>();
}
