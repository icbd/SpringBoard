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
import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ListingService listingService;

    public Task getTaskById(Long id) {
        return taskRepository.getOne(id);
    }

    public Task getTaskByUuid(UUID uuid) {
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

    // TODO: parent_id 循环引用
    public Task updateTask(Task task, UpdateTaskVo vo) {
        taskMapper.mergeTask(task, vo);
        bindRelation(task, vo.getListingUuid(), vo.getParentUuid());
        if (vo.getCompleted() != null && vo.getCompleted()) {
            task.setCompletedAt(ZonedDateTime.now());
        }
        return taskRepository.save(task);
    }

    private void bindRelation(Task task, UUID listingUuid, UUID parentUuid) {
        if (listingUuid != null) {
            bindRelationByListing(task, listingUuid);
        }

        if (parentUuid != null) {
            bindRelationByParent(task, parentUuid);
        }
    }

    private void bindRelationByListing(Task task, UUID listingUuid) {
        Listing listing = listingService.getListingByUuid(listingUuid);
        task.setParentTask(null);
        task.setListing(listing);
    }

    private void bindRelationByParent(Task task, UUID parentUuid) {
        Task parentTask = getTaskByUuid(parentUuid);
        task.setParentTask(parentTask);
        task.setListing(parentTask.getListing());
    }
}
