package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Permission;
import org.springboard.repository.PermissionRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public Permission getPermissionById(Long id) {
        return permissionRepository.getOne(id);
    }

    public void destroyPermission(Long id) {
        permissionRepository.deleteById(id);
    }
}
