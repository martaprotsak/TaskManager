package com.protsak.service.implementation;

import com.protsak.dto.NewTaskDto;
import com.protsak.dto.TaskDto;
import com.protsak.entity.Task;
import com.protsak.entity.User;
import com.protsak.exception.ExceptionMessage;
import com.protsak.exception.NotFoundException;
import com.protsak.mapper.TaskMapper;
import com.protsak.repository.TaskRepository;
import com.protsak.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private UserServiceImpl userService;
    private TaskRepository taskRepository;

    public TaskServiceImpl(TaskMapper taskMapper, UserServiceImpl userService, TaskRepository taskRepository) {
        this.taskMapper = taskMapper;
        this.userService = userService;
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskDto save(NewTaskDto task) {
        User author = userService.getCurrentUser();
        task.setAuthor(author);
        task.setUsersWithAccess(Collections.singletonList(author));
        task.setDateTime(LocalDateTime.now());
        Task savedTask = taskRepository.save(taskMapper.convertToModel(task));
        return taskMapper.convertToDto(savedTask);
    }

    @Override
    public TaskDto edit(TaskDto taskDto) throws NotFoundException {
        Task task = taskRepository.findByIdAndUsersWithAccessContaining(taskDto.getId(), userService.getCurrentUser());
        if (task != null) {
            task.setDescription(taskDto.getDescription());
            task.setTitle(taskDto.getTitle());
            return taskMapper.convertToDto(taskRepository.save(task));
        } else {
            throw new NotFoundException(ExceptionMessage.TASK_NOT_FOUND);
        }
    }
}
