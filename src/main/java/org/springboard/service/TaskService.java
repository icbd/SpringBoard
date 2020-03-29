package org.springboard.service;

import lombok.RequiredArgsConstructor;
import org.springboard.entity.Listing;
import org.springboard.entity.Task;
import org.springboard.entity.User;
import org.springboard.mapper.TaskMapper;
import org.springboard.repository.TaskRepository;
import org.springboard.vo.CreateTaskVo;
import org.springboard.vo.UpdateTaskVo;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ListingService listingService;

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

    public Task createTask(CreateTaskVo vo, User creator) {
        Task task = taskMapper.createTask(vo);
        task.setCreator(creator);
        bindRelation(task, vo.getListingUuid(), vo.getParentUuid());
        return taskRepository.save(task);
    }

    public Task updateTask(Task task, UpdateTaskVo vo) {
        taskMapper.mergeTask(task, vo);
        bindRelation(task, vo.getListingUuid(), vo.getParentUuid());
        return taskRepository.save(task);
    }

    private void bindRelation(Task task, String listingUuid, String parentUuid) {
        if (listingUuid != null) {
            bindRelationByListing(task, listingUuid);
        }

        if (parentUuid != null) {
            bindRelationByParent(task, parentUuid);
        }
    }

    private void bindRelationByListing(Task task, String listingUuid) {
        Listing listing = listingService.getListingByUuid(listingUuid);
        task.setParentTask(null);
        task.setListing(listing);
    }

    private void bindRelationByParent(Task task, String parentUuid) {
        Task parentTask = getTaskByUuid(parentUuid);
        task.setParentTask(parentTask);
        task.setListing(parentTask.getListing());
    }
}
