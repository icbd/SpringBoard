package org.springboard.repository;

import org.springboard.constant.PermissionEnum;
import org.springboard.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("select pms from Permission pms where pms.id in :permissionIds")
    List<Permission> findByIds(List<Long> permissionIds);

    List<Permission> findBySourceTypeAndSourceIdAndCodeIsIn(String sourceType, Long sourceId, List<PermissionEnum> codes);
}
