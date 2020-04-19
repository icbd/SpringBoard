package org.springboard.controller.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springboard.dto.TaskDto;
import org.springboard.entity.Task;
import org.springboard.mapper.TaskMapper;
import org.springboard.service.AuthorizationService;
import org.springboard.service.TaskService;
import org.springboard.vo.CreateTaskVo;
import org.springboard.vo.UpdateTaskVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springboard.constant.PermissionEnum.ADMIN;
import static org.springboard.constant.PermissionEnum.EDIT;
import static org.springboard.constant.PermissionEnum.READ;

@Api(tags = "任务")
@RequiredArgsConstructor
@RestController
@RequestMapping(BaseController.PREFIX + "/tasks")
public class TaskController extends BaseController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final AuthorizationService authorizationService;

    @ApiOperation("展示任务")
    @GetMapping("/{uuid}")
    public ResponseEntity<TaskDto> show(@PathVariable UUID uuid) {
        Task task = taskService.getTaskByUuid(uuid);
        authorizationService.can(task, READ, getCurrentUser());

        TaskDto taskDto = taskMapper.toTaskDto(task);
        return ResponseEntity.ok(taskDto);
    }

    @ApiOperation("创建任务")
    @PostMapping
    public ResponseEntity<TaskDto> create(@Valid @RequestBody CreateTaskVo vo) {
        Task task = taskService.createTask(vo, getCurrentUser());
        authorizationService.can(task, EDIT, getCurrentUser());

        TaskDto taskDto = taskMapper.toTaskDto(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskDto);
    }

    @ApiOperation("编辑任务")
    @PatchMapping("/{uuid}")
    public ResponseEntity<TaskDto> update(@PathVariable UUID uuid, @Valid @RequestBody UpdateTaskVo vo) {
        Task task = taskService.getTaskByUuid(uuid);
        authorizationService.can(task, EDIT, getCurrentUser());

        taskService.updateTask(task, vo);
        TaskDto taskDto = taskMapper.toTaskDto(task);
        return ResponseEntity.ok(taskDto);
    }

    @ApiOperation("删除任务")
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> destroy(@PathVariable UUID uuid) {
        Task task = taskService.getTaskByUuid(uuid);
        authorizationService.can(task, ADMIN, getCurrentUser());

        taskService.destroyTask(task.getId());
        return ResponseEntity.noContent().build();
    }
}
