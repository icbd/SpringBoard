package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.constant.PermissionEnum;
import org.springboard.entity.Listing;
import org.springboard.entity.Product;
import org.springboard.entity.Role;
import org.springboard.entity.User;
import org.springboard.exception.PermissionErrorException;
import org.springboard.repository.RoleAndUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static org.springboard.constant.PermissionEnum.ADMIN;
import static org.springboard.constant.PermissionEnum.EDIT;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final RoleAndUserRepository roleAndUserRepository;

    public List<Role> can(Product product, PermissionEnum code, User user) {
        List<Role> matchedRoles = roleAndUserRepository
                .findRoleBySourceAndUser(product.getClass().getSimpleName(), product.getId(), code, user);
        if (!ObjectUtils.isEmpty(matchedRoles)) {
            return matchedRoles;
        }

        throw new PermissionErrorException();
    }

    public List<Role> can(Listing listing, PermissionEnum code, User user) {
        // 编辑listing只需要product的EDIT权限
        if (ADMIN.equals(code)) {
            code = EDIT;
        }

        return can(listing.getProduct(), code, user);
    }
}
