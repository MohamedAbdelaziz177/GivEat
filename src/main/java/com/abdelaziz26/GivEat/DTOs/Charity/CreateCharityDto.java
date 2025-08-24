package com.abdelaziz26.GivEat.DTOs.Charity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateCharityDto {

    @NotBlank
    private String name;

    @NotBlank
    private String contactNumber;

    @Size(min = 10, max = 200)
    private String description;

    @Size(min = 1)
    private List<String> locations;

}
