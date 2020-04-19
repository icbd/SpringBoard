package org.springboard.repository;

import org.springboard.constant.PermissionEnum;
import org.springboard.entity.Role;
import org.springboard.entity.RoleAndUser;
import org.springboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleAndUserRepository extends JpaRepository<RoleAndUser, Long> {

    RoleAndUser getByRoleAndUser(Role role, User user);

    @Query("select ru.role from RoleAndUser ru inner join ru.role.permissions p " +
            " where ru.user = :user  and p.sourceType = :sourceType and p.sourceId = :sourceId and p.code = :code ")
    List<Role> findRoleBySourceAndUser(String sourceType, Long sourceId, PermissionEnum code, User user);
}
