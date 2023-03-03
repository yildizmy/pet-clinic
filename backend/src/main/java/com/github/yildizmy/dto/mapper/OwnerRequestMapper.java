package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.OwnerRequest;
import com.github.yildizmy.model.Owner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper used for mapping OwnerRequest fields
 */
@Mapper(componentModel = "spring")
public interface OwnerRequestMapper {

    @Mapping(target = "firstName", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getFirstName()))")
    @Mapping(target = "lastName", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getLastName()))")
    Owner toEntity(OwnerRequest dto);

    OwnerRequest toDto(Owner entity);
}
