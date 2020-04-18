package org.springboard.constant;

import lombok.Getter;

import java.util.List;

import static org.springboard.constant.PermissionEnum.ADMIN;
import static org.springboard.constant.PermissionEnum.EDIT;
import static org.springboard.constant.PermissionEnum.READ;

@Getter
public enum RolePackageEnum {

    VIEWER(List.of(READ)),
    PARTNER(List.of(READ, EDIT)),
    ADMINISTRATOR(List.of(READ, EDIT, ADMIN)),
    ;

    private List<PermissionEnum> permissionEnums;

    RolePackageEnum(List<PermissionEnum> permissionEnums) {
        this.permissionEnums = permissionEnums;
    }
}
