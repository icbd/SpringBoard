package org.springboard.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springboard.entity.AccessToken;
import org.springboard.entity.BaseEntity;
import org.springboard.entity.User;
import org.springboard.exception.AuthenticationErrorException;
import org.springboard.repository.AccessTokenRepository;
import org.springboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springboard.util.ListHelper.sample;

@DataJpaTest
@Nested
class AccessTokenServiceTest extends ServiceTestBase {

    @Autowired
    private AccessTokenRepository accessTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessTokenService accessTokenService;

    private User aUser;
    private List<AccessToken> cases = new ArrayList<>();
    private AccessToken aCase;

    @BeforeEach
    void setUp() {
        aUser = userRepository.save(buildUser());
        IntStream.range(0, CASE_COUNT).forEach(i -> cases.add(accessTokenRepository.save(buildAccessToken(aUser))));
        aCase = sample(cases);
    }

    @Test
    void getAccessTokenByIdTest() {
        AccessToken accessToken = accessTokenService.getAccessToken(aCase.getId());
        assertEquals(aCase.getToken(), accessToken.getToken());
    }

    @Nested
    class GetAccessToken {
        @Test
        void getAccessTokenByTokenTest() {
            AccessToken accessToken = accessTokenService.getAccessToken(aCase.getToken());
            assertEquals(aCase.getToken(), accessToken.getToken());
        }

        @Test
        void withInvalidToken() {
            assertThrows(AuthenticationErrorException.class, () -> accessTokenService.getAccessToken("INVALID TOKEN"));
        }

        @Test
        void withExpiredToken() {
            aCase.setExpiredAt(LocalDateTime.now().minusDays(1));
            accessTokenRepository.save(aCase);

            assertThrows(AuthenticationErrorException.class, () -> accessTokenService.getAccessToken(aCase.getToken()));
        }
    }

    @Test
    void findAvailableAccessTokenTest() {
        aCase.setExpiredAt(LocalDateTime.now().minusDays(1));
        accessTokenRepository.save(aCase);

        List<AccessToken> accessTokens = accessTokenService.findAvailableAccessToken(aUser);
        assertEquals(CASE_COUNT - 1, accessTokens.size());
    }

    @Nested
    class findOrCreateAccessToken {
        private List<Long> caseIds;

        @BeforeEach
        void setup() {
            caseIds = cases.stream().map(BaseEntity::getId).collect(Collectors.toList());
        }

        @Test
        void findExist() {
            AccessToken accessToken = accessTokenService.findOrCreateAccessToken(aUser);
            assertTrue(caseIds.contains(accessToken.getId()));
        }

        @Test
        void createNew() {
            User user = userRepository.save(buildUser());
            AccessToken accessToken = accessTokenService.findOrCreateAccessToken(user);
            assertFalse(caseIds.contains(accessToken.getId()));
        }
    }

    @Test
    void createAccessTokenTest() {
        long originCount = accessTokenRepository.count();
        accessTokenService.createAccessToken(aUser);
        assertEquals(originCount + 1, accessTokenRepository.count());
    }

    @Test
    void updateAccessTokenTest() {
        LocalDateTime expiredAt = LocalDateTime.now().plusDays(100);
        accessTokenService.updateAccessToken(aCase, expiredAt);
        assertEquals(expiredAt, aCase.getExpiredAt());
    }

    @Test
    void destroyAccessTokenTest() {
        long originCount = accessTokenRepository.count();
        accessTokenService.destroyAccessToken(aCase.getId());
        assertEquals(originCount - 1, accessTokenRepository.count());
    }

    @Test
    void destroyAllAccessTokensTest() {
        accessTokenService.destroyAllAccessTokens(aUser);
        assertEquals(0, accessTokenRepository.count());
    }
}
