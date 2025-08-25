package com.abdelaziz26.GivEat.DTOs.Request;

import com.abdelaziz26.GivEat.DTOs.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateRequestDto extends Dto {

    @NotBlank
    private String foodName;

    @NotNull
    private double quantity;

    private String unit = "kg";

}
