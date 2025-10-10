package com.theatomicity.scheduler.backend.mapper;

import com.theatomicity.scheduler.backend.model.Task;
import com.theatomicity.scheduler.backend.model.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TaskDtoMapper {
    TaskDto toTaskDto(Task task);
}
