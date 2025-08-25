package com.abdelaziz26.GivEat.DTOs.Request;

import com.abdelaziz26.GivEat.DTOs.Charity.ReadCharityDto;
import com.abdelaziz26.GivEat.DTOs.Dto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReadRequestDto extends Dto {

    private String imageUrl;

    private String foodName;

    private double quantity;

    private String unit = "kg";

    private LocalDateTime requestDate;

    private ReadCharityDto charityDto;
}
