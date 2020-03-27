package org.springboard.util;

import java.util.Optional;

public class ControllerHelper {

    private static final String BEARER_PREFIX = "Bearer ";

    public static Optional<String> parseAccessTokenFrom(String bearer) {
        if (bearer == null || !bearer.startsWith(BEARER_PREFIX)) {
            return Optional.empty();
        }
        String token = bearer.replaceFirst(BEARER_PREFIX, "");
        return Optional.of(token);
    }
}
