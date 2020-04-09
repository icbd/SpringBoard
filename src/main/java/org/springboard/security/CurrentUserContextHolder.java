package org.springboard.security;

import lombok.NoArgsConstructor;
import org.springboard.entity.User;

@NoArgsConstructor
public class CurrentUserContextHolder {

    private final static InheritableThreadLocal<User> CURRENT_USER_CONTEXT
            = new InheritableThreadLocal<>();

    public static User getContext() {
        return CURRENT_USER_CONTEXT.get();
    }

    public static void setContext(User user) {
        CURRENT_USER_CONTEXT.set(user);
    }

    public static void clearContext() {
        CURRENT_USER_CONTEXT.remove();
    }
}
