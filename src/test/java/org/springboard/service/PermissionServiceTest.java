package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.constant.PermissionEnum;
import org.springboard.entity.Permission;
import org.springboard.entity.Task;
import org.springboard.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Nested
class PermissionServiceTest {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionRepository permissionRepository;

    private Permission aCase;

    @BeforeEach
    void setUp() {
        aCase = Permission.builder()
                          .sourceType(Task.class.toString())
                          .sourceId(new Random().nextLong())
                          .code(PermissionEnum.ADMIN)
                          .build();
        permissionRepository.saveAndFlush(aCase);
    }

    @Test
    void getPermissionByIdTest() {
        Permission permission = permissionService.getPermissionById(aCase.getId());
        assertEquals(aCase.getCode(), permission.getCode());
    }

    @Test
    void destroyPermissionTest() {
        long count = permissionRepository.count();
        permissionService.destroyPermission(aCase.getId());
        assertEquals(count - 1, permissionRepository.count());
    }
}
