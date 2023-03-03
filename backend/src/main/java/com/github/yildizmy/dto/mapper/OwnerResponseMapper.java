package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.response.OwnerResponse;
import com.github.yildizmy.model.Owner;
import org.mapstruct.Mapper;

/**
 * Mapper used for mapping OwnerResponse fields
 */
@Mapper(componentModel = "spring")
public interface OwnerResponseMapper {

    Owner toEntity(OwnerResponse dto);

    OwnerResponse toDto(Owner entity);
}
