package com.protsak.service.implementation;

import com.protsak.dto.ShareTaskDto;
import com.protsak.dto.TaskDto;
import com.protsak.entity.Task;
import com.protsak.entity.User;
import com.protsak.utils.ConstantMessage;
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
    public TaskDto save(TaskDto task) {
        User author = userService.getCurrentUser();
        task.setAuthor(author);
        task.setUsersWithAccess(Collections.singletonList(author));
        task.setDateTime(LocalDateTime.now());
        Task savedTask = taskRepository.save(taskMapper.convertToModel(task));
        return taskMapper.convertToDto(savedTask);
    }

    @Override
    public TaskDto edit(TaskDto taskDto) {
        Task task = taskRepository.findByTitleAndUsersWithAccessContaining(taskDto.getTitle(), userService.getCurrentUser());
        if (task != null) {
            task.setDescription(taskDto.getDescription());
            task.setTitle(taskDto.getTitle());
            task.setDateTime(LocalDateTime.now());
            return taskMapper.convertToDto(taskRepository.save(task));
        } else {
            throw new NotFoundException(ConstantMessage.TASK_NOT_FOUND);
        }
    }

    @Override
    public String share(ShareTaskDto dto) {
        Task task = taskRepository.findByTitleAndUsersWithAccessContaining(dto.getTitle(), userService.getCurrentUser());
        if (task != null) {

            User newUser = userService.findUserByEmail(dto.getEmail());
            if (newUser == null) {
                throw new NotFoundException(ConstantMessage.USER_NOT_FOUND_BY_EMAIL + dto.getEmail());
            }

            List<User> userList = task.getUsersWithAccess();
            userList.add(newUser);
            task.setUsersWithAccess(userList);
            taskRepository.save(task);
            return ConstantMessage.TASK_WAS_ADDED + dto.getEmail();
        }
        throw new NotFoundException(ConstantMessage.TASK_NOT_FOUND);
    }

    @Override
    public String delete(String title) {
        Task task = taskRepository.findByTitleAndUsersWithAccessContaining(title, userService.getCurrentUser());
        if (task != null) {
            List<User> userList = task.getUsersWithAccess();
            if (userList.size() > 1) {
                userList.remove(userService.getCurrentUser());
                task.setUsersWithAccess(userList);
                task.setDateTime(LocalDateTime.now());
                taskRepository.save(task);
            } else taskRepository.delete(task);
            return ConstantMessage.TASK_WAS_DELETED;
        }
        throw new NotFoundException(ConstantMessage.TASK_NOT_FOUND);
    }

    @Override
    public List<TaskDto> taskList(User user) {
        return taskMapper.convertToListDto(taskRepository.findAllByUsersWithAccessContaining(user));
    }
}