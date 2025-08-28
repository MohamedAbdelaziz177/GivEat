package com.abdelaziz26.GivEat.Controllers;

import com.abdelaziz26.GivEat.Core.ApiResponse;
import com.abdelaziz26.GivEat.Core.Interfaces.RestaurantService;
import com.abdelaziz26.GivEat.DTOs.Restaurant.CreateRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.ReadRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.UpdateRestaurantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadRestaurantDto>> getById(@PathVariable Long id) {
        ReadRestaurantDto readRestaurantDto = restaurantService.getById(id);
        ApiResponse<ReadRestaurantDto> apiResponse = ApiResponse.createSuccessResponse(readRestaurantDto);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ReadRestaurantDto>>> getAll() {
        List<ReadRestaurantDto> readRestaurantDtoList = restaurantService.getAll();
        ApiResponse<List<ReadRestaurantDto>> apiResponse = ApiResponse.createSuccessResponse(readRestaurantDtoList);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReadRestaurantDto>> create(@RequestBody CreateRestaurantDto createRestaurantDto) throws IOException {
        ReadRestaurantDto readRestaurantDto = restaurantService.create(createRestaurantDto);
        ApiResponse<ReadRestaurantDto> apiResponse = ApiResponse.createSuccessResponse(readRestaurantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadRestaurantDto>> update(@PathVariable Long id, @RequestBody UpdateRestaurantDto updateRestaurantDto) throws IOException {
        ReadRestaurantDto readRestaurantDto = restaurantService.update(id, updateRestaurantDto);
        ApiResponse<ReadRestaurantDto> apiResponse = ApiResponse.createSuccessResponse(readRestaurantDto);
        return ResponseEntity.ok(apiResponse);
    }
}
