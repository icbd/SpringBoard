package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.entity.AccessToken;
import org.springboard.entity.User;
import org.springboard.exception.ParamsErrorException;
import org.springboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@Nested
class LoginServiceTest extends ServiceTestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(buildUser());
    }

    @Test
    void loginByPasswordTest() {
        AccessToken accessToken = loginService.loginByPassword(user.getEmail(), user.getPassword());
        assertTrue(accessToken.getExpiredAt().isAfter(LocalDateTime.now()));
    }

    @Test
    void withInvalidEmail() {
        assertThrows(ParamsErrorException.class, () -> {
            loginService.loginByPassword("INVALID EMAIL", user.getPassword());
        });
    }

    @Test
    void withInvalidPassword() {
        assertThrows(ParamsErrorException.class, () -> {
            loginService.loginByPassword(user.getEmail(), "INVALID PASSWORD");
        });
    }
}
