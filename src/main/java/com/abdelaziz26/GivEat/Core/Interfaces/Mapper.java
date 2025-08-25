package com.abdelaziz26.GivEat.Core.Interfaces;

import com.abdelaziz26.GivEat.DTOs.Dto;

import java.util.List;

public interface Mapper <
        Entity,
        Response extends Dto,
        Create extends Dto,
        Update extends Dto
        >
{

    default Entity toEntity(Create create){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default Entity toEntity(Create create, Object... extras){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default Entity toEntity(Create create, List<String> imagesUrls){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    Response toResponse(Entity entity);

    Entity toEntity(Update update, Entity entity);

}
