package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.response.TypeResponse;
import com.github.yildizmy.model.Type;
import org.mapstruct.Mapper;

/**
 * Mapper used for mapping TypeResponse fields
 */
@Mapper(componentModel = "spring")
public interface TypeResponseMapper {

    Type toEntity(TypeResponse dto);

    TypeResponse toDto(Type entity);
}
