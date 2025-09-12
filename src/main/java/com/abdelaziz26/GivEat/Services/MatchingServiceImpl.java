package com.abdelaziz26.GivEat.Services;

import com.abdelaziz26.GivEat.Core.Entities.FoodItem;
import com.abdelaziz26.GivEat.Core.Entities.FoodRequest;
import com.abdelaziz26.GivEat.Core.Entities.Matching;
import com.abdelaziz26.GivEat.Core.Enums.FoodItemStatus;
import com.abdelaziz26.GivEat.Core.Enums.FoodRequestStatus;
import com.abdelaziz26.GivEat.Core.Interfaces.MatchingService;
import com.abdelaziz26.GivEat.Core.Repositories.FoodItemRepository;
import com.abdelaziz26.GivEat.Core.Repositories.FoodRequestRepository;
import com.abdelaziz26.GivEat.Core.Repositories.MatchingRepository;
import com.abdelaziz26.GivEat.DTOs.FoodItem.ReadFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.Matching.FoodItemMatchedDto;
import com.abdelaziz26.GivEat.DTOs.Matching.MatchingResponse;
import com.abdelaziz26.GivEat.Mappers.FoodItemMapper;
import com.abdelaziz26.GivEat.Mappers.FoodRequestMapper;
import com.abdelaziz26.GivEat.Utils.AuthUtil;
import com.abdelaziz26.GivEat.Utils.OpenAiUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AuthorizationServiceException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    private final OpenAiUtil openAiUtil;
    private final FoodItemMapper foodItemMapper;
    private final AuthUtil authUtil;
    private final MatchingRepository matchingRepository;
    private final FoodItemRepository foodItemRepository;
    private final FoodRequestRepository foodRequestRepository;

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

        foodItemRepository.save(item);
        foodRequestRepository.save(request);

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

        foodItemRepository.save(item);
        foodRequestRepository.save(request);

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

        foodItemRepository.save(item);
        foodRequestRepository.save(request);

    }

    private void rejectAllTheOthers(Long requestId) {

    }
}
