package org.springboard.repository;

import org.springboard.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task getByUuid(String uuid);
}
