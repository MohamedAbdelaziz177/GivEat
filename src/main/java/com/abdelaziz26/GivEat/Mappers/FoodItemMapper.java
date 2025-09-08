package com.abdelaziz26.GivEat.Mappers;


import com.abdelaziz26.GivEat.Core.Entities.FoodItem;
import com.abdelaziz26.GivEat.Core.Enums.FoodCategory;
import com.abdelaziz26.GivEat.Core.Enums.FoodCondition;
import com.abdelaziz26.GivEat.Core.Enums.FoodItemStatus;
import com.abdelaziz26.GivEat.Core.Enums.QuantityUnit;
import com.abdelaziz26.GivEat.Core.Interfaces.Mapper;
import com.abdelaziz26.GivEat.DTOs.FoodItem.CreateFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.FoodItem.ReadFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.FoodItem.UpdateFoodItemDto;
import com.abdelaziz26.GivEat.Utils.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodItemMapper implements Mapper<FoodItem, ReadFoodItemDto, CreateFoodItemDto, UpdateFoodItemDto> {

    @Override
    public FoodItem toEntity(CreateFoodItemDto createFoodItemDto, List<String> imagesUrls) {
        return FoodItem.builder()
                .name(createFoodItemDto.getName())
                .description(createFoodItemDto.getDescription())
                .quantity(createFoodItemDto.getQuantity())
                .unit(QuantityUnit.valueOf(createFoodItemDto.getUnit().toUpperCase()))
                .condition(FoodCondition.valueOf(createFoodItemDto.getCondition().toUpperCase()))
                .foodCategory(FoodCategory.valueOf(createFoodItemDto.getFoodCategory().toUpperCase()))
                .foodItemStatus(FoodItemStatus.AVAILABLE)
                .halalCertified(createFoodItemDto.isHalalCertified())
                .kosherCertified(createFoodItemDto.isKosherCertified())
                .vegetarianFriendly(createFoodItemDto.isVegetarianFriendly())
                .expiryDate(createFoodItemDto.getExpiryDate())
                .imagesUrls(imagesUrls)
                .build();

    }

    @Override
    public ReadFoodItemDto toResponse(FoodItem foodItem) {
        return ReadFoodItemDto.builder()
                .id(foodItem.getId())
                .name(foodItem.getName())
                .description(foodItem.getDescription())
                .expiryDate(foodItem.getExpiryDate())
                .foodCondition(foodItem.getCondition().toString())
                .quantity(foodItem.getQuantity())
                .quantityUnit(foodItem.getUnit().toString())
                .foodCategory(foodItem.getFoodCategory().toString())
                .itemStatus(foodItem.getFoodItemStatus().toString())
                .halalCertified(foodItem.isHalalCertified())
                .kosherCertified(foodItem.isKosherCertified())
                .vegetarianFriendly(foodItem.isVegetarianFriendly())
                .imageUrls(foodItem.getImagesUrls())
                .build();
    }

    @Override
    public FoodItem toEntity(UpdateFoodItemDto updateFoodItemDto, FoodItem foodItem) {

        foodItem.setDescription(updateFoodItemDto.getDescription());
        foodItem.setQuantity(updateFoodItemDto.getQuantity());
        foodItem.setUnit(QuantityUnit.valueOf(updateFoodItemDto.getUnit().toUpperCase()));
        foodItem.setCondition(FoodCondition.valueOf(updateFoodItemDto.getCondition().toUpperCase()));
        foodItem.setFoodCategory(FoodCategory.valueOf(updateFoodItemDto.getFoodCategory().toUpperCase()));
        foodItem.setFoodItemStatus(FoodItemStatus.valueOf(updateFoodItemDto.getItemStatus().toUpperCase()));
        foodItem.setHalalCertified(updateFoodItemDto.isHalalCertified());
        foodItem.setKosherCertified(updateFoodItemDto.isKosherCertified());
        foodItem.setVegetarianFriendly(updateFoodItemDto.isVegetarianFriendly());
        foodItem.setFoodItemStatus(FoodItemStatus.valueOf(updateFoodItemDto.getItemStatus().toUpperCase()));
        foodItem.setExpiryDate(updateFoodItemDto.getExpiryDate());

        return foodItem;
    }

}
