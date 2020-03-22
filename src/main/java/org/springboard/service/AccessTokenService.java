package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springboard.entity.AccessToken;
import org.springboard.entity.User;
import org.springboard.exception.PermissionErrorException;
import org.springboard.repository.AccessTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
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
            throw new PermissionErrorException("Token is invalid.");
        } else if (accessToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new PermissionErrorException("Token has expired.");
        }

        return accessToken;
    }

    public List<AccessToken> findAvailableAccessToken(User user) {
        return accessTokenRepository.findAvailableByUserId(user.getId(), LocalDateTime.now());
    }

    public Optional<AccessToken> findAccessTokenById(Long id) {
        return accessTokenRepository.findById(id);
    }

    public AccessToken findOrCreateAccessToken(User user) {
        List<AccessToken> availableAccessTokens = findAvailableAccessToken(user);
        if (availableAccessTokens.size() > 0) {
            return availableAccessTokens.get(0);
        }
        return createAccessToken(user);
    }

    public AccessToken createAccessToken(User user) {
        LocalDateTime expiredAt = LocalDateTime.now().plusDays(EXPIRED_DAYS);
        return createAccessToken(user, expiredAt);
    }

    public AccessToken createAccessToken(User user, LocalDateTime expiredAt) {
        AccessToken accessToken = AccessToken.builder()
                                             .user(user)
                                             .token(RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH))
                                             .expiredAt(expiredAt)
                                             .build();
        return accessTokenRepository.save(accessToken);
    }

    public void updateAccessToken(AccessToken accessToken, LocalDateTime expiredAt) {
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