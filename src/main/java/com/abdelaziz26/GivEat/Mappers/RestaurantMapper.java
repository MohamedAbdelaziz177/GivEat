package com.abdelaziz26.GivEat.Mappers;

import com.abdelaziz26.GivEat.Core.Entities.Dish;
import com.abdelaziz26.GivEat.Core.Entities.FoodType;
import com.abdelaziz26.GivEat.Core.Entities.Restaurant;
import com.abdelaziz26.GivEat.Core.Enums.FoodTypeEn;
import com.abdelaziz26.GivEat.Core.Interfaces.Mapper;
import com.abdelaziz26.GivEat.Core.MagicValues;
import com.abdelaziz26.GivEat.DTOs.Charity.CreateCharityDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.CreateRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.ReadRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.UpdateRestaurantDto;
import com.abdelaziz26.GivEat.Utils.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantMapper implements Mapper<Restaurant, ReadRestaurantDto, CreateRestaurantDto, UpdateRestaurantDto> {


    @Override
    public Restaurant toEntity(CreateRestaurantDto createRestaurantDto, Object... extra) {

        return Restaurant.builder()
                .name(createRestaurantDto.getName())
                .contactNumber(createRestaurantDto.getContactNumber())
                .description(createRestaurantDto.getDescription())
                .address(createRestaurantDto.getLocation())
                .imageUrl(extra[0].toString())
                .build();

    }

    @Override
    public ReadRestaurantDto toResponse(Restaurant restaurant) {

        return ReadRestaurantDto.builder()
                .ContactNumber(restaurant.getContactNumber())
                .name(restaurant.getName())
                .logoUrl(restaurant.getImageUrl())
                .description(restaurant.getDescription())
                .locations(restaurant.getAddress())
                .dishes(restaurant.getDishes().stream().map(Dish::getName).collect(Collectors.toList()))
                .foodTypes(restaurant.getFoodTypes().stream().map(FoodType::getName).toList())
                .build();
    }

    @Override
    public Restaurant toEntity(UpdateRestaurantDto updateRestaurantDto, Restaurant restaurant) {

        restaurant.setName(updateRestaurantDto.getName());
        restaurant.setContactNumber(updateRestaurantDto.getContactNumber());
        restaurant.setDescription(updateRestaurantDto.getDescription());
        restaurant.setAddress(updateRestaurantDto.getLocations().get(0));

        return restaurant;
    }
}
