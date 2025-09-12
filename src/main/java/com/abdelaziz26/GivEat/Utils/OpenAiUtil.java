package com.abdelaziz26.GivEat.Utils;

import com.abdelaziz26.GivEat.Core.Entities.FoodItem;
import com.abdelaziz26.GivEat.Core.Entities.FoodRequest;
import com.abdelaziz26.GivEat.Core.Enums.FoodItemStatus;
import com.abdelaziz26.GivEat.Core.Repositories.FoodItemRepository;
import com.abdelaziz26.GivEat.Core.Repositories.FoodRequestRepository;
import com.abdelaziz26.GivEat.DTOs.FoodItem.ReadFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.Matching.MatchingResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiUtil {

    private final ChatClient chatClient;
    private final FoodItemRepository foodItemRepository;
    private final FoodRequestRepository foodRequestRepository;
    private final Logger logger = LoggerFactory.getLogger(OpenAiUtil.class);

    public List<MatchingResponse> getMatchedItems(Long requestId){

        return chatClient.prompt()
                .user(this.createPrompt(requestId)).call()
                .entity(new ParameterizedTypeReference<>() {});
    }

    private String createPrompt(Long requestId)
    {
        FoodRequest request = foodRequestRepository.findById(requestId).orElseThrow(() ->
                new EntityNotFoundException("No such request found"));

        List<FoodItem> items = foodItemRepository.findAllByFoodItemStatus(FoodItemStatus.AVAILABLE);

        StringBuilder itemsAsString = new StringBuilder();

        itemsAsString.append("foodItems: [ \n");
        items.forEach(i -> {
            if(i != null) itemsAsString.append(i + "\n");
        });
        itemsAsString.append("] \n");

        logger.info(itemsAsString.toString());

        return """
            You are a food-matching assistant.
            Your task is to match available restaurant food items with the given charity request.
        
            Charity request:
            %s
        
            Available food items:
            %s
        
            For each suggested match, include:
              - requestId
              - itemId
              - matchingScore (between 0.0 and 1.0, where 1.0 = perfect match)
        
            Respond ONLY with JSON in the following format (no extra text):
        
            [
              {
                "requestId": %d,
                "itemId": <foodItemId>,
                "matchingScore": <decimal between 0.0 and 1.0>
              }
            ]
            """.formatted(request, itemsAsString, requestId);
    }
}
