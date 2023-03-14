package com.github.yildizmy.controller;

import com.github.yildizmy.dto.request.ProfileRequest;
import com.github.yildizmy.dto.request.UserRequest;
import com.github.yildizmy.dto.response.ApiResponse;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.UserResponse;
import com.github.yildizmy.service.UserService;
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
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final Clock clock;
    private final UserService userService;

    /**
     * Fetches a single user by the given id
     *
     * @param id
     * @return UserResponse
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable long id) {
        final UserResponse response = userService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Fetches all users based on the given parameters
     *
     * @param pageable
     * @return List of UserResponse
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_ADMIN)")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> findAll(Pageable pageable) {
        final Page<UserResponse> response = userService.findAll(pageable);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Creates a new user using the given request parameters
     *
     * @param request
     * @return id of the created user
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_ADMIN)")
    @PostMapping
    public ResponseEntity<ApiResponse<CommandResponse>> create(@Valid @RequestBody UserRequest request) {
        final CommandResponse response = userService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Updates user using the given request parameters
     *
     * @return id of the updated user
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @PutMapping
    public ResponseEntity<ApiResponse<CommandResponse>> update(@Valid @RequestBody ProfileRequest request) {
        final CommandResponse response = userService.update(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Updates user profile by Full Name (First Name and Last Name fields)
     *
     * @return id of the updated user
     */
    @PreAuthorize("hasRole(T(com.github.yildizmy.model.RoleType).ROLE_USER)")
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<CommandResponse>> updateFullName(@Valid @RequestBody ProfileRequest request) {
        final CommandResponse response = userService.updateFullName(request);
        return ResponseEntity.ok(new ApiResponse<>(Instant.now(clock).toEpochMilli(), SUCCESS, response));
    }

    /**
     * Deletes user by id
     *
     * @param id
     */
    @PreAuthorize("hasAnyRole(T(com.github.yildizmy.model.RoleType).ROLE_ADMIN)")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteById(@PathVariable long id) {
        userService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}