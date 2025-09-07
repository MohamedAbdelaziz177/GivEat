package com.abdelaziz26.GivEat.Mappers;

import com.abdelaziz26.GivEat.Core.Entities.FoodRequest;
import com.abdelaziz26.GivEat.Core.Enums.QuantityUnit;
import com.abdelaziz26.GivEat.Core.Interfaces.Mapper;
import com.abdelaziz26.GivEat.DTOs.Request.CreateRequestDto;
import com.abdelaziz26.GivEat.DTOs.Request.ReadRequestDto;
import com.abdelaziz26.GivEat.DTOs.Request.UpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodRequestMapper implements Mapper<FoodRequest, ReadRequestDto, CreateRequestDto, UpdateRequestDto> {

    private final CharityMapper charityMapper;

    @Override
    public FoodRequest toEntity(CreateRequestDto createRequestDto) {
        return FoodRequest.builder()
                .name(createRequestDto.getFoodName())
                .quantity(createRequestDto.getQuantity())
                .unit(QuantityUnit.valueOf(createRequestDto.getUnit().toUpperCase()))
                .build();
    }

    @Override
    public ReadRequestDto toResponse(FoodRequest foodRequest) {
        return ReadRequestDto.builder()
                .foodName(foodRequest.getName())
                .quantity(foodRequest.getQuantity())
                .unit(foodRequest.getUnit().name())
                .requestDate(foodRequest.getRequestDate())
                .charityDto(charityMapper.toResponse(foodRequest.getCharity()))
                .build();
    }

    @Override
    public FoodRequest toEntity(UpdateRequestDto updateRequestDto, FoodRequest foodRequest) {
        foodRequest.setName(updateRequestDto.getFoodName());
        foodRequest.setQuantity(updateRequestDto.getQuantity());
        foodRequest.setUnit(QuantityUnit.valueOf(updateRequestDto.getUnit().toUpperCase()));
        return foodRequest;
    }
}
