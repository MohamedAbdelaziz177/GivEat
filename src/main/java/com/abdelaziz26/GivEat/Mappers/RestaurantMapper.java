package com.abdelaziz26.GivEat.Mappers;

import com.abdelaziz26.GivEat.Core.Entities.Dish;
import com.abdelaziz26.GivEat.Core.Entities.FoodType;
import com.abdelaziz26.GivEat.Core.Entities.Restaurant;
import com.abdelaziz26.GivEat.Core.Interfaces.Mapper;
import com.abdelaziz26.GivEat.DTOs.Restaurant.CreateRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.ReadRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.RestaurantLightDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.UpdateRestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .foodTypes(restaurant.getFoodTypes().stream().map(ft -> ft.getName().toString()).toList())
                .id(restaurant.getId())
                .build();
    }

    @Override
    public Restaurant toEntity(UpdateRestaurantDto updateRestaurantDto, Restaurant restaurant) {

        restaurant.setName(updateRestaurantDto.getName());
        restaurant.setContactNumber(updateRestaurantDto.getContactNumber());
        restaurant.setDescription(updateRestaurantDto.getDescription());
        restaurant.setAddress(updateRestaurantDto.getLocations());

        return restaurant;
    }

    public RestaurantLightDto toLightDto(Restaurant restaurant) {
        return RestaurantLightDto.builder()
                .id(restaurant.getId())
                .logoUrl(restaurant.getImageUrl())
                .name(restaurant.getName())
                .ContactNumber(restaurant.getContactNumber())
                .build();
    }
}
