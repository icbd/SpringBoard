package org.springboard.repository;

import org.springboard.entity.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {

    AccessToken findByToken(String token);
}
