package com.github.yildizmy.dto.mapper;

import com.github.yildizmy.dto.request.UserRequest;
import com.github.yildizmy.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper used for mapping UserRequest fields
 */
@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    @Mapping(target = "firstName", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getFirstName()))")
    @Mapping(target = "lastName", expression = "java(org.apache.commons.text.WordUtils.capitalizeFully(dto.getLastName()))")
    @Mapping(target = "username", expression = "java(dto.getUsername().trim().toLowerCase())")
    User toEntity(UserRequest dto);

    UserRequest toDto(User entity);
}
