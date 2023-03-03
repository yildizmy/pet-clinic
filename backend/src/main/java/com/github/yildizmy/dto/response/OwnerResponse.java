package com.github.yildizmy.dto.response;

import lombok.Data;

/**
 * Data Transfer Object for Owner response
 */
@Data
public class OwnerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
