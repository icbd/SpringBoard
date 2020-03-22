package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springboard.entity.AccessToken;
import org.springboard.entity.User;
import org.springboard.repository.AccessTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Optional<AccessToken> findAccessTokenById(Long id) {
        return accessTokenRepository.findById(id);
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
}