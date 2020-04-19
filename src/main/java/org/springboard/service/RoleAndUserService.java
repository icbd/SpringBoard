package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Role;
import org.springboard.entity.RoleAndUser;
import org.springboard.entity.User;
import org.springboard.repository.RoleAndUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class RoleAndUserService {

    private final RoleAndUserRepository roleAndUserRepository;

    public RoleAndUser getRoleAndUserById(Long id) {
        return roleAndUserRepository.getOne(id);
    }

    public RoleAndUser bindRoleWithUser(Role role, User user) {
        RoleAndUser relation = roleAndUserRepository.getByRoleAndUser(role, user);
        if (!ObjectUtils.isEmpty(relation)) {
            return relation;
        }

        relation = RoleAndUser.builder().role(role).user(user).build();
        return roleAndUserRepository.save(relation);
    }

    public void unbindRoleWithUser(Role role, User user) {
        RoleAndUser relation = roleAndUserRepository.getByRoleAndUser(role, user);
        if (!ObjectUtils.isEmpty(relation)) {
            destroyRoleAndUser(relation.getId());
        }
    }

    public void destroyRoleAndUser(Long id) {
        roleAndUserRepository.deleteById(id);
    }
}