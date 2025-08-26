package com.abdelaziz26.GivEat.Mappers;


import com.abdelaziz26.GivEat.Core.Entities.FoodItem;
import com.abdelaziz26.GivEat.Core.Enums.FoodCondition;
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

    private final CloudinaryService cloudinaryService;

    @Override
    public FoodItem toEntity(CreateFoodItemDto createFoodItemDto, List<String> imagesUrls) {

        //List<String> imageUrls = createFoodItemDto
        //        .getImages()
        //        .stream()
        //        .map(image -> {
        //            try {
        //                return cloudinaryService.upload(image, MagicValues.FOOD_FOLDER);
        //            } catch (IOException e) {
        //                throw new RuntimeException(e);
        //            }
        //        })
        //        .toList();

        return com.abdelaziz26.GivEat.Core.Entities.FoodItem.builder()
                .name(createFoodItemDto.getName())
                .description(createFoodItemDto.getDescription())
                .quantity(createFoodItemDto.getQuantity())
                .unit(QuantityUnit.valueOf(createFoodItemDto.getQuantityUnit().toUpperCase()))
                .condition(FoodCondition.valueOf(createFoodItemDto.getFoodCondition().toUpperCase()))
                .expiryDate(createFoodItemDto.getExpiryDate())
                .imagesUrls(imagesUrls)
                .build();

    }

    @Override
    public ReadFoodItemDto toResponse(FoodItem foodItem) {
        return ReadFoodItemDto.builder()
                .name(foodItem.getName())
                .id(foodItem.getId())
                .description(foodItem.getDescription())
                .expiryDate(foodItem.getExpiryDate())
                .foodCondition(foodItem.getCondition().toString())
                .quantity(foodItem.getQuantity())
                .quantityUnit(foodItem.getUnit().toString())
                .imageUrls(foodItem.getImagesUrls())
                .build();
    }

    @Override
    public FoodItem toEntity(UpdateFoodItemDto updateFoodItemDto, FoodItem foodItem) {

        foodItem.setName(updateFoodItemDto.getName());
        foodItem.setDescription(updateFoodItemDto.getDescription());
        foodItem.setQuantity(updateFoodItemDto.getQuantity());
        foodItem.setUnit(QuantityUnit.valueOf(updateFoodItemDto.getQuantityUnit().toUpperCase()));
        foodItem.setCondition(FoodCondition.valueOf(updateFoodItemDto.getFoodCondition().toUpperCase()));
        foodItem.setExpiryDate(updateFoodItemDto.getExpiryDate());
        return foodItem;
    }


}
