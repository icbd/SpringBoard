package org.springboard.repository;

import org.springboard.entity.RoleAndPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface RoleAndPermissionRepository extends JpaRepository<RoleAndPermission, Long> {

    @Query("select rp from RoleAndPermission rp where" +
            " rp.role.id = :roleId and rp.permission.id = :permissionId and rp.deletedAt is null" +
            " order by rp.id")
    List<RoleAndPermission> findByRoleAndAndPermission(@NotNull Long roleId, @NotNull Long permissionId);
}
