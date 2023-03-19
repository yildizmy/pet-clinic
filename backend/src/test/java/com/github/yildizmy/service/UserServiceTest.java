package com.github.yildizmy.service;

import com.github.yildizmy.dto.mapper.UserRequestMapper;
import com.github.yildizmy.dto.mapper.UserResponseMapper;
import com.github.yildizmy.dto.request.ProfileRequest;
import com.github.yildizmy.dto.request.UserRequest;
import com.github.yildizmy.dto.response.CommandResponse;
import com.github.yildizmy.dto.response.RoleResponse;
import com.github.yildizmy.dto.response.UserResponse;
import com.github.yildizmy.exception.ElementAlreadyExistsException;
import com.github.yildizmy.exception.NoSuchElementFoundException;
import com.github.yildizmy.model.Role;
import com.github.yildizmy.model.RoleType;
import com.github.yildizmy.model.User;
import com.github.yildizmy.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit Test for UserService methods
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRequestMapper userRequestMapper;

    @Mock
    private UserResponseMapper userResponseMapper;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    /**
     * Method under test: {@link UserService#findById(long)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/users.csv")
    void findById_should_returnUserResponse_when_UserIsFound(long id, String username, String firstName, String lastName, String fullName, String role) {
        Role r = new Role();
        r.setType(RoleType.valueOf(role));
        Set<Role> roles = new HashSet<>(List.of(r));

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRoles(roles);

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(id);
        roleResponse.setType(RoleType.valueOf(role));
        Set<RoleResponse> roleResponses = new HashSet<>();
        roleResponses.add(roleResponse);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(id);
        userResponse.setUsername(username);
        userResponse.setFirstName(firstName);
        userResponse.setLastName(lastName);
        userResponse.setFullName(fullName);
        userResponse.setRoles(roleResponses);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userResponseMapper.toDto(user)).thenReturn(userResponse);

        UserResponse result = userService.findById(id);

        assertEquals(id, result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(fullName, result.getFullName());
        assertEquals(roleResponses, result.getRoles());
    }

    /**
     * Method under test: {@link UserService#findById(long)}
     */
    @Test
    void findById_should_throwNoSuchElementFoundException_when_UserIsNotFound() {
        long id = 101L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            userService.findById(id);
        });

        verify(userRepository).findById(id);
    }

    /**
     * Method under test: {@link UserService#getById(long)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/users.csv")
    void getById_should_returnUser_when_UserIsFound(long id, String username, String firstName, String lastName, String fullName, String role) {
        Role r = new Role();
        r.setType(RoleType.valueOf(role));
        Set<Role> roles = new HashSet<>(List.of(r));

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRoles(roles);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User result = userService.getById(id);

        assertEquals(id, result.getId());
        assertEquals(username, result.getUsername());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(roles, result.getRoles());
    }

    /**
     * Method under test: {@link UserService#getById(long)}
     */
    @Test
    void getById_should_throwNoSuchElementFoundException_when_UserIsNotFound() {
        long id = 101L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            userService.getById(id);
        });

        verify(userRepository).findById(id);
    }

    /**
     * Method under test: {@link UserService#findAll(Pageable)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/users.csv")
    void findAll_should_returnUserResponsePage_when_UserIsFound(long id, String username, String firstName, String lastName, String fullName, String role) {
        Role r = new Role();
        r.setType(RoleType.valueOf(role));
        Set<Role> roles = new HashSet<>(List.of(r));

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRoles(roles);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        PageImpl<User> pageImpl = new PageImpl<>(userList);
        Pageable pageable = PageRequest.of(0, 10);

        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(id);
        roleResponse.setType(RoleType.valueOf(role));
        Set<RoleResponse> roleResponses = new HashSet<>();
        roleResponses.add(roleResponse);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(id);
        userResponse.setUsername(username);
        userResponse.setFirstName(firstName);
        userResponse.setLastName(lastName);
        userResponse.setFullName(fullName);
        userResponse.setRoles(roleResponses);

        when(userRepository.findAll(pageable)).thenReturn(pageImpl);
        when(userResponseMapper.toDto(user)).thenReturn(userResponse);

        Page<UserResponse> result = userService.findAll(pageable);

        verify(userResponseMapper).toDto(any());
        assertEquals(1, result.getContent().size());
        assertEquals(id, result.getContent().get(0).getId());
        assertEquals(username, result.getContent().get(0).getUsername());
        assertEquals(firstName, result.getContent().get(0).getFirstName());
        assertEquals(lastName, result.getContent().get(0).getLastName());
        assertEquals(fullName, result.getContent().get(0).getFullName());
        assertEquals(roleResponses, result.getContent().get(0).getRoles());
    }

    /**
     * Method under test: {@link UserService#findAll(Pageable)}
     */
    @Test
    void findAll_should_throwNoSuchElementFoundException_when_UserIsNotFound() {
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository.findAll(pageable)).thenReturn(new PageImpl<>(new ArrayList<>()));

        assertThrows(NoSuchElementFoundException.class, () -> {
            userService.findAll(pageable);
        });

        verify(userRepository).findAll(pageable);
    }

    /**
     * Method under test: {@link UserService#create(UserRequest)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/users.csv")
    void create_should_returnCommandResponse_when_RequestedRoleUserOrAdmin(long id, String username, String firstName, String lastName, String fullName, String role) {
        // we expect ROLE_USER and the given role
        Role roleUser = new Role();
        roleUser.setType(RoleType.valueOf("ROLE_USER"));
        Role roleRequested = new Role();
        roleRequested.setType(RoleType.valueOf(role));
        Set<Role> roles = new HashSet<>(List.of(roleUser, roleRequested));

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        UserRequest request = new UserRequest();
        request.setId(id);
        request.setUsername(username);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setRoles(new HashSet<>(List.of(role)));

        when(userRepository.existsByUsernameIgnoreCase(username)).thenReturn(false);
        when(userRequestMapper.toEntity(request)).thenReturn(user);

        CommandResponse result = userService.create(request);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals(capturedUser.getId(), result.id());
        assertEquals(username, capturedUser.getUsername());
        assertEquals(firstName, capturedUser.getFirstName());
        assertEquals(lastName, capturedUser.getLastName());
        assertEquals(roles, capturedUser.getRoles());
    }

    /**
     * Method under test: {@link UserService#create(UserRequest)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/users.csv")
    void create_should_returnCommandResponseWithRoleUser_when_RequestedRoleIsNull(long id, String username, String firstName, String lastName, String fullName, String role) {
        // we expect only ROLE_USER as the given role is null
        Role roleUser = new Role();
        roleUser.setType(RoleType.valueOf("ROLE_USER"));
        Set<Role> roles = new HashSet<>(List.of(roleUser));

        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        UserRequest request = new UserRequest();
        request.setId(id);
        request.setUsername(username);
        request.setFirstName(firstName);
        request.setLastName(lastName);

        when(userRepository.existsByUsernameIgnoreCase(username)).thenReturn(false);
        when(userRequestMapper.toEntity(request)).thenReturn(user);

        CommandResponse result = userService.create(request);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals(capturedUser.getId(), result.id());
        assertEquals(username, capturedUser.getUsername());
        assertEquals(firstName, capturedUser.getFirstName());
        assertEquals(lastName, capturedUser.getLastName());
        assertEquals(roles, capturedUser.getRoles());
    }

    /**
     * Method under test: {@link UserService#create(UserRequest)}
     */
    @Test
    void create_should_throwElementAlreadyExistsException_when_UserAlreadyExists() {
        UserRequest request = new UserRequest();
        request.setUsername("username");

        when(userRepository.existsByUsernameIgnoreCase("username")).thenReturn(true);

        assertThrows(ElementAlreadyExistsException.class, () -> {
            userService.create(request);
        });

        verify(userRepository, never()).save(any());
    }

    /**
     * Method under test: {@link UserService#update(ProfileRequest)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/users.csv")
    void update_should_returnCommandResponse_when_RequestedRoleIsNotNull(long id, String username, String firstName, String lastName, String fullName, String role) {
        Set<String> roles = new HashSet<>(List.of("ROLE_USER", role));

        ProfileRequest request = new ProfileRequest();
        request.setId(id);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setRoles(new HashSet<>(roles));

        Role roleUser = new Role();
        roleUser.setId(1L);
        roleUser.setType(RoleType.valueOf("ROLE_USER"));
        Role roleAdmin = new Role();
        roleAdmin.setId(2L);
        roleAdmin.setType(RoleType.valueOf("ROLE_ADMIN"));
        Set<Role> userRoles = new HashSet<>(List.of(roleUser, roleAdmin));

        User user = new User();
        user.setId(id);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setRoles(userRoles);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        CommandResponse result = userService.update(request);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals(capturedUser.getId(), result.id());
        assertEquals(firstName, capturedUser.getFirstName());
        assertEquals(lastName, capturedUser.getLastName());
        assertEquals(userRoles, capturedUser.getRoles());
    }

    /**
     * Method under test: {@link UserService#update(ProfileRequest)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/users.csv")
    void update_should_returnCommandResponse_when_RequestedRoleIsNull(long id, String username, String firstName, String lastName, String fullName, String role) {
        ProfileRequest request = new ProfileRequest();
        request.setId(id);
        request.setFirstName(firstName);
        request.setLastName(lastName);
        request.setRoles(null);

        Role roleUser = new Role();
        roleUser.setId(1L);
        roleUser.setType(RoleType.valueOf("ROLE_USER"));
        Set<Role> roles = new HashSet<>(List.of(roleUser));

        User user = new User();
        user.setId(id);
        user.setFirstName("FirstName");
        user.setLastName("LastName");
        user.setRoles(roles);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        CommandResponse result = userService.update(request);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals(capturedUser.getId(), result.id());
        assertEquals(firstName, capturedUser.getFirstName());
        assertEquals(lastName, capturedUser.getLastName());
        assertEquals(roles, capturedUser.getRoles());
    }

    /**
     * Method under test: {@link UserService#update(ProfileRequest)}
     */
    @Test
    void update_should_throwNoSuchElementFoundException_when_UserIsNotFound() {
        ProfileRequest request = new ProfileRequest();
        request.setId(101L);

        when(userRepository.findById(101L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            userService.update(request);
        });

        verify(userRepository, never()).save(any());
    }

    /**
     * Method under test: {@link UserService#updateFullName(ProfileRequest)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/users.csv")
    void updateFullName_should_returnCommandResponse_when_RequestedRoleIsNotNull(long id, String username, String firstName, String lastName, String fullName, String role) {
        User user = new User();
        user.setId(id);
        user.setFirstName("FirstName");
        user.setLastName("LastName");

        ProfileRequest request = new ProfileRequest();
        request.setId(id);
        request.setFirstName(firstName);
        request.setLastName(lastName);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        CommandResponse result = userService.updateFullName(request);
        verify(userRepository).save(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals(capturedUser.getId(), result.id());
        assertEquals(firstName, capturedUser.getFirstName());
        assertEquals(lastName, capturedUser.getLastName());
    }

    /**
     * Method under test: {@link UserService#updateFullName(ProfileRequest)}
     */
    @Test
    void updateFullName_should_throwNoSuchElementFoundException_when_UserIsNotFound() {
        ProfileRequest request = new ProfileRequest();
        request.setId(101L);

        when(userRepository.findById(101L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            userService.updateFullName(request);
        });

        verify(userRepository, never()).save(any());
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/data/users.csv")
    void deleteById_should_deleteUser_when_UserIsFound(long id, String username, String firstName, String lastName, String fullName, String role) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.deleteById(id);
        verify(userRepository).delete(userCaptor.capture());
        User capturedUser = userCaptor.getValue();

        assertEquals(id, capturedUser.getId());
        assertEquals(username, capturedUser.getUsername());
        assertEquals(firstName, capturedUser.getFirstName());
        assertEquals(lastName, capturedUser.getLastName());
    }

    /**
     * Method under test: {@link UserService#deleteById(long)}
     */
    @Test
    void deleteById_should_throwNoSuchElementFoundException_when_UserIsNotFound() {
        long id = 101L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementFoundException.class, () -> {
            userService.deleteById(id);
        });

        verify(userRepository).findById(id);
        verify(userRepository, never()).delete(any());
    }
}
