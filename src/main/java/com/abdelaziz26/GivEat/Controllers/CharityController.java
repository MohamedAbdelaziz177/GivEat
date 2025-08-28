package com.abdelaziz26.GivEat.Controllers;

import com.abdelaziz26.GivEat.Core.ApiResponse;
import com.abdelaziz26.GivEat.Core.Interfaces.CharityService;
import com.abdelaziz26.GivEat.DTOs.Charity.CreateCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.ReadCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.UpdateCharityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/charity")
@RequiredArgsConstructor
public class CharityController {

    private final CharityService charityService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadCharityDto>> getById(@PathVariable Long id) {
        ReadCharityDto readCharityDto = charityService.getById(id);
        ApiResponse<ReadCharityDto> apiResponse = ApiResponse.createSuccessResponse(readCharityDto);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ReadCharityDto>>> getAll() {
        List<ReadCharityDto> readCharityDtoList = charityService.getAll();
        ApiResponse<List<ReadCharityDto>> apiResponse = ApiResponse.createSuccessResponse(readCharityDtoList);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasRole('CHARITY')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ReadCharityDto>> create(@RequestBody CreateCharityDto createCharityDto) throws IOException {

        ReadCharityDto readCharityDto = charityService.create(createCharityDto);
        ApiResponse<ReadCharityDto> apiResponse = ApiResponse.createSuccessResponse(readCharityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ReadCharityDto>> update(@PathVariable Long id, @RequestBody UpdateCharityDto updateCharityDto) throws IOException {
        ReadCharityDto readCharityDto = charityService.update(id, updateCharityDto);
        ApiResponse<ReadCharityDto> apiResponse = ApiResponse.createSuccessResponse(readCharityDto);
        return ResponseEntity.ok(apiResponse);
    }


}
