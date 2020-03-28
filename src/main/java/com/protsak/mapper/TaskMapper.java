package com.protsak.mapper;

import com.protsak.dto.TaskDto;
import com.protsak.entity.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapper {

    public Task convertToModel(TaskDto dto) {
        return Task.builder()
                .author(dto.getAuthor())
                .dateTime(dto.getDateTime())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .usersWithAccess(dto.getUsersWithAccess())
                .build();
    }

    public TaskDto convertToDto(Task model) {
        return TaskDto.builder()
                .author(model.getAuthor())
                .title(model.getTitle())
                .description(model.getDescription())
                .dateTime(model.getDateTime())
                .usersWithAccess(model.getUsersWithAccess())
                .build();
    }

    public List<TaskDto> convertToListDto(List<Task> tasks) {
        List<TaskDto> listDto = new ArrayList<>();
        for (Task task : tasks) {
            listDto.add(convertToDto(task));
        }
        return listDto;
    }

}
