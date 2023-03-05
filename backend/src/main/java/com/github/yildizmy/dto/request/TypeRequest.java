package com.github.yildizmy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Data Transfer Object for Type request
 */
@Data
public class TypeRequest {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 50)
    private String description;
}
