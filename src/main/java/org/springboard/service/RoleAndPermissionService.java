package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Permission;
import org.springboard.entity.Role;
import org.springboard.entity.RoleAndPermission;
import org.springboard.repository.RoleAndPermissionRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RoleAndPermissionService {

    private final RoleAndPermissionRepository roleAndPermissionRepository;

    public RoleAndPermission getRoleAndPermissionById(Long id) {
        return roleAndPermissionRepository.getOne(id);
    }

    public RoleAndPermission findByRoleAndAndPermission(Role role, Permission permission) {
        return roleAndPermissionRepository
                .findByRoleAndAndPermission(role.getId(), permission.getId());
    }

    public RoleAndPermission bindBetween(Role role, Permission permission) {
        RoleAndPermission roleAndPermission = RoleAndPermission
                .builder()
                .role(role)
                .permission(permission)
                .build();
        return roleAndPermissionRepository.save(roleAndPermission);
    }

    public void unbindBetween(Role role, Permission permission) {
        RoleAndPermission roleAndPermission = roleAndPermissionRepository
                .findByRoleAndAndPermission(role.getId(), permission.getId());
        destroyRoleAndPermission(roleAndPermission.getId());
    }

    public void destroyRoleAndPermission(Long id) {
        roleAndPermissionRepository.deleteById(id);
    }
}
