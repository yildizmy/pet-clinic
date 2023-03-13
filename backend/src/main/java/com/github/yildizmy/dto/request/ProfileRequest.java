package com.github.yildizmy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for Profile request
 */
@Data
public class ProfileRequest {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastName;

    private Set<String> roles;
}
