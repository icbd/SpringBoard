package org.springboard.repository;

import org.springboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByUuid(UUID uuid);

    User getByEmail(String email);
}
