package com.abdelaziz26.GivEat.Controllers;

import com.abdelaziz26.GivEat.Core.ApiResponse;
import com.abdelaziz26.GivEat.Core.Interfaces.MatchingService;
import com.abdelaziz26.GivEat.DTOs.Matching.FoodItemMatchedDto;
import com.abdelaziz26.GivEat.DTOs.Matching.MatchingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matching")
@RequiredArgsConstructor
public class MatchingController {

    private final MatchingService matchingService;

    @GetMapping("/charity")
    public ResponseEntity<ApiResponse<List<FoodItemMatchedDto>>> getMatchingByReqId(@RequestParam Long requestId)
    {
        return ResponseEntity.ok( ApiResponse
                .createSuccessResponse(matchingService
                        .getMatchedItems(requestId)
                )
        );
    }

    @PutMapping("/charity/request")
    public ResponseEntity<ApiResponse<String>> requestMatch(@RequestParam Long matchId)
    {
        matchingService.requestMatchedItem(matchId);
        return ResponseEntity.ok(ApiResponse.createSuccessResponse("Item requested successfully"));
    }

    @PutMapping("/restaurant/accept")
    public ResponseEntity<ApiResponse<String>> acceptMatch(@RequestParam Long matchId)
    {
        matchingService.acceptMatchRequest(matchId);
        return ResponseEntity.ok(ApiResponse.createSuccessResponse("Request accepted successfully"));
    }

    @PutMapping("/restaurant/reject")
    public ResponseEntity<ApiResponse<String>> rejectMatch(@RequestParam Long matchId)
    {
        matchingService.acceptMatchRequest(matchId);
        return ResponseEntity.ok(ApiResponse.createSuccessResponse("Request rejected successfully"));
    }

    @GetMapping("/restaurant/my-matches")
    public ResponseEntity<ApiResponse<List<FoodItemMatchedDto>>> getMatchingByResId()
    {
        return ResponseEntity.ok(ApiResponse
                .createSuccessResponse(matchingService
                        .getMatchesByRestaurant()
                )
        );
    }

    @GetMapping("/charity/my-matches")
    public ResponseEntity<ApiResponse<List<FoodItemMatchedDto>>> getMatchingByCharityId()
    {
        return ResponseEntity.ok(ApiResponse
                .createSuccessResponse(matchingService
                        .getMatchesByCharity()
                )
        );
    }
}
