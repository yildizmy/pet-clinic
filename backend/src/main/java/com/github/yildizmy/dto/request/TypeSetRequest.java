package com.github.yildizmy.dto.request;

import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for retrieving count of selected types of registered pets
 */
@Data
public class TypeSetRequest {

    private Set<Long> ids;
}
