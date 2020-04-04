package org.springboard.elastic.mapper;

import org.mapstruct.Mapper;
import org.springboard.elastic.index.TaskElasticIndex;
import org.springboard.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskElasticMapper extends ZonedDateTimeMapper {

    TaskElasticIndex toTaskIndex(Task task);
}
