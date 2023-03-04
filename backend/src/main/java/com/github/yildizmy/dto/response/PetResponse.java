package com.github.yildizmy.dto.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object for Pet response
 */
@Data
public class PetResponse {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private TypeResponse type;
    private UserResponse user;
}
