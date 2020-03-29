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

    @Mapping(source = "listing", target = "listingUuid", expression = "java(s.getUuid())")
    @Mapping(source = "parentTask", target = "parentTaskUuid", expression = "java(s.getUuid())")
    TaskDto toTaskDto(Task task);

    Task createTask(CreateTaskVo vo);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task mergeTask(@MappingTarget Task task, UpdateTaskVo vo);
}
