package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.constant.PermissionEnum;
import org.springboard.entity.BaseEntity;
import org.springboard.entity.Permission;
import org.springboard.entity.Product;
import org.springboard.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springboard.util.ListHelper.sample;

@DataJpaTest
@Nested
class PermissionServiceTest extends ServiceTestBase {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionRepository permissionRepository;

    private List<Permission> cases = new ArrayList<>();
    private Permission aCase;

    @BeforeEach
    void setUp() {
        cases = buildPermissions()
                .stream()
                .map(c -> permissionRepository.save(c))
                .collect(Collectors.toList());
        aCase = sample(cases);
    }

    @Test
    void getPermissionByIdTest() {
        Permission permission = permissionService.getPermissionById(aCase.getId());
        assertEquals(aCase.getCode(), permission.getCode());
    }

    @Test
    void getPermissionsByIdsTest() {
        assertTrue(permissionService.findPermissionsByIds(null).isEmpty());
        assertTrue(permissionService.findPermissionsByIds(new ArrayList<>()).isEmpty());

        List<Long> caseIds = cases.stream().map(BaseEntity::getId).collect(Collectors.toList());
        List<Permission> permissionsByIds = permissionService.findPermissionsByIds(caseIds);
        assertEquals(caseIds.size(), permissionsByIds.size());
        assertTrue(caseIds.containsAll(permissionsByIds.stream().map(BaseEntity::getId).collect(Collectors.toList())));
    }

    @Nested
    class findOrCreateBytest {
        private String sourceType = Product.class.getSimpleName();
        private Long sourceId = Math.abs(new Random().nextLong());
        private List<PermissionEnum> codes = List.of(PermissionEnum.READ, PermissionEnum.EDIT);
        private List<Permission> existedPermissions;

        @BeforeEach
        void setUp() {
            existedPermissions = codes.stream()
                                      .map(code -> Permission.builder()
                                                             .sourceType(sourceType)
                                                             .sourceId(sourceId)
                                                             .code(code).build())
                                      .collect(Collectors.toList());
            permissionRepository.saveAll(existedPermissions);
        }

        @Test
        void findExistedTest() {
            List<Permission> permissions = permissionService.findOrCreateBy(sourceType, sourceId, codes);

            List<PermissionEnum> codesFound = permissions.stream().map(Permission::getCode)
                                                         .collect(Collectors.toList());
            assertEquals(codes.size(), codesFound.size());
            assertTrue(codes.containsAll(codesFound));
        }

        @Test
        void createNotExistedTest() {
            List<PermissionEnum> codes = List.of(PermissionEnum.EDIT, PermissionEnum.ADMIN);
            List<Permission> permissions = permissionService.findOrCreateBy(sourceType, sourceId, codes);

            List<PermissionEnum> codesFound = permissions.stream().map(Permission::getCode)
                                                         .collect(Collectors.toList());
            assertEquals(codes.size(), codesFound.size());
            assertTrue(codes.containsAll(codesFound));
        }
    }

    @Test
    void destroyPermissionTest() {
        long count = permissionRepository.count();
        permissionService.destroyPermission(aCase.getId());
        assertEquals(count - 1, permissionRepository.count());
    }
}
