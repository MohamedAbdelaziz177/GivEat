package com.abdelaziz26.GivEat.DTOs.Charity;

import com.abdelaziz26.GivEat.DTOs.Dto;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CreateCharityDto extends Dto {

    @NotBlank
    private String name;

    @NotBlank
    private String contactNumber;

    @Size(min = 10, max = 200)
    private String description;

    @Size(min = 1)
    private String location;

    private MultipartFile logo;

}
