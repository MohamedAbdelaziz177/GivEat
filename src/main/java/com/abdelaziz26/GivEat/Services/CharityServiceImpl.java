package com.abdelaziz26.GivEat.Services;

import com.abdelaziz26.GivEat.Core.Entities.Charity;
import com.abdelaziz26.GivEat.Core.Interfaces.CharityService;
import com.abdelaziz26.GivEat.Core.Interfaces.Mapper;
import com.abdelaziz26.GivEat.Core.MagicValues;
import com.abdelaziz26.GivEat.Core.Repositories.CharityRepository;
import com.abdelaziz26.GivEat.Core.Repositories.UserRepo;
import com.abdelaziz26.GivEat.DTOs.Charity.CreateCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.ReadCharityDto;
import com.abdelaziz26.GivEat.DTOs.Charity.UpdateCharityDto;
import com.abdelaziz26.GivEat.Mappers.CharityMapper;
import com.abdelaziz26.GivEat.Utils.AuthUtil;
import com.abdelaziz26.GivEat.Utils.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.support.Repositories;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CharityServiceImpl implements CharityService {

    private final CharityRepository charityRepository;
    private final CharityMapper charityMapper;
    private final UserRepo userRepo;
    private final CloudinaryService cloudinaryService;
    private final AuthUtil authUtil;

    public ReadCharityDto getById(Long id) {
        return charityMapper.toResponse(charityRepository.findById(id).orElseThrow());
    }

    public List<ReadCharityDto> getAll() {
        List<Charity> charityList = charityRepository.findAll();
        return charityList.stream().map(charityMapper::toResponse).toList();
    }

    @PreAuthorize("hasRole('CHARITY')")
    public ReadCharityDto create(CreateCharityDto createCharityDto) throws IOException {

        Long userId = authUtil.getUserId();

        String imageUrl = cloudinaryService
                .upload(createCharityDto.getLogo(), MagicValues.CHARITY_FOLDER);

        Charity charity = charityMapper.toEntity(createCharityDto, imageUrl);

        charity.setUser(userRepo.findById(userId).orElseThrow(() ->
                new AuthenticationServiceException("User not found with id: " + userId)));

        charity.setImageUrl(imageUrl);

        charityRepository.save(charity);

        return charityMapper.toResponse(charity);
    }

    @PreAuthorize("hasRole('CHARITY')")
    public ReadCharityDto update(Long id, UpdateCharityDto updateCharityDto) {

        Long userId = authUtil.getUserId();

        Charity charity = charityRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Charity not found with id: " + id));

        if (!charity.getUser().getId().equals(userId)) {
            throw new AuthorizationServiceException("User not authorized to update this charity");
        }

        Charity UpdateCh = charityMapper.toEntity(updateCharityDto, charity);
        charityRepository.save(UpdateCh);

        return charityMapper.toResponse(UpdateCh);
    }

    @Override
    public void delete(Long id) {

         Charity charity = charityRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Charity not found with id: " + id));
         charityRepository.delete(charity);
    }
}
