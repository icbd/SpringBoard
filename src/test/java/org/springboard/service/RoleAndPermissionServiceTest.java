package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.entity.Permission;
import org.springboard.entity.Role;
import org.springboard.entity.User;
import org.springboard.repository.RoleAndPermissionRepository;
import org.springboard.repository.RoleRepository;
import org.springboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@Nested
class RoleAndPermissionServiceTest extends ServiceTestBase {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleAndPermissionRepository roleAndPermissionRepository;

    @Autowired
    private RoleAndPermissionService roleAndPermissionService;

    private Role role;
    private List<Permission> permissions;

    @BeforeEach
    void setUp() {
        User user = userRepository.save(buildUser());
        role = buildRole(user);
        permissions = buildPermissions();
        role.setPermissions(permissions);
        roleRepository.save(role);
    }

    @Test
    void findPermissionsByRoleIdTest() {
        List<Permission> permissionsByRoleId = roleAndPermissionService.findPermissionsByRoleId(role.getId());
        assertEquals(permissions.size(), permissionsByRoleId.size());
        assertTrue(permissionsByRoleId.containsAll(permissions));
    }

    @Test
    void findRolesByPermissionIdTest() {
        List<Role> roles = roleAndPermissionService.findRolesByPermissionId(permissions.get(0).getId());
        assertEquals(1, roles.size());
        assertTrue(roles.contains(role));
    }

    @Test
    void deleteByRoleAndPermissionTest() {
        Long count = roleAndPermissionRepository.count();
        roleAndPermissionService.deleteByRoleAndPermission(role, permissions.get(0));
        assertEquals(count - 1, roleAndPermissionRepository.count());
    }
}
