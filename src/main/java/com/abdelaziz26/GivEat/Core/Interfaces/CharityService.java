package com.abdelaziz26.GivEat.Core.Interfaces;

import com.abdelaziz26.GivEat.Core.Entities.Charity;
import com.abdelaziz26.GivEat.DTOs.Charity.CreateCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.ReadCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.UpdateCharityDto;

import java.io.IOException;
import java.util.List;

public interface CharityService {

    ReadCharityDto getById(Long id);
    List<ReadCharityDto> getAll();
    ReadCharityDto create(CreateCharityDto createCharityDto) throws IOException;
    ReadCharityDto update(Long id, UpdateCharityDto updateCharityDto) throws IOException;
    void delete(Long id);
}
