package com.protsak.mapper;

import com.protsak.dto.NewTaskDto;
import com.protsak.dto.TaskDto;
import com.protsak.entity.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task convertToModel(NewTaskDto dto) {
        return Task.builder()
                .author(dto.getAuthor())
                .dateTime(dto.getDateTime())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .usersWithAccess(dto.getUsersWithAccess())
                .build();
    }

    public TaskDto convertToDto(Task model){
        return TaskDto.builder()
                .id(model.getId())
                .author(model.getAuthor())
                .title(model.getTitle())
                .description(model.getDescription())
                .dateTime(model.getDateTime())
                .build();

    }

}
