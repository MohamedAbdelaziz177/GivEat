package com.abdelaziz26.GivEat.DTOs.Request;

import com.abdelaziz26.GivEat.DTOs.Charity.ReadCharityDto;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReadRequestDto {

    private String imageUrl;

    private String foodName;

    private double quantity;

    private String unit = "kg";

    private LocalDate requestDate;

    private ReadCharityDto charityDto;
}
