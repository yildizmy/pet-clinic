package com.github.yildizmy.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

import static com.github.yildizmy.common.Constants.DATE_FORMAT;

/**
 * Data Transfer Object for Pet request
 */
@Data
public class PetRequest {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @JsonFormat(pattern = DATE_FORMAT)
    private LocalDate birthDate;

    @NotNull()
    private Long typeId;

    @NotNull()
    private Long userId;
}