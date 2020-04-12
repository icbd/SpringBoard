package org.springboard.constant;

import lombok.Getter;

@Getter
public enum PermissionEnum {

    READ(1),  // 2^0=1
    EDIT(2),  // 2^1=2
    ADMIN(4); // 2^2=4

    private Integer code;

    PermissionEnum(int code) {
        this.code = code;
    }
}
