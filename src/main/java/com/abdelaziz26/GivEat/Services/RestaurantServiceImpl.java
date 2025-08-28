package com.abdelaziz26.GivEat.Services;

import com.abdelaziz26.GivEat.Core.Entities.Charity;
import com.abdelaziz26.GivEat.Core.Entities.Restaurant;
import com.abdelaziz26.GivEat.Core.Interfaces.RestaurantService;
import com.abdelaziz26.GivEat.Core.MagicValues;
import com.abdelaziz26.GivEat.Core.Repositories.CharityRepository;
import com.abdelaziz26.GivEat.Core.Repositories.RestaurantRepository;
import com.abdelaziz26.GivEat.Core.Repositories.UserRepo;
import com.abdelaziz26.GivEat.DTOs.Charity.CreateCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.ReadCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.UpdateCharityDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.CreateRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.ReadRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.UpdateRestaurantDto;
import com.abdelaziz26.GivEat.Mappers.CharityMapper;
import com.abdelaziz26.GivEat.Mappers.RestaurantMapper;
import com.abdelaziz26.GivEat.Utils.AuthUtil;
import com.abdelaziz26.GivEat.Utils.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final UserRepo userRepo;
    private final CloudinaryService cloudinaryService;
    private final AuthUtil authUtil;

    public ReadRestaurantDto getById(Long id) {
        return restaurantMapper.toResponse(restaurantRepository.findById(id).orElseThrow());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ReadRestaurantDto> getAll() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurants.stream().map(restaurantMapper::toResponse).toList();
    }

    public ReadRestaurantDto create(CreateRestaurantDto createRestaurantDto) throws IOException {

        Long userId = authUtil.getUserId();

        String imageUrl = cloudinaryService
                 .upload(createRestaurantDto.getLogo(), MagicValues.RESTAURANT_FOLDER);

        Restaurant restaurant = restaurantMapper.toEntity(createRestaurantDto, imageUrl);

        restaurant.setUser(userRepo.findById(userId).orElseThrow(() ->
                new AuthorizationServiceException("User not found with id: " + userId)));

        restaurant.setImageUrl(imageUrl);

        return restaurantMapper.toResponse(restaurantRepository.save(restaurant));
    }

    public ReadRestaurantDto update(Long id, UpdateRestaurantDto restaurantDto) {

        Long userId = authUtil.getUserId();

        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Restaurant not found with id: " + id));

        if (!restaurant.getUser().getId().equals(userId)) {
            throw new AuthorizationServiceException("User not authorized to update this restaurant");
        }

        return restaurantMapper
                .toResponse(restaurantRepository
                        .save(restaurantMapper
                                .toEntity(restaurantDto, restaurant)));
    }
}
