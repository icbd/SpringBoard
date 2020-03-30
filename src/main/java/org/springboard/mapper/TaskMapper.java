package org.springboard.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springboard.dto.TaskDto;
import org.springboard.entity.Task;
import org.springboard.vo.CreateTaskVo;
import org.springboard.vo.UpdateTaskVo;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "listing.uuid", target = "listingUuid")
    @Mapping(source = "parentTask.uuid", target = "parentTaskUuid")
    TaskDto toTaskDto(Task task);

    Task createTask(CreateTaskVo vo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task mergeTask(@MappingTarget Task task, UpdateTaskVo vo);
}
