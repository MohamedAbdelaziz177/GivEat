package com.abdelaziz26.GivEat.DTOs.Request;

import com.abdelaziz26.GivEat.DTOs.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Getter
@Setter
public class UpdateRequestDto extends Dto {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private double quantity;

    @NotBlank
    private String unit = "kg";

    private LocalDateTime expiryLimit;

    private String condition;

    @NotBlank
    private String foodCategory;

    @Range(min = 1, max = 5)
    private int urgency;

    private boolean requiresHalal = false;

    private boolean requiresKosher = false;

    private boolean vegetarianOnly = false;
}
