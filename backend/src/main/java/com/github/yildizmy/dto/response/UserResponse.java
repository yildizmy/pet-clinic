package com.github.yildizmy.dto.response;

import lombok.Data;

/**
 * Data Transfer Object for User response
 */
@Data
public class UserResponse {

    private Long id;
    private String username;
    private String fullName;
}
