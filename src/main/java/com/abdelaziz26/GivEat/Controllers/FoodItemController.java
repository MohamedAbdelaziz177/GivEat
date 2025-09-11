package com.abdelaziz26.GivEat.Controllers;

import com.abdelaziz26.GivEat.Core.ApiResponse;
import com.abdelaziz26.GivEat.Core.Interfaces.FoodItemService;
import com.abdelaziz26.GivEat.DTOs.FoodItem.CreateFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.FoodItem.ReadFoodItemDto;
import com.abdelaziz26.GivEat.DTOs.FoodItem.UpdateFoodItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/food-item")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    public ResponseEntity<ApiResponse<ReadFoodItemDto>> getById(Long id) {
        ReadFoodItemDto readFoodItemDto = foodItemService.getById(id);
        ApiResponse<ReadFoodItemDto> apiResponse = ApiResponse.createSuccessResponse(readFoodItemDto);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ReadFoodItemDto>>> getAll() {
        List<ReadFoodItemDto> readFoodItemDtoList = foodItemService.getAll();
        ApiResponse<List<ReadFoodItemDto>> apiResponse = ApiResponse.createSuccessResponse(readFoodItemDtoList);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<ApiResponse<List<ReadFoodItemDto>>> getAllByRestaurantId(@PathVariable Long restaurantId) {
        List<ReadFoodItemDto> readFoodItemDtoList = foodItemService.getAllByRestaurantId(restaurantId);
        ApiResponse<List<ReadFoodItemDto>> apiResponse = ApiResponse.createSuccessResponse(readFoodItemDtoList);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<List<ReadFoodItemDto>>> getMyFoodItems() {
        List<ReadFoodItemDto> readFoodItemDtoList = foodItemService.getMyFoodItems();
        ApiResponse<List<ReadFoodItemDto>> apiResponse = ApiResponse.createSuccessResponse(readFoodItemDtoList);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ReadFoodItemDto>> create(@ModelAttribute CreateFoodItemDto createFoodItemDto) throws IOException {
        ReadFoodItemDto readFoodItemDto = foodItemService.create(createFoodItemDto);
        ApiResponse<ReadFoodItemDto> apiResponse = ApiResponse.createSuccessResponse(readFoodItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadFoodItemDto>> update(@PathVariable Long id, @ModelAttribute UpdateFoodItemDto updateFoodItemDto) throws IOException {
        ReadFoodItemDto readFoodItemDto = foodItemService.update(id, updateFoodItemDto);
        ApiResponse<ReadFoodItemDto> apiResponse = ApiResponse.createSuccessResponse(readFoodItemDto);
        return ResponseEntity.ok(apiResponse);
    }

    //@DeleteMapping("/{id}")
    //public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
    //    foodItemService.delete(id);
    //    ApiResponse<Void> apiResponse = ApiResponse.createSuccessResponse(null);
    //    return ResponseEntity.ok(apiResponse);
    //}


}
