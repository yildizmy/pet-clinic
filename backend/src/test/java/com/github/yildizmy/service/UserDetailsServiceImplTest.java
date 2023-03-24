package com.github.yildizmy.service;

import com.github.yildizmy.model.User;
import com.github.yildizmy.repository.UserRepository;
import com.github.yildizmy.security.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Unit Test for UserDetailsServiceImpl methods
 */
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    /**
     * Method under test: {@link UserDetailsServiceImpl#loadUserByUsername(String)}
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/data/auth.csv")
    void loadUserByUsername_should_returnUserDetails_when_UserIsFound(long id, String username, String password) throws UsernameNotFoundException {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails result = userDetailsServiceImpl.loadUserByUsername(username);

        assertEquals(username, result.getUsername());
        assertEquals(password, result.getPassword());
    }

    /**
     * Method under test: {@link UserDetailsServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void loadUserByUsername_should_throwUsernameNotFoundException_when_UserNotFound() {
        String username = "username";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsServiceImpl.loadUserByUsername(username);
        });
    }
}
