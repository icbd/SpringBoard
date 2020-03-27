package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.AccessToken;
import org.springboard.entity.User;
import org.springboard.exception.ParamsErrorException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserService userService;
    private final AccessTokenService accessTokenService;
    private final PasswordEncoder passwordEncoder;

    public AccessToken loginByPassword(String email, String password) {
        User user;
        try {
            user = userService.getUserByEmail(email);
            if (!passwordEncoder.matches(password, user.getPasswordDigest())) {
                throw new EntityNotFoundException();
            }
        } catch (Exception e) {
            throw new ParamsErrorException("Incorrect Email or Password.");
        }

        return accessTokenService.findOrCreateAccessToken(user);
    }
}
