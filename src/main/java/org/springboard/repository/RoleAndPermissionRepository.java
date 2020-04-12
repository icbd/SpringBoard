package org.springboard.repository;

import org.springboard.entity.RoleAndPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleAndPermissionRepository extends JpaRepository<RoleAndPermission, Long> {
}
