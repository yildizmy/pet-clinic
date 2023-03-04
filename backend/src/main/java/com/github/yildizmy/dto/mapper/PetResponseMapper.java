package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.response.PetResponse;
import com.github.yildizmy.model.Pet;
import org.mapstruct.Mapper;

/**
 * Mapper used for mapping PetResponse fields
 */
@Mapper(componentModel = "spring")
public interface PetResponseMapper {

    Pet toEntity(PetResponse dto);

    PetResponse toDto(Pet entity);
}
