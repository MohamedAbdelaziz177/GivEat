package com.abdelaziz26.GivEat.Core.Interfaces;

import com.abdelaziz26.GivEat.DTOs.Restaurant.CreateRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.ReadRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.UpdateRestaurantDto;

import java.io.IOException;
import java.util.List;

public interface RestaurantService {

    ReadRestaurantDto getById(Long id);
    List<ReadRestaurantDto> getAll();
    ReadRestaurantDto create(CreateRestaurantDto createRestaurantDto) throws IOException;
    ReadRestaurantDto update(Long id, UpdateRestaurantDto updateRestaurantDto) throws IOException;
}
