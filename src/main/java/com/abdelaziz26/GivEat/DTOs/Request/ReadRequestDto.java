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

    private Long id;

    private String name;

    private double quantity;

    private String unit;

    private LocalDateTime requestDate;

    private LocalDateTime expiryLimit;

    private String condition;

    private String foodCategory;

    private String status;

    private int urgency;

    private boolean requiresHalal;

    private boolean requiresKosher;

    private boolean vegetarianOnly;

    private ReadCharityDto charityDto;
}
