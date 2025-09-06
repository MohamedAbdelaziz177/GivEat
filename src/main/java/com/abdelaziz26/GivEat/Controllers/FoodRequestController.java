package com.abdelaziz26.GivEat.Controllers;

import com.abdelaziz26.GivEat.Core.ApiResponse;
import com.abdelaziz26.GivEat.Core.Interfaces.FoodRequestService;
import com.abdelaziz26.GivEat.DTOs.Request.CreateRequestDto;
import com.abdelaziz26.GivEat.DTOs.Request.ReadRequestDto;
import com.abdelaziz26.GivEat.DTOs.Request.UpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food-request")
@RequiredArgsConstructor
public class FoodRequestController {

    private final FoodRequestService foodRequestService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadRequestDto>> getById(@PathVariable Long id) {
        ReadRequestDto readRequestDto = foodRequestService.getById(id);
        ApiResponse<ReadRequestDto> apiResponse = ApiResponse.createSuccessResponse(readRequestDto);
        return ResponseEntity.ok(apiResponse);
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ReadRequestDto>>> getAll() {
        List<ReadRequestDto> readRequestDtoList = foodRequestService.getAll();
        ApiResponse<List<ReadRequestDto>> apiResponse = ApiResponse.createSuccessResponse(readRequestDtoList);
        return ResponseEntity.ok(apiResponse);
    }



    @GetMapping("/mine")
    public ResponseEntity<ApiResponse<List<ReadRequestDto>>> getByCharity() {
        List<ReadRequestDto> readRequestDtoList = foodRequestService.getByCharity();
        ApiResponse<List<ReadRequestDto>> apiResponse = ApiResponse.createSuccessResponse(readRequestDtoList);
        return ResponseEntity.ok(apiResponse);
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ReadRequestDto>> create(@RequestBody CreateRequestDto createRequestDto) {
        ReadRequestDto readRequestDto = foodRequestService.create(createRequestDto);
        ApiResponse<ReadRequestDto> apiResponse = ApiResponse.createSuccessResponse(readRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ReadRequestDto>> update(@PathVariable Long id, @RequestBody UpdateRequestDto updateRequestDto) {
        ReadRequestDto readRequestDto = foodRequestService.update(id, updateRequestDto);
        ApiResponse<ReadRequestDto> apiResponse = ApiResponse.createSuccessResponse(readRequestDto);
        return ResponseEntity.ok(apiResponse);
    }

}
