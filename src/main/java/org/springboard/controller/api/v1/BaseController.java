package org.springboard.controller.api.v1;

import lombok.Getter;
import lombok.Setter;
import org.springboard.entity.User;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseController {
    protected static final String PREFIX = "/api/v1";

    // Set by AOP
    private User currentUser;
}
