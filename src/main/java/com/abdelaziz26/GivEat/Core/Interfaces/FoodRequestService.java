package com.abdelaziz26.GivEat.Core.Interfaces;

import com.abdelaziz26.GivEat.DTOs.Request.CreateRequestDto;
import com.abdelaziz26.GivEat.DTOs.Request.ReadRequestDto;
import com.abdelaziz26.GivEat.DTOs.Request.UpdateRequestDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface FoodRequestService  {
    ReadRequestDto getById(Long id);

    @PreAuthorize("hasRole('ADMIN')")
    List<ReadRequestDto> getAll();

    @PreAuthorize("hasRole('Charity')")
    List<ReadRequestDto> getByCharity();

    @PreAuthorize("hasRole('Charity')")
    ReadRequestDto create(CreateRequestDto createRequestDto);

    @PreAuthorize("hasRole('Charity')")
    ReadRequestDto update(Long id, UpdateRequestDto updateRequestDto);
}
