package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.constant.PermissionEnum;
import org.springboard.entity.Permission;
import org.springboard.entity.Product;
import org.springboard.entity.Role;
import org.springboard.entity.RoleAndPermission;
import org.springboard.entity.User;
import org.springboard.repository.PermissionRepository;
import org.springboard.repository.RoleAndPermissionRepository;
import org.springboard.repository.RoleRepository;
import org.springboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springboard.util.ListHelper.sample;

@DataJpaTest
@Nested
class RoleAndPermissionServiceTest extends ServiceTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleAndPermissionRepository roleAndPermissionRepository;

    @Autowired
    private RoleAndPermissionService roleAndPermissionService;

    private User creator;
    private List<RoleAndPermission> roleAndPermissions = new ArrayList<>();
    private RoleAndPermission aCase;

    @BeforeEach
    void setUp() {
        creator = userRepository.save(buildUser());
        List<Permission> permissions = permissionRepository.saveAll(buildPermissions());
        Role role = roleRepository.save(buildRole(creator));

        roleAndPermissions = permissions.stream()
                                        .map(permission -> {
                                            RoleAndPermission roleAndPermission =
                                                    RoleAndPermission.builder().role(role).permission(permission)
                                                                     .build();
                                            return roleAndPermissionRepository.save(roleAndPermission);
                                        })
                                        .collect(Collectors.toList());
        aCase = sample(roleAndPermissions);
    }

    @Test
    void getRoleAndPermissionByIdTest() {
        RoleAndPermission roleAndPermissionById = roleAndPermissionService.getRoleAndPermissionById(aCase.getId());
        assertEquals(aCase.getRole().getId(), roleAndPermissionById.getRole().getId());
    }

    @Test
    void findByRoleAndAndPermissionTest() {
        RoleAndPermission byRoleAndAndPermission = roleAndPermissionService
                .findByRoleAndAndPermission(aCase.getRole(), aCase.getPermission());

        assertEquals(aCase.getId(), byRoleAndAndPermission.getId());
    }

    @Test
    void bindBetweenTest() {
        long count = roleAndPermissionRepository.count();
        Role role = aCase.getRole();
        Permission permission = permissionRepository.save(
                buildPermission(Product.class.getSimpleName(), new Random().nextLong(), PermissionEnum.ADMIN));
        roleAndPermissionService.bindBetween(role, permission);

        assertEquals(count + 1, roleAndPermissionRepository.count());
        assertEquals(permission.getId(), roleAndPermissionService.findByRoleAndAndPermission(role, permission).getId());
    }

    @Test
    void unbindBetweenTest() {
        long count = roleAndPermissionRepository.count();
        Role role = aCase.getRole();
        Permission permission = aCase.getPermission();
        roleAndPermissionService.unbindBetween(role, permission);

        assertEquals(count - 1, roleAndPermissionRepository.count());
        assertNull(roleAndPermissionService.findByRoleAndAndPermission(role, permission));
    }

    @Test
    void destroyRoleAndPermissionTest() {
        long count = roleAndPermissionRepository.count();
        roleAndPermissionService.destroyRoleAndPermission(aCase.getId());
        assertEquals(count - 1, roleAndPermissionRepository.count());
    }
}
