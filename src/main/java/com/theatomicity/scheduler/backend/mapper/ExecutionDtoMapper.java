package com.theatomicity.scheduler.backend.mapper;

import com.theatomicity.scheduler.backend.model.Execution;
import com.theatomicity.scheduler.backend.model.dto.ExecutionDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValueMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ExecutionDtoMapper {
    ExecutionDto toExecutionDto(Execution execution);
}
