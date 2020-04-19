package org.springboard.controller.api.v1;

import lombok.Getter;
import lombok.Setter;
import org.springboard.entity.User;
import org.springboard.security.CurrentUserContextHolder;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseController {

    public static final String PREFIX = "/api/v1";

    protected User getCurrentUser() {
        return CurrentUserContextHolder.getContext();
    }
}
