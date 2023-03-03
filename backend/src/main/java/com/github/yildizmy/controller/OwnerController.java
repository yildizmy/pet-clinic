package com.github.yildizmy.controller;

import com.github.yildizmy.dto.request.OwnerRequest;
import com.github.yildizmy.dto.response.ApiResponse;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.OwnerResponse;
import com.github.yildizmy.service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.Instant;

import static com.github.yildizmy.common.Constants.SUCCESS;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final Clock clock;
    private final OwnerService ownerService;

    /**
     * Fetches a single owner by the given id
     *
     * @param id
     * @return OwnerResponse
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OwnerResponse>> findById(@PathVariable long id) {
        final OwnerResponse response = ownerService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all owners based on the given parameters
     *
     * @param pageable
     * @return List of OwnerResponse
     */
    @GetMapping
    ResponseEntity<ApiResponse<Page<OwnerResponse>>> findAll(Pageable pageable) {
        final Page<OwnerResponse> response = ownerService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Creates a new owner using the given request parameters
     *
     * @param request
     * @return id of the created owner
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CommandResponse>> create(@Valid @RequestBody OwnerRequest request) {
        final CommandResponse response = ownerService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Updates owner using the given request parameters
     *
     * @return id of the updated owner
     */
    @PutMapping
    public ResponseEntity<ApiResponse<CommandResponse>> update(@Valid @RequestBody OwnerRequest request) {
        final CommandResponse response = ownerService.update(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Deletes owner by id
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable long id) {
        ownerService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
