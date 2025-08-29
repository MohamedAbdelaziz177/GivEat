package com.abdelaziz26.GivEat.Controllers;

import com.abdelaziz26.GivEat.Core.ApiResponse;
import com.abdelaziz26.GivEat.Core.Interfaces.CharityService;
import com.abdelaziz26.GivEat.DTOs.Charity.CreateCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.ReadCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.UpdateCharityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Charity", description = "Endpoints for managing charities")
public class CharityController {

    private final CharityService charityService;

    @Operation(
            summary = "Get charity by ID",
            description = "Fetch a single charity using its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadCharityDto>> getById(@PathVariable Long id) {
        ReadCharityDto readCharityDto = charityService.getById(id);
        ApiResponse<ReadCharityDto> apiResponse = ApiResponse.createSuccessResponse(readCharityDto);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Get all charities",
            description = "Fetch all charities"
    )
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ReadCharityDto>>> getAll() {
        List<ReadCharityDto> readCharityDtoList = charityService.getAll();
        ApiResponse<List<ReadCharityDto>> apiResponse = ApiResponse.createSuccessResponse(readCharityDtoList);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Create a charity",
            description = "Create a new charity with logo, name, description, etc."
    )
    @PreAuthorize("hasRole('CHARITY')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ReadCharityDto>> create(@ModelAttribute CreateCharityDto createCharityDto) throws IOException {

        ReadCharityDto readCharityDto = charityService.create(createCharityDto);
        ApiResponse<ReadCharityDto> apiResponse = ApiResponse.createSuccessResponse(readCharityDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(
            summary = "Update a charity",
            description = "Update an existing charity with new information"
    )
    @PreAuthorize("hasRole('CHARITY')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ReadCharityDto>> update(@PathVariable Long id, @RequestBody UpdateCharityDto updateCharityDto) throws IOException {
        ReadCharityDto readCharityDto = charityService.update(id, updateCharityDto);
        ApiResponse<ReadCharityDto> apiResponse = ApiResponse.createSuccessResponse(readCharityDto);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(
            summary = "Delete a charity",
            description = "Delete a charity by its ID"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        charityService.delete(id);
        ApiResponse<String> apiResponse = ApiResponse.createSuccessResponse("Charity deleted successfully");
        return ResponseEntity.ok(apiResponse);
    }
}
