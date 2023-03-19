package com.github.yildizmy.service;

import com.github.yildizmy.model.Role;
import com.github.yildizmy.model.RoleType;
import com.github.yildizmy.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

/**
 * Unit Test for RoleService methods
 */
@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;

    /**
     * Method under test: {@link RoleService#findAll()}
     */
    @Test
    void findAll_should_returnRoleList() {
        Role role = new Role();
        role.setId(101L);
        role.setType(RoleType.ROLE_USER);

        List<Role> roles = new ArrayList<>();
        roles.add(role);

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> result = roleService.findAll();

        assertSame(roles, result);
        assertEquals(1, result.size());
        assertEquals(101L, result.get(0).getId());
        assertEquals(RoleType.ROLE_USER, result.get(0).getType());
    }
}

