package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.TypeRequest;
import com.github.yildizmy.model.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper used for mapping TypeRequest fields
 */
@Mapper(componentModel = "spring")
public interface TypeRequestMapper {

    @Mapping(target = "name", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getName()))")
    Type toEntity(TypeRequest dto);

    TypeRequest toDto(Type entity);
}
