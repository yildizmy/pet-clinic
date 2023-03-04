package com.github.yildizmy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for User request
 */
@Data
public class UserRequest {

    private Long id;

    @Size(min = 3, max = 50)
    @NotBlank
    private String firstName;

    @Size(min = 3, max = 50)
    @NotBlank
    private String lastName;

    @Size(min = 3, max = 50)
    @NotBlank
    private String username;

    @Size(min = 3, max = 120)
    @NotBlank
    private String password;
}
