package com.abdelaziz26.GivEat.Services;

import com.abdelaziz26.GivEat.Core.Entities.Charity;
import com.abdelaziz26.GivEat.Core.Entities.FoodRequest;
import com.abdelaziz26.GivEat.Core.Entities.User;
import com.abdelaziz26.GivEat.Core.Interfaces.FoodRequestService;
import com.abdelaziz26.GivEat.Core.Repositories.CharityRepository;
import com.abdelaziz26.GivEat.Core.Repositories.FoodRequestRepository;
import com.abdelaziz26.GivEat.Core.Repositories.RestaurantRepository;
import com.abdelaziz26.GivEat.Core.Repositories.UserRepo;
import com.abdelaziz26.GivEat.DTOs.Request.CreateRequestDto;
import com.abdelaziz26.GivEat.DTOs.Request.ReadRequestDto;
import com.abdelaziz26.GivEat.DTOs.Request.UpdateRequestDto;
import com.abdelaziz26.GivEat.Mappers.FoodRequestMapper;
import com.abdelaziz26.GivEat.Utils.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodRequestServiceImpl implements FoodRequestService {

    private final FoodRequestRepository foodRequestRepository;
    private final UserRepo userRepo;
    private final CharityRepository charityRepo;
    private final FoodRequestMapper foodRequestMapper;
    private final RestaurantRepository restaurantRepository;
    private final AuthUtil authUtil;

    @Override
    public ReadRequestDto getById(Long id) {
        return foodRequestMapper.toResponse(foodRequestRepository.findById(id).orElseThrow());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<ReadRequestDto> getAll() {
        List<FoodRequest> foodRequestList = foodRequestRepository.findAll();
        return foodRequestList.stream().map(foodRequestMapper::toResponse).toList();
    }

    @PreAuthorize("hasRole('Charity')")
    @Override
    public List<ReadRequestDto> getByCharity() {

        Long userId = authUtil.getUserId();

        User user = userRepo.findById(userId).orElseThrow(() ->
                new AuthenticationServiceException("User not found"));

        Charity charity = charityRepo.findByUser_Id(user.getId()).orElseThrow(() ->
                new AuthorizationServiceException("Charity not found"));

        List<FoodRequest> foodRequestList = foodRequestRepository.findByCharity(charity);

        return foodRequestList.stream().map(foodRequestMapper::toResponse).toList();
    }

    //public List<ReadRequestDto> getByRestaurant(Long userId) {
//
    //    User user = userRepo.findById(userId).orElseThrow(() ->
    //            new AuthenticationServiceException("User not found"));
//
    //    Restaurant restaurant = restaurantRepository.findByUser_Id(user.getId()).orElseThrow(() ->
    //            new AuthorizationServiceException("Charity not found"));
    //
    //    List<FoodRequest> foodRequestList = foodRequestRepository.findByRestaurant(restaurant);
    //
    //}

    @PreAuthorize("hasRole('Charity')")
    @Override
    public ReadRequestDto create(CreateRequestDto createRequestDto) {

        Long userId = authUtil.getUserId();

        FoodRequest foodRequest = foodRequestMapper.toEntity(createRequestDto);

        User user = userRepo.findById(userId).orElseThrow(() ->
                new AuthenticationServiceException("User not found"));

        Charity charity = charityRepo.findByUser_Id(user.getId()).orElseThrow(() ->
                new AuthorizationServiceException("Charity not found"));

        foodRequest.setCharity(charity);

        return foodRequestMapper.toResponse(foodRequestRepository.save(foodRequest));
    }

    @PreAuthorize("hasRole('Charity')")
    @Override
    public ReadRequestDto update(Long id, UpdateRequestDto updateRequestDto) {

        Long userId = authUtil.getUserId();

        FoodRequest foodRequest = foodRequestRepository.findById(id).orElseThrow(() ->
                new AuthenticationServiceException("Food Request not found"));

        User user = userRepo.findById(userId).orElseThrow(() ->
                new AuthenticationServiceException("User not found"));

        Charity charity = charityRepo.findByUser_Id(user.getId()).orElseThrow(() ->
                new AuthorizationServiceException("Charity not found"));

        if (!foodRequest.getCharity().getId().equals(charity.getId())) {
            throw new AuthorizationServiceException("You are not authorized to update this food request");
        }

        foodRequestMapper.toEntity(updateRequestDto, foodRequest);
        foodRequestRepository.save(foodRequest);
        return foodRequestMapper.toResponse(foodRequest);
    }
}
