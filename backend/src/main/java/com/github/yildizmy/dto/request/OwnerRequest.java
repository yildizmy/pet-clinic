package com.github.yildizmy.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for Type request
 */
@Data
public class OwnerRequest {

    private Long id;

    @Size(min = 3, max = 50)
    @NotBlank
    private String firstName;

    @Size(min = 3, max = 50)
    @NotBlank
    private String lastName;

    @Email
    @Size(max = 50)
    private String email;
}
