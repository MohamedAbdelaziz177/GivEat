package com.abdelaziz26.GivEat.DTOs.Charity;

import com.abdelaziz26.GivEat.DTOs.Dto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ReadCharityDto extends Dto {

    private Long id;

    private String name;

    private String contactNumber;

    private String address;

    private String description;

    private String logoUrl;
}
