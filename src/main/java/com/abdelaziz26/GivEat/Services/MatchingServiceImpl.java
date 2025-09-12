package com.abdelaziz26.GivEat.Services;

import com.abdelaziz26.GivEat.Core.Entities.FoodItem;
import com.abdelaziz26.GivEat.Core.Entities.FoodRequest;
import com.abdelaziz26.GivEat.Core.Entities.Matching;
import com.abdelaziz26.GivEat.Core.Interfaces.MatchingService;
import com.abdelaziz26.GivEat.Core.Repositories.FoodItemRepository;
import com.abdelaziz26.GivEat.Core.Repositories.FoodRequestRepository;
import com.abdelaziz26.GivEat.Core.Repositories.MatchingRepository;
import com.abdelaziz26.GivEat.DTOs.FoodItem.ReadFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.Matching.FoodItemMatchedDto;
import com.abdelaziz26.GivEat.DTOs.Matching.MatchingResponse;
import com.abdelaziz26.GivEat.Mappers.FoodItemMapper;
import com.abdelaziz26.GivEat.Mappers.FoodRequestMapper;
import com.abdelaziz26.GivEat.Utils.OpenAiUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    private final MatchingRepository matchingRepository;
    private final FoodItemRepository foodItemRepository;
    private final FoodRequestRepository foodRequestRepository;

    private final FoodItemMapper foodItemMapper;

    private final OpenAiUtil openAiUtil;

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
                            .readFoodItemDto(foodItemMapper.toResponse(item))
                            .score(matchingResponse.getScore())
                            .build());

        }

        return itemMatchedResponses;
    }

    @Override
    public void requestMatchedItem(Long itemId) {

    }

    @Override
    public void acceptMatchRequest(Long requestId) {

        this.rejectAllTheOthers(requestId);
    }

    @Override
    public void rejectMatchRequest(Long requestId) {

    }

    private void rejectAllTheOthers(Long requestId) {

    }
}
