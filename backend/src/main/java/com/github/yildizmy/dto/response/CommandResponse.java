package com.github.yildizmy.dto.response;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * Data Transfer Object used for returning id value of the saved entity
 */
@Value
@RequiredArgsConstructor
@Builder
public record CommandResponse(Long id) {
}
