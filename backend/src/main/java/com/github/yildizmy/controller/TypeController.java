package com.github.yildizmy.controller;

import com.github.yildizmy.dto.request.TypeRequest;
import com.github.yildizmy.dto.response.ApiResponse;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.TypeResponse;
import com.github.yildizmy.service.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;

import static com.github.yildizmy.common.Constants.SUCCESS;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1/types")
@RequiredArgsConstructor
public class TypeController {

    private final Clock clock;
    private final TypeService typeService;

    /**
     * Fetches a single type by the given id
     *
     * @param id
     * @return TypeResponse
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TypeResponse>> findById(@PathVariable long id) {
        final TypeResponse response = typeService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all types based on the given parameters
     *
     * @param pageable
     * @return List of TypeResponse
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<TypeResponse>>> findAll(Pageable pageable) {
        final Page<TypeResponse> response = typeService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Creates a new type using the given request parameters
     *
     * @param request
     * @return id of the created type
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @PostMapping
    public ResponseEntity<ApiResponse<CommandResponse>> create(@Valid @RequestBody TypeRequest request) {
        final CommandResponse response = typeService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Updates type using the given request parameters
     *
     * @return id of the updated type
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @PutMapping
    public ResponseEntity<ApiResponse<CommandResponse>> update(@Valid @RequestBody TypeRequest request) {
        final CommandResponse response = typeService.update(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Deletes type by id
     *
     * @param id
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable long id) {
        typeService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
