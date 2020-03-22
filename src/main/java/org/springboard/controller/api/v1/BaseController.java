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

    /**
     * 该资源仅对入参的user开放, 其他人403
     *
     * @param user
     */
    protected void assertOnlyFor(User user) {
        return;
//        if (!user.getId().equals(currentUser.getId())) {
//            throw new PermissionErrorException("Resources are only open to themselves.");
//        }
    }
}
