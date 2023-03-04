package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.PetRequest;
import com.github.yildizmy.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper used for mapping PetRequest fields
 */
@Mapper(componentModel = "spring")
public interface PetRequestMapper {

    @Mapping(target = "name", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getName()))")
    Pet toEntity(PetRequest dto);

    PetRequest toDto(Pet entity);
}
