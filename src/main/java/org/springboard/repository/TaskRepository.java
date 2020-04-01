package org.springboard.repository;

import org.springboard.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task getByUuid(UUID uuid);
}
