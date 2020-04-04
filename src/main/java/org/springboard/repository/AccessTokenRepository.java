package org.springboard.repository;

import org.springboard.entity.AccessToken;
import org.springboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    AccessToken getByToken(String token);

    void deleteAllByUser(User user);

    @Query("select tk from AccessToken tk where tk.user.id = ?1 and tk.expiredAt > ?2 order by tk.createdAt desc")
    List<AccessToken> findAvailableByUserId(Long userId, ZonedDateTime compareWith);
}
