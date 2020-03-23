package org.springboard.util;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springboard.util.ControllerHelper.parseAccessTokenFrom;

@Nested
class ControllerHelperTest {

    @Test
    void parseAccessTokenFromTest() throws Exception {
        assertTrue(parseAccessTokenFrom(null).isEmpty());
        assertTrue(parseAccessTokenFrom("").isEmpty());
        assertTrue(parseAccessTokenFrom("Bearer").isEmpty());
        assertTrue(parseAccessTokenFrom("Bearer token").isPresent());
        assertEquals("token", parseAccessTokenFrom("Bearer token").get());
    }
}
