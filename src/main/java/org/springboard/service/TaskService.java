package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Task;
import org.springboard.mapper.TaskMapper;
import org.springboard.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public Task getTaskById(Long id) {
        return taskRepository.getOne(id);
    }

    public Task getTaskByUuid(String uuid) {
        Task task = taskRepository.getByUuid(uuid);
        if (task == null) {
            throw new EntityNotFoundException();
        }
        return task;
    }

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void destroyTask(Long id) {
        taskRepository.deleteById(id);
    }
}
