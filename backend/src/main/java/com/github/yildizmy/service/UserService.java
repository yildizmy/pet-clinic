package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.UserRequestMapper;
import com.github.yildizmy.dto.mapper.UserResponseMapper;
import com.github.yildizmy.dto.request.ProfileRequest;
import com.github.yildizmy.dto.request.UserRequest;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.UserResponse;
import com.github.yildizmy.exception.ElementAlreadyExistsException;
import com.github.yildizmy.exception.NoSuchElementFoundException;
import com.github.yildizmy.model.Role;
import com.github.yildizmy.model.RoleType;
import com.github.yildizmy.model.User;
import com.github.yildizmy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.github.yildizmy.common.Constants.*;

/**
 * Service used for User related operations
 */
@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    /**
     * Fetches a single user by the given id
     *
     * @param id
     * @return UserResponse
     */
    public UserResponse findById(long id) {
        return userRepository.findById(id)
                .map(userResponseMapper::toDto)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_USER));
    }

    /**
     * Fetches a single user (entity) by the given id
     *
     * @param id
     * @return User
     */
    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_USER));
    }

    /**
     * Fetches all users based on the given paging and sorting parameters
     *
     * @param pageable
     * @return List of UserResponse
     */
    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(Pageable pageable) {
        final Page<UserResponse> users = userRepository.findAll(pageable)
                .map(userResponseMapper::toDto);

        if (users.isEmpty())
            throw new NoSuchElementFoundException(NOT_FOUND_RECORD);
        return users;
    }

    /**
     * Creates a new user using the given request parameters
     *
     * @param request
     * @return id of the created user
     */
    public CommandResponse create(UserRequest request) {
        if (userRepository.existsByUsernameIgnoreCase(request.getUsername()))
            throw new ElementAlreadyExistsException(ALREADY_EXISTS_USER);

        // add default role (ROLE_USER) to the user
        final Set<Role> roles = new HashSet<>(Arrays.asList(new Role(1L, RoleType.ROLE_USER)));

        // add ROLE_ADMIN role if requested by admin
        if (request.getRoles() != null && request.getRoles().contains(RoleType.ROLE_ADMIN.name()))
            roles.add(new Role(2L, RoleType.ROLE_ADMIN));

        final User user = userRequestMapper.toEntity(request);
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRoles(roles);
        userRepository.save(user);
        log.info(CREATED_USER);
        return CommandResponse.builder().id(user.getId()).build();
    }

    /**
     * Updates user using the given request parameters
     *
     * @param request
     * @return id of the updated user
     */
    public CommandResponse update(ProfileRequest request) {
        final User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_USER));

        // update admin role of the user based on the request
        if (request.getRoles() != null && request.getRoles().contains(RoleType.ROLE_ADMIN.name()))
            user.addRole(new Role(2L, RoleType.ROLE_ADMIN));
        else
            user.removeRole(new Role(2L, RoleType.ROLE_ADMIN));

        user.setFirstName(WordUtils.capitalizeFully(request.getFirstName()));
        user.setLastName(WordUtils.capitalizeFully(request.getLastName()));
        userRepository.save(user);
        log.info(UPDATED_USER);
        return CommandResponse.builder().id(user.getId()).build();
    }

    /**
     * Updates user profile by Full Name (First Name and Last Name fields)
     *
     * @param request
     * @return id of the updated user
     */
    public CommandResponse updateFullName(ProfileRequest request) {
        final User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_USER));

        user.setFirstName(WordUtils.capitalizeFully(request.getFirstName()));
        user.setLastName(WordUtils.capitalizeFully(request.getLastName()));
        userRepository.save(user);
        log.info(UPDATED_USER);
        return CommandResponse.builder().id(user.getId()).build();
    }

    /**
     * Deletes user by the given id
     *
     * @param id
     */
    public void deleteById(long id) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException(NOT_FOUND_USER));
        userRepository.delete(user);
        log.info(DELETED_USER);
    }
}
