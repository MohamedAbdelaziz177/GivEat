package com.abdelaziz26.GivEat.Controllers;

import com.abdelaziz26.GivEat.Core.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matching")
public class MatchingController {

    @GetMapping("/charity")
    public ResponseEntity<ApiResponse<?>> getMatchingByReqId(@RequestParam Long requestId)
    {
        ApiResponse<?> response = new ApiResponse<>();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/charity")
    public ResponseEntity<ApiResponse<?>> requestMatch(@RequestParam Long itemId)
    {
        ApiResponse<?> response = new ApiResponse<>();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/restaurant/accept")
    public ResponseEntity<ApiResponse<?>> acceptMatch(@RequestParam Long requestId)
    {
        ApiResponse<?> response = new ApiResponse<>();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/restaurant/reject")
    public ResponseEntity<ApiResponse<?>> rejectMatch(@RequestParam Long requestId)
    {
        ApiResponse<?> response = new ApiResponse<>();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/restaurant")
    public ResponseEntity<ApiResponse<?>> getMatchingByResId(@RequestParam Long restaurantId)
    {
        ApiResponse<?> response = new ApiResponse<>();
        return ResponseEntity.ok(response);
    }
}
