package com.abdelaziz26.GivEat.Services;

import com.abdelaziz26.GivEat.Core.Entities.FoodItem;
import com.abdelaziz26.GivEat.Core.Entities.Restaurant;
import com.abdelaziz26.GivEat.Core.Entities.User;
import com.abdelaziz26.GivEat.Core.Interfaces.FoodItemService;
import com.abdelaziz26.GivEat.Core.MagicValues;
import com.abdelaziz26.GivEat.Core.Repositories.FoodItemRepository;
import com.abdelaziz26.GivEat.Core.Repositories.RestaurantRepository;
import com.abdelaziz26.GivEat.Core.Repositories.UserRepo;
import com.abdelaziz26.GivEat.DTOs.FoodItem.CreateFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.FoodItem.ReadFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.FoodItem.UpdateFoodItemDto;
import com.abdelaziz26.GivEat.Mappers.FoodItemMapper;
import com.abdelaziz26.GivEat.Utils.AuthUtil;
import com.abdelaziz26.GivEat.Utils.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FoodItemServiceImpl implements FoodItemService {

    private final FoodItemRepository foodItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepo userRepository;
    private final CloudinaryService cloudinaryService;
    private final FoodItemMapper foodItemMapper;
    private final AuthUtil authUtil;

    public ReadFoodItemDto getById(Long id) {
        return foodItemMapper.toResponse(foodItemRepository.findById(id).orElseThrow());
    }

    public List<ReadFoodItemDto> getAll() {
        List<FoodItem> foodItemList = foodItemRepository.findAll();
        return foodItemList.stream().map(foodItemMapper::toResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<ReadFoodItemDto> getAllByRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow();
        List<FoodItem> foodItemList = foodItemRepository.findAllByRestaurant(restaurant);
        return foodItemList.stream().map(foodItemMapper::toResponse).toList();
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    public List<ReadFoodItemDto> getMyFoodItems()
    {
        Long userId = authUtil.getUserId();
        User user = userRepository.findById(userId).orElseThrow(() ->
                new AuthenticationServiceException("User not found with id: " + userId));
        List<FoodItem> foodItemList = foodItemRepository.findAllByRestaurant(user.getRestaurant());
        return foodItemList.stream().map(foodItemMapper::toResponse).toList();
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @Transactional
    public ReadFoodItemDto create(CreateFoodItemDto createFoodItemDto)
            throws IOException {

        Long userId = authUtil.getUserId();
        List<String> imageUrl = new ArrayList<>();

        for (MultipartFile img : createFoodItemDto.getImages()) {
            imageUrl.add(cloudinaryService.upload(img, MagicValues.FOOD_FOLDER));
        }

        User user = userRepository.findById(userId).orElseThrow(() ->
                new AuthenticationServiceException("User not found with id: " + userId));

        FoodItem item = foodItemMapper.toEntity(createFoodItemDto, imageUrl);

        item.setRestaurant(user.getRestaurant());

        return foodItemMapper.toResponse(foodItemRepository.save(item));
    }

    @PreAuthorize("hasRole('RESTAURANT')")
    @Transactional
    public ReadFoodItemDto update(Long foodItemId, UpdateFoodItemDto updateFoodItemDto)
            throws IOException {

        Long userId = authUtil.getUserId();

        User user = userRepository.findById(userId).orElseThrow(() ->
                new AuthenticationServiceException("User not found with id: " + userId));

        FoodItem item = foodItemRepository.findById(foodItemId).orElseThrow(() ->
                new EntityNotFoundException("Food item not found with id: " + foodItemId));

        if(!Objects.equals(item.getRestaurant().getId(), user.getRestaurant().getId()))
            throw new AuthorizationServiceException("You are not authorized to update this food item");

        item = foodItemMapper.toEntity(updateFoodItemDto, item);

        List<String> imageUrl = new ArrayList<>();

        for (MultipartFile img : updateFoodItemDto.getImages()) {
            imageUrl.add(cloudinaryService.upload(img, MagicValues.FOOD_FOLDER));
        }

        item.setImagesUrls(imageUrl);

        return foodItemMapper.toResponse(foodItemRepository.save(item));
    }

}
