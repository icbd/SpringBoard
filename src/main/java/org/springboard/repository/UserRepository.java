package org.springboard.repository;

import org.springboard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByUuid(String uuid);

    User getByEmail(String email);
}
