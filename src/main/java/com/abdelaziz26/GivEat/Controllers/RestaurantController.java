package com.abdelaziz26.GivEat.Controllers;

import com.abdelaziz26.GivEat.Core.ApiResponse;
import com.abdelaziz26.GivEat.Core.Interfaces.RestaurantService;
import com.abdelaziz26.GivEat.DTOs.Restaurant.CreateRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.ReadRestaurantDto;
import com.abdelaziz26.GivEat.DTOs.Restaurant.UpdateRestaurantDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@Tag(name = "Restaurant", description = "Endpoints for managing restaurants")

public class RestaurantController {

    private final RestaurantService restaurantService;

    @Operation(
            summary = "Get restaurant by ID",
            description = "Fetch a single restaurant using its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadRestaurantDto>> getById(@PathVariable Long id) {
        ReadRestaurantDto readRestaurantDto = restaurantService.getById(id);
        ApiResponse<ReadRestaurantDto> apiResponse = ApiResponse.createSuccessResponse(readRestaurantDto);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Get all restaurants",
            description = "Fetch all restaurants"
    )
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ReadRestaurantDto>>> getAll() {
        List<ReadRestaurantDto> readRestaurantDtoList = restaurantService.getAll();
        ApiResponse<List<ReadRestaurantDto>> apiResponse = ApiResponse.createSuccessResponse(readRestaurantDtoList);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Create a restaurant",
            description = "Create a new restaurant with logo, name, description, food types, etc."
    )
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ReadRestaurantDto>> create(@ModelAttribute CreateRestaurantDto createRestaurantDto) throws IOException {
        ReadRestaurantDto readRestaurantDto = restaurantService.create(createRestaurantDto);
        ApiResponse<ReadRestaurantDto> apiResponse = ApiResponse.createSuccessResponse(readRestaurantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(
            summary = "Update a restaurant",
            description = "Update restaurant details by ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadRestaurantDto>> update(@PathVariable Long id, @RequestBody UpdateRestaurantDto updateRestaurantDto) throws IOException {
        ReadRestaurantDto readRestaurantDto = restaurantService.update(id, updateRestaurantDto);
        ApiResponse<ReadRestaurantDto> apiResponse = ApiResponse.createSuccessResponse(readRestaurantDto);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Delete a restaurant",
            description = "Remove a restaurant by its ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        ApiResponse<Void> apiResponse = ApiResponse.createSuccessResponse(null);
        return ResponseEntity.ok(apiResponse);
    }
}
