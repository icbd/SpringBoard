package org.springboard.repository;

import org.springboard.entity.Role;
import org.springboard.entity.RoleAndUser;
import org.springboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleAndUserRepository extends JpaRepository<RoleAndUser, Long> {

    RoleAndUser getByRoleAndUser(Role role, User user);
}
