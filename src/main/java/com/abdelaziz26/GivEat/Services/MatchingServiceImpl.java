package com.abdelaziz26.GivEat.Services;

import com.abdelaziz26.GivEat.Core.Entities.*;
import com.abdelaziz26.GivEat.Core.Enums.FoodItemStatus;
import com.abdelaziz26.GivEat.Core.Enums.FoodRequestStatus;
import com.abdelaziz26.GivEat.Core.Enums.MatchingStatus;
import com.abdelaziz26.GivEat.Core.Interfaces.MatchingService;
import com.abdelaziz26.GivEat.Core.Repositories.*;
import com.abdelaziz26.GivEat.DTOs.FoodItem.ReadFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.Matching.FoodItemMatchedDto;
import com.abdelaziz26.GivEat.DTOs.Matching.MatchingResponse;
import com.abdelaziz26.GivEat.Mappers.FoodItemMapper;
import com.abdelaziz26.GivEat.Mappers.FoodRequestMapper;
import com.abdelaziz26.GivEat.Mappers.MatchingMapper;
import com.abdelaziz26.GivEat.Utils.AuthUtil;
import com.abdelaziz26.GivEat.Utils.OpenAiUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MatchingServiceImpl implements MatchingService {

    private final OpenAiUtil openAiUtil;
    private final FoodItemMapper foodItemMapper;
    private final AuthUtil authUtil;
    private final MatchingRepository matchingRepository;
    private final FoodItemRepository foodItemRepository;
    private final FoodRequestRepository foodRequestRepository;
    private final CharityRepository charityRepository;
    private final RestaurantRepository restaurantRepository;
    private final MatchingMapper matchingMapper;

    @Override
    public List<FoodItemMatchedDto> getMatchesByCharity() {

        Long userId = authUtil.getUserId();

        Charity charity = charityRepository.findByUser_Id(userId).orElseThrow(() ->
                new EntityNotFoundException("Charity not found with id: " + userId));

        List<Matching> matches = matchingRepository.findAllByCharityId(charity.getId(), MatchingStatus.ACCEPTED);
        return matches.stream().map(matchingMapper::mapToMatchedItemDto).toList();
    }

    @Override
    public List<FoodItemMatchedDto> getMatchesByRestaurant() {

        Long userId = authUtil.getUserId();

        Restaurant restaurant = restaurantRepository.findByUser_Id(userId).orElseThrow(() ->
                new EntityNotFoundException("Restaurant not found with id: " + userId));

        List<Matching> matches = matchingRepository.findAllByRestaurantId(restaurant.getId(), MatchingStatus.REQUESTED);
        return matches.stream().map(matchingMapper::mapToMatchedItemDto).toList();
    }

    @Override
    public List<FoodItemMatchedDto> getMatchedItems(Long requestId) {

        List<MatchingResponse> matchingResponses = openAiUtil.getMatchedItems(requestId);
        List<FoodItemMatchedDto> itemMatchedResponses = new ArrayList<>();

        for(MatchingResponse matchingResponse : matchingResponses) {

            FoodItem item = foodItemRepository.findById(matchingResponse.getItemId()).orElseThrow();
            FoodRequest request = foodRequestRepository.findById(matchingResponse.getRequestId()).orElseThrow();

            Matching matching = Matching.builder()
                    .foodItem(item)
                    .foodRequest(request)
                    .matchingScore(matchingResponse.getScore())
                    .matchingStatus(MatchingStatus.IGNORED)
                    .build();

            matchingRepository.save(matching);

            itemMatchedResponses.add(FoodItemMatchedDto.builder()
                            .matchId(matching.getId())
                            .readFoodItemDto(foodItemMapper.toResponse(item))
                            .score(matchingResponse.getScore())
                            .build());

        }

        return itemMatchedResponses;
    }

    @Override
    public void requestMatchedItem(Long matchingId) {

        Matching matching = matchingRepository.findById(matchingId).orElseThrow();
        FoodItem item = matching.getFoodItem();
        FoodRequest request = matching.getFoodRequest();

        Long currUserId = authUtil.getUserId();

        if(!currUserId.equals(request.getCharity().getUser().getId()))
            throw new AuthorizationServiceException("you are not authorized to do this action");

        request.setStatus(FoodRequestStatus.DELIVERED);
        item.setFoodItemStatus(FoodItemStatus.REQUESTED);
        matching.setMatchingStatus(MatchingStatus.REQUESTED);

        foodItemRepository.save(item);
        foodRequestRepository.save(request);
        matchingRepository.save(matching);

        // SEND NOTIFICATION TO THE RESTAURANT -> To be implemented
    }

    @Override
    public void acceptMatchRequest(Long matchingId) {

        Matching matching = matchingRepository.findById(matchingId).orElseThrow();
        FoodItem item = matching.getFoodItem();
        FoodRequest request = matching.getFoodRequest();

        Long currUserId = authUtil.getUserId();

        if(!currUserId.equals(item.getRestaurant().getUser().getId()))
            throw new AuthorizationServiceException("you are not authorized to do this action");

        request.setStatus(FoodRequestStatus.ACCEPTED);
        item.setFoodItemStatus(FoodItemStatus.UNAVAILABLE);
        matching.setMatchingStatus(MatchingStatus.ACCEPTED);

        foodItemRepository.save(item);
        foodRequestRepository.save(request);
        matchingRepository.save(matching);

        this.rejectAllTheOthers(matchingId);
    }

    @Override
    public void rejectMatchRequest(Long matchingId) {

        Matching matching = matchingRepository.findById(matchingId).orElseThrow();
        FoodItem item = matching.getFoodItem();
        FoodRequest request = matching.getFoodRequest();

        Long currUserId = authUtil.getUserId();

        if(!currUserId.equals(item.getRestaurant().getUser().getId()))
            throw new AuthorizationServiceException("you are not authorized to do this action");

        request.setStatus(FoodRequestStatus.REJECTED);
        item.setFoodItemStatus(FoodItemStatus.AVAILABLE);
        matching.setMatchingStatus(MatchingStatus.REJECTED);

        foodItemRepository.save(item);
        foodRequestRepository.save(request);
        matchingRepository.save(matching);

    }

    private void rejectAllTheOthers(Long requestId) {
        matchingRepository.rejectOthersWhenAcceptance(requestId);
    }
}
