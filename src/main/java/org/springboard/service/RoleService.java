package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Permission;
import org.springboard.entity.Role;
import org.springboard.entity.User;
import org.springboard.mapper.RoleMapper;
import org.springboard.repository.RoleRepository;
import org.springboard.vo.CreateRoleVo;
import org.springboard.vo.UpdateRoleVo;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PermissionService permissionService;
    private final RoleAndPermissionService roleAndPermissionService;

    public Role getRoleById(Long id) {
        return roleRepository.getOne(id);
    }

    public Role createRole(CreateRoleVo vo, User creator) {
        List<Permission> permissions = permissionService.findPermissionsByIds(vo.getPermissionIds());
        Role role = roleMapper.createRole(vo);
        role.setCreator(creator);
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    public Role updateRole(Role role, UpdateRoleVo vo) {
        roleMapper.mergeRole(role, vo);
        List<Permission> permissions = permissionService.findPermissionsByIds(vo.getPermissionIds());
        role.setPermissions(permissions);
        return roleRepository.save(role);
    }

    public void destroyRole(Long id) {
        roleRepository.deleteById(id);
    }
}