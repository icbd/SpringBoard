package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Permission;
import org.springboard.entity.Role;
import org.springboard.entity.RoleAndPermission;
import org.springboard.repository.RoleAndPermissionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class RoleAndPermissionService {

    private final RoleAndPermissionRepository roleAndPermissionRepository;

    public RoleAndPermission getRoleAndPermissionById(Long id) {
        return roleAndPermissionRepository.getOne(id);
    }

    public List<Permission> findPermissionsByRoleId(Long roleId) {
        return roleAndPermissionRepository.findPermissionsByRoleId(roleId);
    }

    public List<Role> findRolesByPermissionId(Long permissionId) {
        return roleAndPermissionRepository.findRolesByPermissionId(permissionId);
    }

    public int deleteByRoleAndPermission(Role role, Permission permission) {
        return roleAndPermissionRepository.deleteByRoleAndPermission(role, permission);
    }
}
