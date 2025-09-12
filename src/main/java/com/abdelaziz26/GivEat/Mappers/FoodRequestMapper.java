package com.abdelaziz26.GivEat.Mappers;

import com.abdelaziz26.GivEat.Core.Entities.FoodRequest;
import com.abdelaziz26.GivEat.Core.Enums.FoodCategory;
import com.abdelaziz26.GivEat.Core.Enums.FoodCondition;
import com.abdelaziz26.GivEat.Core.Enums.FoodRequestStatus;
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
                .name(createRequestDto.getName())
                .quantity(createRequestDto.getQuantity())
                .unit(QuantityUnit.valueOf(createRequestDto.getUnit().toUpperCase()))
                .condition(FoodCondition.valueOf(createRequestDto.getCondition()))
                .foodCategory(FoodCategory.valueOf(createRequestDto.getFoodCategory()))
                .requiresHalal(createRequestDto.isRequiresHalal())
                .requiresKosher(createRequestDto.isRequiresKosher())
                .vegetarianOnly(createRequestDto.isVegetarianOnly())
                .urgency(createRequestDto.getUrgency())
                .status(FoodRequestStatus.PENDING)
                .expiryLimit(createRequestDto.getExpiryLimit())
                .build();
    }

    @Override
    public ReadRequestDto toResponse(FoodRequest foodRequest) {
        return ReadRequestDto.builder()
                .id(foodRequest.getId())
                .name(foodRequest.getName())
                .quantity(foodRequest.getQuantity())
                .unit(foodRequest.getUnit().name())
                .requestDate(foodRequest.getRequestDate())
                .condition(foodRequest.getCondition().name())
                .foodCategory(foodRequest.getFoodCategory().name())
                .requiresHalal(foodRequest.isRequiresHalal())
                .requiresKosher(foodRequest.isRequiresKosher())
                .vegetarianOnly(foodRequest.isVegetarianOnly())
                .urgency(foodRequest.getUrgency())
                .status(foodRequest.getStatus().name())
                .expiryLimit(foodRequest.getExpiryLimit())
                .charityDto(charityMapper.toResponse(foodRequest.getCharity()))
                .build();
    }

    @Override
    public FoodRequest toEntity(UpdateRequestDto updateRequestDto, FoodRequest foodRequest) {
        foodRequest.setName(updateRequestDto.getName());
        foodRequest.setCondition(FoodCondition.valueOf(updateRequestDto.getCondition()));
        foodRequest.setFoodCategory(FoodCategory.valueOf(updateRequestDto.getFoodCategory()));
        foodRequest.setRequiresHalal(updateRequestDto.isRequiresHalal());
        foodRequest.setRequiresKosher(updateRequestDto.isRequiresKosher());
        foodRequest.setVegetarianOnly(updateRequestDto.isVegetarianOnly());
        foodRequest.setUrgency(updateRequestDto.getUrgency());
        foodRequest.setExpiryLimit(updateRequestDto.getExpiryLimit());
        foodRequest.setQuantity(updateRequestDto.getQuantity());
        foodRequest.setUnit(QuantityUnit.valueOf(updateRequestDto.getUnit().toUpperCase()));
        return foodRequest;
    }
}
