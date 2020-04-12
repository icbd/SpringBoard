package org.springboard.constant;

import lombok.Getter;

@Getter
public enum PermissionEnum {

    READ(1),  // 2^0=1
    EDIT(3),  // 2^1=2, 1+2=3
    ADMIN(7); // 2^2=4, 1+2+4=7

    private Integer code;

    PermissionEnum(int code) {
        this.code = code;
    }
}
