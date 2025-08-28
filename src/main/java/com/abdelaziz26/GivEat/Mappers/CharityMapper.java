package com.abdelaziz26.GivEat.Mappers;

import com.abdelaziz26.GivEat.Core.Entities.Charity;
import com.abdelaziz26.GivEat.Core.Interfaces.Mapper;
import com.abdelaziz26.GivEat.DTOs.Charity.CreateCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.ReadCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.UpdateCharityDto;
import org.springframework.stereotype.Service;

@Service
public class CharityMapper implements Mapper<Charity, ReadCharityDto, CreateCharityDto, UpdateCharityDto> {

    @Override
    public Charity toEntity(CreateCharityDto dto, Object... extra) {

        if(dto == null)
            return null;

        return Charity.builder()
                .address(dto.getLocations().get(0))
                .name(dto.getName())
                .contactNumber(dto.getContactNumber())
                .description(dto.getDescription())
                .imageUrl(extra[0].toString() )
                .build();
    }

    @Override
    public ReadCharityDto toResponse(Charity charity) {

        if (charity == null) {
            return null;
        }
        return ReadCharityDto.builder()
                .id(charity.getId())
                .name(charity.getName())
                .contactNumber(charity.getContactNumber())
                .description(charity.getDescription())
                .address(charity.getAddress())
                .build();

    }

    @Override
    public Charity toEntity(UpdateCharityDto dto, Charity charity) {

        charity.setName(dto.getName());
        charity.setContactNumber(dto.getContactNumber());
        charity.setDescription(dto.getDescription());
        charity.setAddress(dto.getLocations().get(0));

        return charity;
    }
}
