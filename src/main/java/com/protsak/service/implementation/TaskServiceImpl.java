package com.protsak.service.implementation;

import com.protsak.dto.NewTaskDto;
import com.protsak.dto.ShareTaskDto;
import com.protsak.dto.TaskDto;
import com.protsak.entity.Task;
import com.protsak.entity.User;
import com.protsak.exception.ConstantMessage;
import com.protsak.exception.NotFoundException;
import com.protsak.mapper.TaskMapper;
import com.protsak.repository.TaskRepository;
import com.protsak.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

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
            throw new NotFoundException(ConstantMessage.TASK_NOT_FOUND);
        }
    }

    @Override
    public String share(ShareTaskDto dto) {
        Task task = taskRepository.findByIdAndUsersWithAccessContaining(dto.getId(), userService.getCurrentUser());
        if (task != null) {

            User newUser = userService.findUserByEmail(dto.getEmail());
            if (newUser == null) return ConstantMessage.USER_NOT_FOUND_BY_EMAIL + dto.getEmail();

            List<User> userList = task.getUsersWithAccess();
            userList.add(newUser);
            task.setUsersWithAccess(userList);
            taskRepository.save(task);
            return ConstantMessage.TASK_WAS_ADDED + dto.getEmail();
        }
        return ConstantMessage.TASK_NOT_FOUND;
    }

    @Override
    public String delete(String id) {
        Task task = taskRepository.findByIdAndUsersWithAccessContaining(id, userService.getCurrentUser());
        if (task != null) {
            List<User> userList = task.getUsersWithAccess();
            if (userList.size() > 1) {
                userList.remove(userService.getCurrentUser());
                task.setUsersWithAccess(userList);
                taskRepository.save(task);
            } else taskRepository.delete(task);
            return ConstantMessage.TASK_WAS_DELETED;
        }
        return ConstantMessage.TASK_NOT_FOUND;
    }
}