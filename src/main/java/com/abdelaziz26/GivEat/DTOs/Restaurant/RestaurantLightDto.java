package com.abdelaziz26.GivEat.DTOs.Restaurant;

import com.abdelaziz26.GivEat.DTOs.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RestaurantLightDto extends Dto {

    private Long id;

    private String logoUrl;

    private String name;

    private String ContactNumber;
}
