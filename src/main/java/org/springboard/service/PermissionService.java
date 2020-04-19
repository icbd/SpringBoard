package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.constant.PermissionEnum;
import org.springboard.entity.Permission;
import org.springboard.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
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

    public Permission createPermission(String sourceType, Long sourceId, PermissionEnum code) {
        Permission permission = Permission.builder()
                                          .sourceType(sourceType)
                                          .sourceId(sourceId)
                                          .code(code)
                                          .build();
        return permissionRepository.save(permission);
    }

    public List<Permission> findOrCreateBy(String sourceType, Long sourceId, List<PermissionEnum> codes) {
        List<Permission> permissions = permissionRepository
                .findBySourceTypeAndSourceIdAndCodeIsIn(sourceType, sourceId, codes);
        if (permissions.size() == codes.size()) {
            return permissions;
        }

        List<PermissionEnum> existedCodes = permissions.stream()
                                                       .map(Permission::getCode)
                                                       .collect(Collectors.toList());
        ArrayList<PermissionEnum> targetCodes = new ArrayList<>(codes);
        targetCodes.removeAll(existedCodes);
        List<Permission> createdPermissions = targetCodes.stream()
                                                         .map(code -> createPermission(sourceType, sourceId, code))
                                                         .collect(Collectors.toList());
        permissions.addAll(createdPermissions);
        return permissions;
    }

    public void destroyPermission(Long id) {
        permissionRepository.deleteById(id);
    }
}
