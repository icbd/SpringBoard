package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springboard.entity.AccessToken;
import org.springboard.entity.User;
import org.springboard.exception.AuthenticationErrorException;
import org.springboard.repository.AccessTokenRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class AccessTokenService {

    private static final Integer EXPIRED_DAYS = 60;
    private static final Integer TOKEN_LENGTH = 30;
    private final AccessTokenRepository accessTokenRepository;

    public AccessToken getAccessToken(Long id) {
        return accessTokenRepository.getOne(id);
    }

    public AccessToken getAccessToken(String token) {
        AccessToken accessToken = accessTokenRepository.getByToken(token);
        if (accessToken == null) {
            throw new AuthenticationErrorException("Token is invalid.");
        } else if (accessToken.getExpiredAt().isBefore(ZonedDateTime.now())) {
            throw new AuthenticationErrorException("Token has expired.");
        }

        return accessToken;
    }

    public List<AccessToken> findAvailableAccessToken(User user) {
        return accessTokenRepository.findAvailableByUserId(user.getId(), ZonedDateTime.now());
    }

    public AccessToken findOrCreateAccessToken(User user) {
        List<AccessToken> availableAccessTokens = findAvailableAccessToken(user);
        if (availableAccessTokens.size() > 0) {
            return availableAccessTokens.get(0);
        }
        return createAccessToken(user);
    }

    public AccessToken createAccessToken(User user) {
        ZonedDateTime expiredAt = ZonedDateTime.now().plusDays(EXPIRED_DAYS);
        return createAccessToken(user, expiredAt);
    }

    public AccessToken createAccessToken(User user, ZonedDateTime expiredAt) {
        AccessToken accessToken = AccessToken.builder()
                                             .user(user)
                                             .token(RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH))
                                             .expiredAt(expiredAt)
                                             .build();
        return accessTokenRepository.save(accessToken);
    }

    public void updateAccessToken(AccessToken accessToken, ZonedDateTime expiredAt) {
        accessToken.setExpiredAt(expiredAt);
        accessTokenRepository.save(accessToken);
    }

    public void destroyAccessToken(Long id) {
        accessTokenRepository.deleteById(id);
    }

    public void destroyAllAccessTokens(User user) {
        accessTokenRepository.deleteAllByUser(user);
    }
}