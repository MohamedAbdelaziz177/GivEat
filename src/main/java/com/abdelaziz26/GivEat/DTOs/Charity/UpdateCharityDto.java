package com.abdelaziz26.GivEat.DTOs.Charity;

import com.abdelaziz26.GivEat.DTOs.Dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateCharityDto extends Dto {

    @NotBlank
    private String name;

    @NotBlank
    private String contactNumber;

    @Size(min = 10, max = 200)
    private String description;

    private String location;
}
