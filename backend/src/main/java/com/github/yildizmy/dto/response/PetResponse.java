package com.github.yildizmy.dto.response;

import lombok.Data;

/**
 * Data Transfer Object for Pet response
 */
@Data
public class PetResponse {

    private Long id;
    private String name;
    private TypeResponse type;
    private UserResponse user;
}
