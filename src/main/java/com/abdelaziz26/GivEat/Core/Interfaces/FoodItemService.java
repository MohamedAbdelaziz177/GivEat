package com.abdelaziz26.GivEat.Core.Interfaces;

import com.abdelaziz26.GivEat.DTOs.FoodItem.*;

import java.io.IOException;
import java.util.List;

public interface FoodItemService {

    ReadFoodItemDto getById(Long id);
    List<ReadFoodItemDto> getAll();
    ReadFoodItemDto create(CreateFoodItemDto createFoodItemDto) throws IOException;
    ReadFoodItemDto update(Long id, UpdateFoodItemDto updateFoodItemDto) throws IOException;
    List<ReadFoodItemDto> getAllByRestaurantId(Long restaurantId);
    List<ReadFoodItemDto> getMyFoodItems();
    void deleteFoodItem(Long id);
    ReadFoodItemDto addImg(AddFoodItemImgDto addFoodItemImgDto) throws IOException;
}
