package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.constant.RolePackageEnum;
import org.springboard.entity.BaseEntityWithoutDeletedAt;
import org.springboard.entity.Permission;
import org.springboard.entity.Role;
import org.springboard.entity.User;
import org.springboard.vo.CreateRoleVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 为新资源创建角色包
 */
@Service
@RequiredArgsConstructor
public class RolePackageService {

    private final RoleService roleService;
    private final PermissionService permissionService;

    public Role createRolePackage(String sourceType, Long sourceId, RolePackageEnum packageEnum, User creator) {
        List<Permission> permissions = permissionService.findOrCreateBy(sourceType,
                                                                        sourceId,
                                                                        packageEnum.getPermissionEnums());
        List<Long> permissionIds = permissions.stream()
                                              .map(BaseEntityWithoutDeletedAt::getId)
                                              .collect(Collectors.toList());
        CreateRoleVo roleVo = CreateRoleVo.builder()
                                          .permissionIds(permissionIds)
                                          .title(packageEnum.name())
                                          .build();
        return roleService.createRole(roleVo, creator);
    }
}
