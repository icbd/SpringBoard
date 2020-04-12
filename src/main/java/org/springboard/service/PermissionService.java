package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Permission;
import org.springboard.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public Permission getPermissionById(Long id) {
        return permissionRepository.getOne(id);
    }

    public List<Permission> findPermissionsByIds(List<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return new ArrayList<>();
        }

        return permissionRepository.findByIds(permissionIds);
    }

    public void destroyPermission(Long id) {
        permissionRepository.deleteById(id);
    }
}
