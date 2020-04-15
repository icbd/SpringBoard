package org.springboard.repository;

import org.springboard.entity.Permission;
import org.springboard.entity.Role;
import org.springboard.entity.RoleAndPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RoleAndPermissionRepository extends JpaRepository<RoleAndPermission, Long> {

    @Query("select rp.role from RoleAndPermission rp where rp.permission.id = :permissionId")
    List<Role> findRolesByPermissionId(@NotNull Long permissionId);

    @Query("select rp.permission from RoleAndPermission rp where rp.role.id = :roleId")
    List<Permission> findPermissionsByRoleId(@NotNull Long roleId);
}
