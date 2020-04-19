package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.constant.PermissionEnum;
import org.springboard.entity.BaseEntityWithoutDeletedAt;
import org.springboard.entity.Permission;
import org.springboard.entity.Product;
import org.springboard.entity.Role;
import org.springboard.entity.User;
import org.springboard.repository.PermissionRepository;
import org.springboard.repository.ProductRepository;
import org.springboard.repository.RoleAndPermissionRepository;
import org.springboard.repository.RoleRepository;
import org.springboard.repository.UserRepository;
import org.springboard.vo.CreateRoleVo;
import org.springboard.vo.UpdateRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Nested
class RoleServiceTest extends ServiceTestBase {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleAndPermissionRepository roleAndPermissionRepository;

    @Autowired
    private EntityManager entityManager;

    private User creator;
    private List<Permission> permissions;
    private Role aCase;

    @BeforeEach
    void setUp() {
        creator = userRepository.save(buildUser());
        permissions = permissionRepository.saveAll(buildPermissions());

        aCase = buildRole(creator);
        aCase.setPermissions(permissions);
        roleRepository.saveAndFlush(aCase);
    }

    @Test
    void getRoleByIdTest() {
        Role role = roleService.getRoleById(aCase.getId());
        assertEquals(aCase.getTitle(), role.getTitle());
    }

    @Test
    void createRoleTest() {
        long count = roleRepository.count();
        List<Long> permissionIds =
                permissions.stream().map(BaseEntityWithoutDeletedAt::getId).collect(Collectors.toList());
        CreateRoleVo vo = buildCreateRoleVo(permissionIds);
        Role role = roleService.createRole(vo, creator);
        assertEquals(count + 1, roleRepository.count());
        assertEquals(vo.getTitle(), role.getTitle());

        permissionRepository.flush();
        Permission one = permissionRepository.getOne(permissionIds.get(0));
        entityManager.refresh(one);
        assertTrue(one.getRoles().contains(role));
    }

    @Test
    void updateRoleTest() {
        List<Long> targetPermissionIds = new ArrayList<>();
        Permission newPermission = permissionRepository.saveAndFlush(
                buildPermission(Product.class.getSimpleName(), new Random().nextLong(), PermissionEnum.EDIT));
        targetPermissionIds.add(newPermission.getId());
        targetPermissionIds.add(permissions.get(0).getId());

        UpdateRoleVo vo = buildUpdateRoleVo(targetPermissionIds);
        roleService.updateRole(aCase, vo);
        List<Long> permissionIds = roleAndPermissionRepository.findPermissionsByRoleId(aCase.getId())
                                                              .stream().map(BaseEntityWithoutDeletedAt::getId)
                                                              .collect(Collectors.toList());
        assertTrue(permissionIds.size() == 2);
        assertTrue(permissionIds.containsAll(targetPermissionIds));
    }

    @Test
    void destroyRoleTest() {
        roleService.destroyRole(aCase.getId());
        List<Permission> permissionsByRoleId = roleAndPermissionRepository.findPermissionsByRoleId(aCase.getId());
        assertTrue(permissionsByRoleId.isEmpty());
    }
}
